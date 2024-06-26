<template>
  <div>
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button type="primary" v-auth="'system:sysTenant:save'" @click="handleCreate"
          >新增租户</a-button
        >
      </template>
      <template #status="{ record }">
        <BaseStatusSwitch
          :value="record.status"
          :api="statusApi"
          :id="record.id"
          @success="reload"
          perm-code="system:sysTenant:status"
        />
      </template>
      <template #action="{ record }">
        <TableAction
          v-if="record.id != 1"
          :actions="[
            {
              icon: 'clarity:note-edit-line',
              auth: 'system:sysTenant:save',
              onClick: handleEdit.bind(null, record),
            },
            {
              icon: 'ant-design:delete-outlined',
              color: 'error',
              auth: 'system:sysTenant:remove',
              popConfirm: {
                title: '是否确认删除',
                confirm: handleDelete.bind(null, record),
              },
            },
          ]"
        />
      </template>
    </BasicTable>

    <SysTenantDrawer @register="registerDrawer" @success="reload()" />
  </div>
</template>

<script setup lang="ts">
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { listApi, deleteApi, statusApi } from '@/api/system/tenant';
  import { columns, searchFormSchema } from './tenant.data';
  import { useDrawer } from '@/components/Drawer';
  import SysTenantDrawer from './TenantDrawer.vue';
  import BaseStatusSwitch from '@/components/Donut/BaseStatusSwitch/index.vue';

  defineOptions({ name: 'SysTenantManagement' });

  const [registerDrawer, { openDrawer }] = useDrawer();
  const [registerTable, { reload }] = useTable({
    title: '租户列表',
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
      width: 80,
      title: '操作',
      dataIndex: 'action',
      slots: { customRender: 'action' },
      fixed: 'right',
    },
  });

  function handleCreate() {
    openDrawer(true, {
      isUpdate: false,
    });
  }

  function handleEdit(record: Recordable) {
    openDrawer(true, {
      record,
      isUpdate: true,
    });
  }

  function handleDelete(record: Recordable) {
    deleteApi(record.id).then(() => {
      reload();
    });
  }
</script>
