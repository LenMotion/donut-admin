<template>
  <BasicModal
    @register="registerModal"
    width="680px"
    v-bind="$attrs"
    :title="title"
    :footer="false"
  >
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button type="success" v-auth="'system:role:user:bind'" @click="handleBindUser"
          >关联用户</a-button
        >
      </template>
      <template #action="{ record }">
        <TableAction
          :actions="[
            {
              icon: 'ant-design:close-square-outlined',
              auth: 'system:role:user:unbind',
              tooltip: '解除关联',
              color: 'error',
              ifShow: record.hasPermission,
              popConfirm: {
                title: '是否确认解除关联',
                confirm: handleRemove.bind(null, record),
              }
            },
          ]"
        />
      </template>
    </BasicTable>
  </BasicModal>
  <UserListModal @register="registerUserModal" @success="reload" />
</template>

<script setup lang="ts">
  import { BasicModal, useModalInner,useModal } from '@/components/Modal';
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { roleUserListApi, roleUserUnbindApi } from '@/api/system/role';
  import { ref } from 'vue';
  import UserListModal from './UserListModal.vue';

  const title = ref('');

  const [registerUserModal, {openModal}] = useModal();
  const [registerModal] = useModalInner((data) => {
    title.value = `“${data.record.roleName}”关联用户列表`;
    getForm().setFieldsValue({ roleId: data.record.id, keyword: '' });
    reload({ searchInfo: { roleId: data.record.id } });
  });

  const [registerTable, { reload, getForm }] = useTable({
    api: roleUserListApi,
    immediate: false,
    useSearchForm: true,
    canResize: false,
    columns: [
      {
        title: '用户名',
        dataIndex: 'username',
      },
      {
        title: '昵称',
        dataIndex: 'nickName',
      },
      {
        title: '所属部门',
        dataIndex: 'transMap.deptName',
        customRender: ({ record }) => record?.transMap?.deptName,
      },
    ],
    formConfig: {
      labelWidth: 90,
      showResetButton: false,
      schemas: [
        {
          field: 'roleId',
          label: '角色ID',
          component: 'Input',
          colProps: { span: 12 },
          show: false,
        },
        {
          field: 'keyword',
          label: '用户名/昵称',
          component: 'Input',
          colProps: { span: 12 },
        },
      ],
    },
    showTableSetting: false,
    actionColumn: {
      width: 60,
      title: '操作',
      slots: { customRender: 'action' },
      fixed: 'right',
    },
  });

  async function handleRemove(record) {
    await roleUserUnbindApi(record.id)
    reload();
  }

  function handleBindUser() {
    openModal(true, {
      record: getForm().getFieldsValue()
    })
  }
</script>
