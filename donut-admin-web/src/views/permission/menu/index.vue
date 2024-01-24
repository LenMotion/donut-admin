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

  const { createMessage } = useMessage();

  defineOptions({ name: 'MenuManagement' });

  const [registerDrawer, { openDrawer }] = useDrawer();
  const [
    registerTable,
    {
      updateTableDataRecord,
      deleteTableDataRecord,
      getDataSource,
      insertTableDataRecord,
      findTableDataRecord,
    },
  ] = useTable({
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
      deleteTableDataRecord(record.id);
    });
  }

  function handleSuccess(item: Recordable, isUpdate: boolean) {
    console.log(item, isUpdate);
    if (isUpdate) {
      const hasItem = findTableDataRecord(item.id);
      console.log(item, hasItem);
      // 更新数据
      updateTableDataRecord(item.id, item);
      // 因为可能设置到更新排序，所以需要重新排序
      if (item.parentId == 0) {
        const list = getDataSource();
        list.sort((a, b) => a.orderNo - b.orderNo);
      } else {
        const parentItem = findTableDataRecord(item.parentId);
        parentItem!.children.sort((a, b) => a.orderNo - b.orderNo);
        updateTableDataRecord(parentItem!.id, parentItem!);
        console.log(parentItem);
      }
    } else {
      // 如果上级的id为0，表示在第一级插入数据
      if (item.parentId == 0) {
        let treeList = getDataSource() || [];
        // 如果当前的序号大于等于某个菜单的序号，那么就插入到这个菜单的后面
        const indexToInsert = treeList.findIndex((record) => item.orderNo < record.orderNo);
        insertTableDataRecord(item, indexToInsert + 1);
      } else {
        // 根据上级的id获取对应的数据
        const parentItem = findTableDataRecord(item.parentId);
        if (!parentItem) {
          return;
        }
        // 拿到对应的子菜单
        const children = parentItem.children || [];
        // 查询比插入数据要大的坐标
        const indexToInsert = children.findIndex((record) => item.orderNo < record.orderNo);
        // 如果没有找到比item.orderNo大的元素，直接插入到末尾
        if (indexToInsert === -1) {
          children.push(item);
        } else {
          children.splice(indexToInsert, 0, item);
        }
        // 更新数据
        parentItem.children = children;
        updateTableDataRecord(parentItem.id, parentItem);
      }
    }
  }
</script>
