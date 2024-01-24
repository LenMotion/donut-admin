<template>
  <div>
    <BasicTable @register="registerTable">
      <template #url="{ record }">
        <a-image
          v-if="record.contentType?.startsWith('image')"
          :width="50"
          :src="record.transMap?.previewUrl"
        />
      </template>
      <template #action="{ record }">
        <TableAction
          :actions="[
            {
              icon: 'ant-design:cloud-download-outlined',
              tooltip: '下载',
              popConfirm: {
                title: '是否确认下载文件',
                confirm: handleDownload.bind(null, record),
              },
            },
          ]"
        />
      </template>
    </BasicTable>
  </div>
</template>
<script lang="ts" setup>
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { listApi } from '@/api/system/fileStorage';
  import { Image as AImage } from 'ant-design-vue';
  import { columns, searchFormSchema } from './fileStorage.data';
  import { downloadByUrl } from '@/utils/file/download';

  defineOptions({ name: 'FileManagement' });

  const [registerTable] = useTable({
    title: '文件记录列表',
    api: listApi,
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
      fixed: 'right',
    },
  });

  function handleDownload(record: Recordable) {
    downloadByUrl({ url: record.transMap?.previewUrl });
  }
</script>
