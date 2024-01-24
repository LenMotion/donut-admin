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
  import { formSchema } from './dictType.data';
  import { BasicDrawer, useDrawerInner } from '@/components/Drawer';

  import { saveApi } from '@/api/system/dictType';
  import { useMessage } from '@/hooks/web/useMessage';

  defineOptions({ name: 'DictTypeManagement' });

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
    isUpdate.value = !!data?.isUpdate;

    if (unref(isUpdate)) {
      setFieldsValue({ ...data.record });
    }
  });

  const getTitle = computed(() => (!unref(isUpdate) ? '新增字典' : '编辑字典'));

  async function handleSubmit() {
    try {
      setDrawerProps({ confirmLoading: true });
      const values = await validate();
      await saveApi(values);

      createMessage.success('保存成功');
      closeDrawer();
      emit('success');
    } finally {
      setDrawerProps({ confirmLoading: false });
    }
  }
</script>
