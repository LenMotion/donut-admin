<template>
  <BasicDrawer
    v-bind="$attrs"
    @register="registerDrawer"
    showFooter
    :title="getTitle"
    width="40%"
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm" />
  </BasicDrawer>
</template>
<script lang="ts" setup>
  import { ref, computed, unref } from 'vue';
  import { BasicForm, useForm } from '@/components/Form/index';
  import { formSchema } from './notice.data';
  import { BasicDrawer, useDrawerInner } from '@/components/Drawer';

  import { saveApi, detailApi } from '@/api/system/notice';
  import { treeApi } from '@/api/system/dept';
  import { useMessage } from '@/hooks/web/useMessage';

  defineOptions({ name: 'NoticeDrawer' });

  const emit = defineEmits(['success', 'register']);
  const isUpdate = ref(true);

  const { createMessage } = useMessage();

  const [registerForm, { resetFields, setFieldsValue, validate, updateSchema }] = useForm({
    labelWidth: 80,
    schemas: formSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 24 },
  });

  const [registerDrawer, { setDrawerProps, closeDrawer }] = useDrawerInner(async (data) => {
    resetFields();
    setDrawerProps({ confirmLoading: false });
    isUpdate.value = !!data?.isUpdate;

    const treeData = await treeApi();
    updateSchema([
      {
        field: 'deptIds',
        componentProps: { treeData },
      },
    ]);

    if (unref(isUpdate)) {
      const res = await detailApi(data.record.id);
      setFieldsValue(res);
    }
  });

  const getTitle = computed(() => (!unref(isUpdate) ? '新增通知公告' : '编辑通知公告'));

  async function handleSubmit() {
    try {
      const values = await validate();
      setDrawerProps({ confirmLoading: true });
      await saveApi(values);
      createMessage.success('保存成功');
      closeDrawer();
      emit('success');
    } finally {
      setDrawerProps({ confirmLoading: false });
    }
  }
</script>
./notice.data
