<template>
  <div>
    <BasicTable @register="registerTable">
      <template #toolbar>
        <Export
          exportType="login_list"
          text="导出"
          prems="system:loginLog:export"
          :doExport="handleExport"
        />
      </template>
    </BasicTable>
  </div>
</template>
<script lang="ts" setup>
  import { BasicTable, useTable } from '@/components/Table';
  import { loginLogListApi, exportLoginLogApi } from '@/api/system/log';

  import { columns, searchFormSchema } from './loginLog.data';
  import Export from '@/components/Donut/Export/index.vue';

  defineOptions({ name: 'LoginLogManagement' });

  const [registerTable, { getForm }] = useTable({
    title: '登录日志列表',
    api: loginLogListApi,
    columns,
    formConfig: {
      labelWidth: 120,
      schemas: searchFormSchema,
    },
    useSearchForm: true,
    showTableSetting: true,
    bordered: true,
    showIndexColumn: true,
  });

  const handleExport = async () => {
    await exportLoginLogApi(getForm().getFieldsValue());
  };
</script>
