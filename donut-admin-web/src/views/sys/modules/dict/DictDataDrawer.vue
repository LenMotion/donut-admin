<template>
  <BasicDrawer
    v-bind="$attrs"
    @register="registerDrawer"
    showFooter
    :title="getTitle"
    width="500px"
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm">
      <template #preview="{ model }">
        <ATag :color="model.listClass">{{ model.dictLabel }}</ATag>
      </template>
    </BasicForm>
  </BasicDrawer>
</template>
<script lang="ts" setup>
  import { ref, computed, unref } from 'vue';
  import { BasicForm, useForm } from '@/components/Form/index';
  import { formSchema } from './dictData.data';
  import { BasicDrawer, useDrawerInner } from '@/components/Drawer';

  import { saveApi } from '@/api/system/dictData';
  import { useMessage } from '@/hooks/web/useMessage';
  import { Tag as ATag } from 'ant-design-vue';

  defineOptions({ name: 'DictDataManagement' });

  const emit = defineEmits(['success', 'register']);
  const isUpdate = ref(true);

  const { createMessage } = useMessage();

  const [registerForm, { resetFields, setFieldsValue, validate }] = useForm({
    labelWidth: 90,
    schemas: formSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 24 },
  });

  const [registerDrawer, { setDrawerProps, closeDrawer }] = useDrawerInner(async (data) => {
    resetFields();
    setDrawerProps({ confirmLoading: false });
    // 需要在setFieldsValue之前先填充treeData，否则Tree组件可能会报key not exist警告
    isUpdate.value = !!data?.isUpdate;

    setFieldsValue({
      ...data.record,
    });
  });

  const getTitle = computed(() => (!unref(isUpdate) ? '新增字典' : '编辑字典'));

  async function handleSubmit() {
    try {
      const values = await validate();
      setDrawerProps({ confirmLoading: true });
      saveApi(values).then(() => {
        createMessage.success('保存成功');
        closeDrawer();
        emit('success');
      });
    } finally {
      setDrawerProps({ confirmLoading: false });
    }
  }
</script>
