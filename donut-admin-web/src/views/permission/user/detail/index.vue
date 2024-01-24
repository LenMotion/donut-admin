<template>
  <div>
    <Spin :spinning="spinning">
      <PageWrapper title="用户详情">
        <template #footer>
          <Tabs v-model:active-key="activeKey">
            <Tabs.TabPane key="1" tab="基本信息" />
            <Tabs.TabPane key="2" tab="权限信息" />
            <Tabs.TabPane key="3" tab="操作日志" />
          </Tabs>
        </template>
        <Description
          v-show="activeKey == '1'"
          size="middle"
          title="用户信息"
          :column="4"
          :schema="userInfoSchema"
          :data="detail"
        />
        <Row v-show="activeKey == '2'" :gutter="20">
          <Col :span="8">
            <Card title="菜单权限">
              <BasicTree
                :toolbar="true"
                :defaultExpandAll="true"
                :treeData="menuTree"
                :fieldNames="{ key: 'id' }"
              />
            </Card>
          </Col>
          <Col :span="16">
            <Card title="登录日志">
              <Timeline mode="alternate">
                <TimelineItem
                  v-for="item in loginLogList"
                  :key="item.id"
                  :color="item.status == 0 ? 'green' : 'red'"
                  >{{ item.msg + ' 【' + item.loginTime + '】' }}</TimelineItem
                >
              </Timeline>
            </Card>
          </Col>
        </Row>
        <BasicTable v-show="activeKey == '3'" @register="registerTable" />
      </PageWrapper>
    </Spin>
  </div>
</template>

<script setup lang="ts">
  import { Description } from '@/components/Description';
  import { BasicTable, useTable } from '@/components/Table';
  import { PageWrapper } from '@/components/Page';
  import { Spin, Card, Row, Col, Timeline, TimelineItem, Tabs } from 'ant-design-vue';

  import { detailApi } from '@/api/system/user';
  import { userMenuApi } from '@/api/system/menu';
  import { loginLogListApi, operationLogListApi } from '@/api/system/log';
  import { listToTree } from '@/utils/helper/treeHelper';
  import { userInfoSchema, columns } from './data';
  import { useRoute } from 'vue-router';
  import { ref } from 'vue';
  import { BasicTree } from '@/components/Tree';

  defineOptions({ name: 'UserDetail' });

  const activeKey = ref('1');

  const route = useRoute();
  const userId = route.params.id;

  const spinning = ref(true);
  const detail = ref({});
  const menuTree = ref([]);
  const loginLogList = ref<Recordable[]>([]);

  async function getData() {
    detail.value = await detailApi(userId);
    const menuList = await userMenuApi(userId);
    menuTree.value = listToTree(menuList, { id: 'id', children: 'children', pid: 'parentId' });
    // @ts-ignore
    const { items } = await loginLogListApi({ userId });
    loginLogList.value = items;
    spinning.value = false;
  }

  getData();

  const [registerTable] = useTable({
    api: operationLogListApi,
    title: '操作日志',
    rowKey: 'id',
    columns,
    searchInfo: { userId },
    useSearchForm: false,
    showTableSetting: false,
    bordered: true,
    canResize: false,
  });
</script>
