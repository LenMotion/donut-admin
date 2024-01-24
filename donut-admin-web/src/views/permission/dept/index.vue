<template>
  <div>
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button type="primary" @click="handleCreate" v-auth="'system:dept:save'">
          新增部门
        </a-button>
      </template>
      <template #status="{ record }">
        <!--parentDeptTag主要是为了禁用这个操作 -->
        <BaseStatusSwitch
          :value="record.status"
          :api="statusApi"
          :id="record.id"
          @success="reload"
        />
      </template>
      <template #action="{ record }">
        <TableAction
          :actions="[
            {
              icon: 'clarity:note-edit-line',
              auth: 'system:dept:save',
              ifShow: !record.parentDeptTag,
              onClick: handleEdit.bind(null, record),
            },
            {
              icon: 'ant-design:delete-outlined',
              auth: 'system:dept:remove',
              color: 'error',
              ifShow: !record.parentDeptTag,
              popConfirm: {
                title: '是否确认删除',
                placement: 'left',
                confirm: handleDelete.bind(null, record),
              },
            },
          ]"
        />
      </template>
    </BasicTable>
    <DeptDrawer @register="registerDrawer" @success="reload()" />
  </div>
</template>
<script lang="ts" setup>
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { treeApi, deleteApi, statusApi } from '@/api/system/dept';

  import { useDrawer } from '@/components/Drawer';
  import DeptDrawer from './DeptDrawer.vue';
  import BaseStatusSwitch from '@/components/Donut/BaseStatusSwitch/index.vue';

  import { columns, searchFormSchema } from './dept.data';
  import { useMessage } from '@/hooks/web/useMessage';

  const { createMessage } = useMessage();

  defineOptions({ name: 'DeptManagement' });

  const [registerDrawer, { openDrawer }] = useDrawer();
  const [registerTable, { reload, getDataSource }] = useTable({
    title: '部门列表',
    api: treeApi,
    columns,
    formConfig: {
      labelWidth: 80,
      schemas: searchFormSchema,
    },
    isTreeTable: true,
    pagination: false,
    striped: false,
    useSearchForm: true,
    showTableSetting: true,
    bordered: true,
    showIndexColumn: false,
    canResize: false,
    actionColumn: {
      width: 80,
      title: '操作',
      dataIndex: 'action',
      fixed: 'right',
      slots: { customRender: 'action' },
    },
  });

  function handleCreate() {
    openDrawer(true, {
      isUpdate: false,
      treeData: getDataSource(),
    });
  }

  function handleEdit(record: Recordable) {
    openDrawer(true, {
      record,
      treeData: getDataSource(),
      isUpdate: true,
    });
  }

  function handleDelete(record: Recordable) {
    deleteApi(record.id).then(() => {
      createMessage.success('删除成功！');
      reload();
    });
  }
</script>
