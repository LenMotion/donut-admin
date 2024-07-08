<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    width="70%"
    title="日志列表"
    :show-ok-btn="false"
    cancel-text="关闭"
  >
    <BasicTable @register="registerTable">
      <template #action="{ record }">
        <TableAction
          v-if="record.status == 1"
          :actions="[
            {
              icon: 'ant-design:cloud-download-outlined',
              onClick: handleDownload.bind(null, record),
            },
          ]"
        />
      </template>
    </BasicTable>
  </BasicModal>
</template>

<script setup lang="ts">
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { listApi } from '@/api/system/exportLog';
  import { downloadByUrl } from '@/utils/file/download';

  defineEmits(['register']);

  const props = defineProps({
    exportType: {
      type: String,
      required: true,
    },
  });

  const [registerModal] = useModalInner(() => {
    // 避免立即刷新导致导出数据未生成
    setTimeout(() => {
      reload();
    }, 200);
  });

  const [registerTable, { reload }] = useTable({
    api: listApi,
    columns: [
      {
        title: '文件名称',
        dataIndex: 'name',
      },
      {
        title: '导出时间',
        dataIndex: 'createTime',
      },
      {
        title: '状态',
        dataIndex: 'status',
        customRender: ({ record }) => record.transMap?.statusName || '-',
      },
      {
        title: '执行时间(秒)',
        dataIndex: 'execTime',
        customRender: ({ text }) => (text ? (parseFloat(text) / 1000).toFixed(2) : '-'),
      },
      {
        title: '错误日志',
        dataIndex: 'errorMsg',
      },
    ],
    size: 'small',
    showIndexColumn: true,
    showTableSetting: true,
    canResize: false,
    bordered: true,
    searchInfo: {
      exportType: props.exportType,
    },
    actionColumn: {
      width: 60,
      title: '操作',
      dataIndex: 'action',
      slots: { customRender: 'action' },
      fixed: undefined,
    },
  });

  function handleDownload(record: Recordable) {
    downloadByUrl({ url: record.transMap?.url });
  }
</script>
