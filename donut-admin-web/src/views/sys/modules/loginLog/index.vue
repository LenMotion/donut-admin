<template>
  <div>
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button
          color="warning"
          @click="handleExport"
          v-auth="'system:loginLog:export'"
          :loading="loading"
          >导出</a-button
        >
      </template>
    </BasicTable>
  </div>
</template>
<script lang="ts" setup>
  import { BasicTable, useTable } from '@/components/Table';
  import { loginLogListApi, exportLoginLogApi } from '@/api/system/log';

  import { columns, searchFormSchema } from './loginLog.data';

  import { downloadByUrl } from '@/utils/file/download';
  import { ref } from 'vue';

  defineOptions({ name: 'LoginLogManagement' });

  const loading = ref(false);

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

  const handleExport = () => {
    loading.value = true;
    exportLoginLogApi(getForm().getFieldsValue())
      .then((url) => downloadByUrl({ url }))
      .finally(() => (loading.value = false));
  };
</script>
