<template>
  <div>
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button type="primary" v-auth="'system:role:save'" @click="handleCreate"
          >新增角色</a-button
        >
      </template>
      <template #status="{ record }">
        <BaseStatusSwitch
          :value="record.status"
          :api="statusApi"
          :id="record.id"
          @success="reload"
          perm-code="system:role:status"
        />
      </template>
      <template #action="{ record }">
        <TableAction
          :actions="[
            {
              icon: 'clarity:user-line',
              auth: 'system:role:user:list',
              tooltip: '用户列表',
              onClick: handleUserList.bind(null, record),
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

    <RoleDrawer @register="registerDrawer" @success="reload()" />
    <RoleUserModal @register="registerModal" />
  </div>
</template>

<script setup lang="ts">
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { listApi, deleteApi, statusApi } from '@/api/system/role';
  import { columns, searchFormSchema } from './role.data';
  import { useDrawer } from '@/components/Drawer';
  import RoleDrawer from './RoleDrawer.vue';
  import BaseStatusSwitch from '@/components/Donut/BaseStatusSwitch/index.vue';
  import RoleUserModal from './RoleUserModal.vue';
  import { useModal } from '@/components/Modal';

  defineOptions({ name: 'RoleManagement' });

  const [registerDrawer, { openDrawer }] = useDrawer();
  const [registerModal, { openModal }] = useModal();
  const [registerTable, { reload }] = useTable({
    title: '角色列表',
    api: listApi,
    columns,
    formConfig: {
      labelWidth: 90,
      schemas: searchFormSchema,
      baseColProps: { xl: 6, xxl: 4 },
    },
    useSearchForm: true,
    showTableSetting: true,
    bordered: true,
    showIndexColumn: true,
    actionColumn: {
      width: 130,
      title: '操作',
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

  function handleUserList(record: Recordable) {
    openModal(true, {
      record,
    });
  }
</script>
