<template>
  <div>
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
  </div>
</template>

<script setup lang="ts">
  import { computed, ref, unref } from 'vue';
  import { BasicDrawer, useDrawerInner } from '@/components/Drawer';
  import { BasicForm, useForm } from '@/components/Form';
  import { formSchema } from './dept.data';
  import { saveApi } from '@/api/system/dept';

  const emit = defineEmits(['success', 'register']);

  defineOptions({ name: 'DeptDrawer' });

  const isUpdate = ref(true);

  const [registerDrawer, { closeDrawer, setDrawerProps }] = useDrawerInner(async (data) => {
    resetFields();
    setDrawerProps({ confirmLoading: false });
    isUpdate.value = !!data?.isUpdate;

    updateSchema({
      field: 'parentId',
      componentProps: { treeData: [{ id: 0, deptName: '无上级', children: data.treeData }] },
    });

    if (unref(isUpdate)) {
      setFieldsValue({ ...data.record });
    }
  });

  const [registerForm, { validate, setFieldsValue, resetFields, updateSchema }] = useForm({
    labelWidth: '100px',
    showActionButtonGroup: false,
    schemas: formSchema,
    baseColProps: { span: 24 },
  });

  const getTitle = computed(() => (!unref(isUpdate) ? '新增部门' : '编辑部门'));

  const handleSubmit = async () => {
    try {
      const values = await validate();
      setDrawerProps({ confirmLoading: true });
      const id = await saveApi(values);
      closeDrawer();
      emit('success', { ...values, id }, unref(isUpdate));
    } finally {
      setDrawerProps({ confirmLoading: false });
    }
  };
</script>
