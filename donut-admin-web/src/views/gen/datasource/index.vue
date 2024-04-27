<template>
  <div>
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button type="primary" v-auth="'generator:genDatasource:save'" @click="handleCreate"
          >新增数据源</a-button
        >
      </template>
      <template #status="{ record }">
        <BaseStatusSwitch
          :value="record.status + ''"
          :api="statusApi"
          :id="record.id"
          @success="reload"
          perm-code="generator:genDatasource:status"
        />
      </template>
      <template #action="{ record }">
        <TableAction
          :actions="[
            {
              auth: 'generator:genDatasource:checkConnection',
              label: '测试连接',
              onClick: handleConnection.bind(null, record),
            },
            {
              icon: 'clarity:note-edit-line',
              auth: 'generator:genDatasource:save',
              onClick: handleEdit.bind(null, record),
            },
            {
              icon: 'ant-design:delete-outlined',
              color: 'error',
              auth: 'generator:genDatasource:remove',
              popConfirm: {
                title: '是否确认删除',
                confirm: handleDelete.bind(null, record),
              },
            },
          ]"
        />
      </template>
    </BasicTable>

    <GenDatasourceDrawer @register="registerDrawer" @success="reload()" />
  </div>
</template>

<script setup lang="ts">
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { listApi, deleteApi, statusApi, checkConnectionApi } from '@/api/gen/datasource';
  import { columns, searchFormSchema } from './genDatasource.data';
  import { useDrawer } from '@/components/Drawer';
  import GenDatasourceDrawer from './GenDatasourceDrawer.vue';
  import BaseStatusSwitch from '@/components/Donut/BaseStatusSwitch/index.vue';
  import { message } from 'ant-design-vue';

  defineOptions({ name: 'GenDatasourceManagement' });

  const [registerDrawer, { openDrawer }] = useDrawer();
  const [registerTable, { reload }] = useTable({
    title: '数据源列表',
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
      width: 150,
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

  function handleConnection(record: Recordable) {
    checkConnectionApi(record.id).then((res) => {
      if (res) {
        message.success('连接成功');
      } else {
        message.error('连接失败');
      }
      reload();
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
