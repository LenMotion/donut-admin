<template>
  <PageWrapper dense contentFullHeight fixedHeight contentClass="flex">
    <DeptTree class="w-1/5" @select="handleSelectDept" />
    <BasicTable @register="registerTable" class="w-4/5" :searchInfo="searchInfo">
      <template #toolbar>
        <a-button type="success" v-auth="'system:user:import'" @click="handleImport"
          >导入用户</a-button
        >
        <a-button
          type="warning"
          v-auth="'system:user:export'"
          @click="handleExport"
          :loading="loading"
          >导出查询用户</a-button
        >
        <a-button type="primary" v-auth="'system:user:save'" @click="handleCreate"
          >新增用户</a-button
        >
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
              icon: 'ant-design:info-circle-outlined',
              auth: 'system:user:save',
              onClick: handleDetail.bind(null, record),
            },
            {
              icon: 'clarity:note-edit-line',
              auth: 'system:user:save',
              onClick: handleEdit.bind(null, record),
            },
            {
              icon: 'ant-design:delete-outlined',
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
  import { downloadByUrl } from '@/utils/file/download';

  import {
    listApi,
    deleteApi,
    statusApi,
    exportApi,
    importApi,
    exportTempApi,
  } from '@/api/system/user';

  import { columns, searchFormSchema } from './user.data';
  import { reactive, ref } from 'vue';

  import UserDrawer from './UserDrawer.vue';
  import { useRouter } from 'vue-router';
  import ImportModal from '@/components/Donut/ImportModal/index.vue';
  import { useModal } from '@/components/Modal';
  import BaseStatusSwitch from '@/components/Donut/BaseStatusSwitch/index.vue';

  defineOptions({ name: 'UserManagement' });

  const router = useRouter();

  const searchInfo = reactive<Recordable>({});
  const loading = ref(false);

  const [registerDrawer, { openDrawer }] = useDrawer();
  const [registerImportModal, importAction] = useModal();
  const [registerTable, { reload, getForm }] = useTable({
    title: '用户列表',
    api: listApi,
    rowKey: 'id',
    columns,
    formConfig: {
      labelWidth: 120,
      schemas: searchFormSchema,
      autoSubmitOnEnter: true,
    },
    useSearchForm: true,
    showTableSetting: true,
    bordered: true,
    actionColumn: {
      width: 120,
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

  function handleExport() {
    loading.value = true;
    exportApi(getForm().getFieldsValue())
      .then((url) => downloadByUrl({ url }))
      .finally(() => (loading.value = false));
  }

  function handleImport() {
    importAction.openModal();
  }
</script>
