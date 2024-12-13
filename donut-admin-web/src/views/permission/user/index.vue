<template>
  <PageWrapper dense contentFullHeight fixedHeight contentClass="flex">
    <DeptTree class="w-1/5" @select="handleSelectDept" />
    <BasicTable @register="registerTable" class="w-4/5" :searchInfo="searchInfo">
      <template #toolbar>
        <a-button
          type="warning"
          v-auth="'system:user:password'"
          :disabled="getSelectRowKeys().length === 0"
          @click="handleResetPwd(getSelectRowKeys())"
          >重置密码</a-button
        >
        <a-button type="success" v-auth="'system:user:import'" @click="handleImport"
          >导入用户</a-button
        >
        <Export
          text="导出查询用户"
          export-type="user_list"
          prems="system:user:export"
          :doExport="handleExport"
        />
        <a-button type="primary" v-auth="'system:user:save'" @click="handleCreate"
          >新增用户</a-button
        >
      </template>
      <template #username="{ record }">
        <a-button type="link" @click="handleDetail(record)">{{ record.username }}</a-button>
      </template>
      <template #status="{ record }">
        <BaseStatusSwitch
          :value="record.status"
          :api="statusApi"
          :id="record.id"
          @success="reload"
          perm-code="system:user:status"
        />
      </template>
      <template #action="{ record }">
        <TableAction
          :actions="[
            {
              icon: 'clarity:note-edit-line',
              auth: 'system:user:save',
              onClick: handleEdit.bind(null, record),
            },
          ]"
          :drop-down-actions="[
            {
              icon: 'ant-design:info-circle-outlined',
              label: '详情',
              auth: 'system:user:save',
              onClick: handleDetail.bind(null, record),
            },
            {
              icon: 'ant-design:lock-outlined',
              label: '重置密码',
              color: 'error',
              auth: 'system:user:password',
              popConfirm: {
                title: '是否重置密码该用户密码？',
                confirm: handleResetPwd.bind(null, [record.id]),
              },
            },
            {
              icon: 'ant-design:delete-outlined',
              label: '删除',
              color: 'error',
              auth: 'system:user:remove',
              popConfirm: {
                title: '是否确认删除',
                confirm: handleDelete.bind(null, record),
              },
            },
          ]"
        />
      </template>
    </BasicTable>
    <UserDrawer @register="registerDrawer" @success="reload()" />
    <ImportModal
      :upload-api="importApi"
      :template-api="exportTempApi"
      @register="registerImportModal"
    />
  </PageWrapper>
</template>

<script lang="ts" setup>
  import { PageWrapper } from '@/components/Page';
  import DeptTree from '@/components/Donut/Dept/DeptTree.vue';
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { useDrawer } from '@/components/Drawer';
  import Export from '@/components/Donut/Export/index.vue';
  import {
    listApi,
    deleteApi,
    statusApi,
    exportApi,
    importApi,
    exportTempApi,
    resetPasswordApi,
  } from '@/api/system/user';
  import { columns, searchFormSchema } from './user.data';
  import { reactive } from 'vue';
  import UserDrawer from './UserDrawer.vue';
  import { useRouter } from 'vue-router';
  import ImportModal from '@/components/Donut/ImportModal/index.vue';
  import { useModal } from '@/components/Modal';
  import BaseStatusSwitch from '@/components/Donut/BaseStatusSwitch/index.vue';
  import { useMessage } from '@/hooks/web/useMessage';
  import type { Key } from 'ant-design-vue/es/_util/type';

  defineOptions({ name: 'UserManagement' });

  const { createMessage } = useMessage();
  const router = useRouter();

  const searchInfo = reactive<Recordable>({});

  const [registerDrawer, { openDrawer }] = useDrawer();
  const [registerImportModal, importAction] = useModal();
  const [registerTable, { reload, getForm, getSelectRowKeys }] = useTable({
    title: '用户列表',
    api: listApi,
    rowKey: 'id',
    columns,
    formConfig: {
      labelWidth: 120,
      schemas: searchFormSchema,
      autoSubmitOnEnter: true,
    },
    rowSelection: {
      type: 'checkbox',
    },
    clickToRowSelect: false,
    useSearchForm: true,
    showTableSetting: true,
    bordered: true,
    actionColumn: {
      width: 80,
      title: '操作',
      dataIndex: 'action',
      slots: { customRender: 'action' },
    },
  });

  const handleSelectDept = (deptId) => {
    searchInfo.deptId = deptId;
    reload();
  };

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

  function handleDetail(record: Recordable) {
    router.push({ path: '/organ/userManagement/userDetail/' + record.id });
  }

  async function handleExport() {
    await exportApi(getForm().getFieldsValue());
  }

  function handleImport() {
    importAction.openModal();
  }

  function handleResetPwd(userIds: Key[]) {
    if (userIds.length === 0) {
      return createMessage.error('请选择要重置密码的用户');
    }

    resetPasswordApi({ userIds }).then(() => {
      createMessage.success('重置密码成功');
    });
  }
</script>
