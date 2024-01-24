<template>
  <div>
    <PageWrapper title="通知公告" dense :headerStyle="{ paddingBottom: 0, top: 0 }" headerSticky>
      <template #headerContent>
        <Tabs
          v-model:activeKey="searchInfo.noticeType"
          :tabBarStyle="{ margin: 0 }"
          @change="reload()"
        >
          <TabPane key="0" tab="通知" />
          <TabPane key="1" tab="公告" />
        </Tabs>
      </template>

      <BasicTable @register="registerTable" :search-info="searchInfo">
        <template #action="{ record }">
          <TableAction
            :actions="[
              {
                icon: 'ant-design:eye-outlined',
                onClick: handleDetail.bind(null, record),
              },
            ]"
          />
        </template>
      </BasicTable>
    </PageWrapper>

    <NoticeDetailModal @register="registerModal" />
  </div>
</template>

<script setup lang="ts">
  import { PageWrapper } from '@/components/Page';
  import { Tabs, TabPane } from 'ant-design-vue';
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { reactive, watch } from 'vue';
  import { userNoticeListApi } from '@/api/system/notice';
  import NoticeDetailModal from '@/components/Donut/NoticeDetailModal/index.vue';
  import { useModal } from '@/components/Modal';
  import { useRoute } from 'vue-router';

  defineOptions({ name: 'UserNoticeList' });

  const searchInfo = reactive({
    noticeType: '0',
  });

  const route = useRoute();

  watch(
    () => route.query,
    (newQuery) => {
      searchInfo.noticeType = newQuery.noticeType as string;
      reload({ searchInfo });
    },
  );

  console.log(route.query);
  const [registerModal, { openModal }] = useModal();
  const [registerTable, { reload }] = useTable({
    api: userNoticeListApi,
    columns: [
      {
        title: '标题',
        dataIndex: 'noticeTitle',
      },
      {
        title: '发布时间',
        dataIndex: 'publishTime',
      },
      {
        title: '是否阅读',
        dataIndex: 'isRead',
        customRender: ({ text }) => (text ? '是' : '否'),
      },
      {
        title: '阅读时间',
        dataIndex: 'readTime',
      },
    ],
    formConfig: {
      labelWidth: 120,
      schemas: [
        {
          field: 'noticeTitle',
          label: '标题',
          component: 'Input',
          colProps: { xl: 6, lg: 8 },
        },
      ],
      autoSubmitOnEnter: true,
    },
    useSearchForm: true,
    showTableSetting: true,
    bordered: true,
    actionColumn: {
      width: 60,
      title: '操作',
      dataIndex: 'action',
      slots: { customRender: 'action' },
    },
  });

  const handleDetail = (item) => {
    openModal(true, { notice: item });
  };
</script>
