<template>
  <div>
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button color="warning" @click="handleExport" v-auth="'system:operationLog:export'"
          >导出</a-button
        >
      </template>
      <template #action="{ record }">
        <TableAction
          :actions="[
            {
              icon: 'ant-design:eye-outlined',
              auth: 'system:operationLog:list',
              onClick: handleDetail.bind(null, record),
            },
          ]"
        />
      </template>
    </BasicTable>

    <DetailDrawer @register="registerDrawer" />
  </div>
</template>
<script lang="ts" setup name="">
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { operationLogListApi } from '@/api/system/log';
  import DetailDrawer from './DetailDrawer.vue';

  import { useDrawer } from '@/components/Drawer';

  import { columns, searchFormSchema } from './operationLog.data';

  defineOptions({ name: 'OperationLogManagement' });

  const [registerDrawer, { openDrawer }] = useDrawer();
  const [registerTable] = useTable({
    title: '操作日志列表',
    api: operationLogListApi,
    columns,
    formConfig: {
      labelWidth: 120,
      schemas: searchFormSchema,
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
      fixed: undefined,
    },
    rowKey: 'operId',
  });

  function handleDetail(record: Recordable) {
    openDrawer(true, {
      record,
    });
  }

  const handleExport = () => {
    // downloadFileByUrl({
    //   url: '/monitor/operlog/export',
    //   fileName: 'operlog' + new Date().getTime() + '.xlsx',
    //   method: 'POST',
    //   data: getForm().getFieldsValue(),
    // });
  };
</script>
