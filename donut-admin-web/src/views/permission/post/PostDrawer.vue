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
  import { formSchema } from './post.data';
  import { BasicDrawer, useDrawerInner } from '@/components/Drawer';

  import { saveApi } from '@/api/system/post';
  import { treeApi } from '@/api/system/dept';
  import { useMessage } from '@/hooks/web/useMessage';

  defineOptions({ name: 'PostDrawer' });

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
        field: 'deptId',
        componentProps: { treeData: [{ id: '0', deptName: '全局' }, ...treeData] },
      },
    ]);

    if (unref(isUpdate)) {
      setFieldsValue({ ...data.record });
    }
  });

  const getTitle = computed(() => (!unref(isUpdate) ? '新增岗位' : '编辑岗位'));

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
