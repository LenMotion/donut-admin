<template>
  <div>
    <PageWrapper>
      <BasicTable @register="registerTable">
        <template #toolbar>
          <a-button type="primary" @click="handleCreate({})" v-auth="'system:menu:save'">
            新增菜单
          </a-button>
        </template>
        <template #action="{ record }">
          <TableAction
            :actions="[
              {
                icon: 'ant-design:plus-square-outlined',
                auth: 'system:menu:save',
                onClick: handleCreate.bind(null, { parentId: record.id }),
              },
              {
                icon: 'clarity:note-edit-line',
                auth: 'system:menu:save',
                onClick: handleEdit.bind(null, record),
              },
              {
                icon: 'ant-design:delete-outlined',
                auth: 'system:menu:remove',
                color: 'error',
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
      <MenuDrawer @register="registerDrawer" @success="handleSuccess" />
    </PageWrapper>
  </div>
</template>
<script lang="ts" setup>
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { menuTreeApi, deleteApi } from '@/api/system/menu';
  import { PageWrapper } from '@/components/Page';
  import { useDrawer } from '@/components/Drawer';
  import MenuDrawer from './MenuDrawer.vue';
  import { columns } from './menu.data';
  import { useMessage } from '@/hooks/web/useMessage';
  import { insertTableData } from '@/utils/helper/tableTreeHelper';

  const { createMessage } = useMessage();

  defineOptions({ name: 'MenuManagement' });

  const [registerDrawer, { openDrawer }] = useDrawer();
  const [registerTable, tableAction] = useTable({
    title: '菜单列表',
    api: menuTreeApi,
    rowKey: 'id',
    columns,
    isTreeTable: true,
    pagination: false,
    striped: false,
    useSearchForm: false,
    showTableSetting: true,
    bordered: true,
    showIndexColumn: false,
    canResize: false,
    actionColumn: {
      width: 120,
      title: '操作',
      dataIndex: 'action',
      slots: { customRender: 'action' },
      fixed: 'right',
    },
  });

  function handleCreate(record: Recordable) {
    openDrawer(true, {
      isUpdate: false,
      record,
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
      createMessage.success('删除成功！');
      tableAction.deleteTableDataRecord(record.id);
    });
  }

  function handleSuccess(item: Recordable, isUpdate: boolean) {
    insertTableData({
      item,
      isUpdate,
      tableAction,
      orderField: 'orderNo',
    });
  }
</script>
