<template>
  <BasicDrawer
    v-bind="$attrs"
    @register="registerDrawer"
    :showFooter="false"
    :title="title"
    width="60%"
    @ok="closeDrawer"
  >
    <BasicTable @register="registerTable" />
  </BasicDrawer>
</template>
<script lang="ts" setup>
  import { reactive, unref, ref } from 'vue';
  import { BasicTable, useTable } from '@/components/Table/index';
  import { logColumns, logSearchFormSchema } from './job.data';
  import { BasicDrawer, useDrawerInner } from '@/components/Drawer';

  import { logListApi } from '@/api/system/job';

  const searchInfo = reactive<Recordable>({});
  const title = ref('');
  const [registerTable, { reload }] = useTable({
    title: '日志列表',
    api: logListApi,
    columns: logColumns,
    formConfig: {
      labelWidth: 120,
      schemas: logSearchFormSchema,
      fieldMapToTime: [['createTime', ['createStartTime', 'createEndTime'], 'YYYY-MM-DD HH:mm:ss']],
    },
    useSearchForm: true,
    showTableSetting: false,
    bordered: true,
    showIndexColumn: true,
    immediate: false,
    canResize: false,
    beforeFetch: (params) => {
      params.jobId = searchInfo.jobId;
      return params;
    },
  });

  defineEmits(['register']);

  const [registerDrawer, { setDrawerProps, closeDrawer }] = useDrawerInner(async (data) => {
    setDrawerProps({ confirmLoading: false });
    searchInfo.jobId = unref(data).record.id;
    console.log(data.record);
    title.value = '[' + unref(data).record.name + ']执行日志';
    reload();
  });
</script>
