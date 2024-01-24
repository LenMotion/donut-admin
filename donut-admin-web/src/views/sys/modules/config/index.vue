<template>
  <PageWrapper title="系统相关设置">
    <Card>
      <Tabs tab-position="left" :tabBarStyle="tabBarStyle">
        <template v-for="item in settingList" :key="item.key">
          <TabPane :tab="item.name">
            <component :is="tabs[item.component]" />
          </TabPane>
        </template>
      </Tabs>
    </Card>
  </PageWrapper>
</template>

<script setup lang="ts">
  import { PageWrapper } from '@/components/Page';
  import { Tabs, Card } from 'ant-design-vue';

  import ConfigForm from './components/ConfigForm.vue';
  import ConfigTable from './components/ConfigTable.vue';

  import { settingList } from './components/config.data';

  defineOptions({ name: 'ConfigManagement' });

  const tabs = {
    ConfigForm,
    ConfigTable,
  };

  const TabPane = Tabs.TabPane;
  const tabBarStyle = {
    width: '120px',
  };
</script>
