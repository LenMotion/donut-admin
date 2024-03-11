<template>
  <div>
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button type="primary" v-auth="'system:role:save'" @click="handleCreate"
          >新增生成</a-button
        >
      </template>
      <template #action="{ record }">
        <TableAction
          :actions="[
            {
              icon: 'ant-design:eye-outlined',
              auth: 'system:role:save',
              onClick: handlePreview.bind(null, record),
            },
            {
              icon: 'clarity:note-edit-line',
              auth: 'system:role:save',
              onClick: handleEdit.bind(null, record),
            },
            {
              icon: 'ant-design:delete-outlined',
              color: 'error',
              auth: 'system:role:remove',
              popConfirm: {
                title: '是否确认删除',
                confirm: handleDelete.bind(null, record),
              },
            },
          ]"
        />
      </template>
    </BasicTable>

    <CodeModal @register="registerModal" @success="reload()" />
    <CodePreviewModal @register="registerPreviewModal" />
  </div>
</template>

<script setup lang="ts">
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { listApi, deleteApi } from '@/api/gen/code';
  import { columns, searchFormSchema } from './code.data';
  import { useModal } from '@/components/Modal';
  import CodeModal from './CodeModal.vue';
  import CodePreviewModal from './CodePreviewModal.vue';

  defineOptions({ name: 'RoleManagement' });

  const [registerModal, { openModal }] = useModal();
  const [registerPreviewModal, { openModal: openPreviewModal }] = useModal();
  const [registerTable, { reload }] = useTable({
    title: '代码列表',
    api: listApi,
    columns,
    formConfig: {
      labelWidth: 90,
      schemas: searchFormSchema,
      baseColProps: { xl: 8, xxl: 6 },
    },
    useSearchForm: true,
    showTableSetting: true,
    bordered: true,
    showIndexColumn: true,
    actionColumn: {
      width: 120,
      title: '操作',
      dataIndex: 'action',
      slots: { customRender: 'action' },
      fixed: 'right',
    },
  });

  function handleCreate() {
    openModal(true, {
      isUpdate: false,
    });
  }

  function handleEdit(record: Recordable) {
    openModal(true, {
      record,
      isUpdate: true,
    });
  }

  function handleDelete(record: Recordable) {
    deleteApi(record.id).then(() => {
      reload();
    });
  }

  function handlePreview(record: Recordable) {
    openPreviewModal(true, { record });
  }
</script>
