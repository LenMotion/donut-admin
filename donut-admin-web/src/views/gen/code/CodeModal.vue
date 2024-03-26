<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    :title="isUpdate ? '更新信息' : '新增信息'"
    :mask-closable="false"
    width="80%"
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm" />
    <BasicTable @register="registerTable">
      <template #action="{ record }">
        <TableAction :actions="createActions(record)" />
      </template>
    </BasicTable>
  </BasicModal>
</template>

<script setup lang="ts">
  import { ref } from 'vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { BasicForm, useForm } from '@/components/Form';
  import { formSchema, fieldColumns } from './code.data';
  import { BasicTable, useTable, TableAction, EditRecordRow, ActionItem } from '@/components/Table';
  import { tableColumnsApi, tableColumnsByTableApi, saveApi } from '@/api/gen/code';
  import { useMessage } from '@/hooks/web/useMessage';
  import { menuTreeApi } from '@/api/system/menu';

  const { createMessage: msg } = useMessage();
  const isUpdate = ref(true);
  const emit = defineEmits(['success', 'register']);

  const menuTree = ref<any[]>([]);

  menuTreeApi().then((res) => {
    menuTree.value = res;
  });

  const [registerModal, { setModalProps, closeModal }] = useModalInner((data) => {
    isUpdate.value = data?.isUpdate;
    resetFields();
    setTableData([]);
    currentEditKeyRef.value = '';
    updateSchema([{ field: 'menuId', componentProps: { treeData: menuTree.value } }]);
    if (data?.isUpdate) {
      setFieldsValue({ ...data.record });
      tableColumnsByTableApi(data.record.id).then((res) => {
        setTableData(res);
        currentEditKeyRef.value = '';
      });
    }
  });
  const [registerForm, { validate, resetFields, updateSchema, setFieldsValue }] = useForm({
    showActionButtonGroup: false,
    baseColProps: { lg: 8, md: 12, sm: 24 },
    labelWidth: 120,
    schemas: formSchema((tableName) => {
      tableColumnsApi(tableName).then((res) => {
        setTableData(res);
      });
    }),
  });
  const [registerTable, { setTableData, getDataSource }] = useTable({
    columns: fieldColumns,
    pagination: false,
    canResize: true,
    actionColumn: {
      width: 120,
      title: 'Action',
      dataIndex: 'action',
      slots: { customRender: 'action' },
    },
  });

  const currentEditKeyRef = ref('');

  function handleEdit(record: EditRecordRow) {
    currentEditKeyRef.value = record.key;
    record.onEdit?.(true);
  }

  function handleCancel(record: EditRecordRow) {
    currentEditKeyRef.value = '';
    record.onEdit?.(false, false);
  }

  async function handleSave(record: EditRecordRow) {
    // 校验
    const valid = await record.onValid?.();
    if (valid) {
      try {
        // 保存之后提交编辑状态
        const pass = await record.onEdit?.(false, true);
        if (pass) {
          currentEditKeyRef.value = '';
        }
      } catch (error) {
        msg.error({ content: '保存失败', key: 'saving' });
      }
    } else {
      msg.error({ content: '请填写正确的数据', key: 'saving' });
    }
  }
  function createActions(record: EditRecordRow): ActionItem[] {
    if (!record.editable) {
      return [
        {
          label: '编辑',
          disabled: currentEditKeyRef.value ? currentEditKeyRef.value !== record.key : false,
          onClick: handleEdit.bind(null, record),
        },
      ];
    }
    return [
      {
        label: '保存',
        onClick: handleSave.bind(null, record),
      },
      {
        label: '取消',
        popConfirm: {
          title: '是否取消编辑',
          confirm: handleCancel.bind(null, record),
        },
      },
    ];
  }

  const handleSubmit = async () => {
    try {
      const values = await validate();
      values.columns = getDataSource();
      setModalProps({ confirmLoading: true });
      await saveApi(values);
      msg.success('保存成功');
      closeModal();
      emit('success');
    } finally {
      setModalProps({ confirmLoading: false });
    }
  };
</script>
