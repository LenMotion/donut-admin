<template>
  <ScrollContainer>
    <div ref="wrapperRef" :class="prefixCls">
      <Tabs tab-position="left" :tabBarStyle="tabBarStyle">
        <template v-for="item in settingList" :key="item.key">
          <TabPane :tab="item.name">
            <component :is="tabs[item.component]" />
          </TabPane>
        </template>
      </Tabs>
    </div>
  </ScrollContainer>
</template>

<script lang="ts" setup>
  import { Tabs } from 'ant-design-vue';
  import { ScrollContainer } from '@/components/Container';
  import { settingList } from './data';
  import BaseSetting from './BaseSetting.vue';
  import ChangePassword from './ChangePassword.vue';
  import BasicInfo from './BasicInfo.vue';

  defineOptions({ name: 'UserSettingManagement' });

  const TabPane = Tabs.TabPane;
  const tabs = {
    BaseSetting,
    ChangePassword,
    BasicInfo,
  };

  const prefixCls = 'account-setting';
  const tabBarStyle = {
    width: '220px',
  };
</script>
<style lang="less">
  .account-setting {
    margin: 12px;
    background-color: @component-background;

    .base-title {
      padding-left: 0;
    }

    .ant-tabs-tab-active {
      background-color: @item-active-bg;
    }
  }
</style>
