<template>
  <BasicDrawer
    v-bind="$attrs"
    @register="registerDrawer"
    showFooter
    :title="getTitle"
    width="500px"
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm" />
  </BasicDrawer>
</template>
<script lang="ts" setup>
  import { ref, computed, unref } from 'vue';
  import { BasicForm, useForm } from '@/components/Form/index';
  import { editFormSchema } from './config.data';
  import { BasicDrawer, useDrawerInner } from '@/components/Drawer';

  import { saveApi } from '@/api/system/config';
  import { useMessage } from '@/hooks/web/useMessage';

  defineOptions({ name: 'ConfigDrawer' });

  const emit = defineEmits(['success', 'register']);
  const isUpdate = ref(true);

  const { createMessage } = useMessage();

  const [registerForm, { resetFields, setFieldsValue, validate }] = useForm({
    labelWidth: 90,
    schemas: editFormSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 24 },
  });

  const [registerDrawer, { setDrawerProps, closeDrawer }] = useDrawerInner(async (data) => {
    resetFields();
    setDrawerProps({ confirmLoading: false });
    // 需要在setFieldsValue之前先填充treeData，否则Tree组件可能会报key not exist警告
    isUpdate.value = !!data?.isUpdate;

    setFieldsValue({ ...data.record });
  });

  const getTitle = computed(() => (!unref(isUpdate) ? '新增配置' : '编辑配置'));

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
