<template>
  <div>
    <BasicTable @register="registerTable">
      <template #action="{ record }">
        <TableAction :actions="createActions(record)" />
      </template>
    </BasicTable>
  </div>
</template>

<script setup lang="ts">
  import { ref } from 'vue';
  import { BasicTable, useTable, TableAction, EditRecordRow, ActionItem } from '@/components/Table';
  import { fieldColumns } from './component.data';
  import { useMessage } from '@/hooks/web/useMessage';

  const { createMessage: msg } = useMessage();

  const [registerTable, { setTableData, getDataSource }] = useTable({
    columns: fieldColumns,
    pagination: false,
    canResize: true,
    maxHeight: 480,
    showIndexColumn: false,
    actionColumn: {
      width: 120,
      title: '操作',
      dataIndex: 'action',
      slots: { customRender: 'action' },
    },
  });

  const currentEditKeyRef = ref('');

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

  defineExpose({
    setTableData: setTableData,
    getDataSource: getDataSource,
    resetTable: (data: Recordable[]) => {
      currentEditKeyRef.value = '';
      setTableData(data);
    },
  });
</script>
