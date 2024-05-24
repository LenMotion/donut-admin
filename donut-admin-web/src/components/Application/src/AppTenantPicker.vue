<template>
  <Dropdown
    v-if="tenantList.length > 1"
    placement="bottom"
    :trigger="['click']"
    :dropMenuList="tenantList"
    :selectedKeys="selectedKeys"
    @menu-event="handleTenantEvent"
    overlayClassName="app-locale-picker-overlay"
  >
    <span class="cursor-pointer flex items-center">
      <span class="ml-1">{{ getLocaleText }}</span>
    </span>
  </Dropdown>
</template>
<script lang="ts" setup>
  import { computed, ref, watchEffect } from 'vue';
  import { Dropdown, DropMenu } from '@/components/Dropdown';
  import { useGlobSetting } from '@/hooks/setting';
  import { baseInfoApi } from '@/api/system/tenant';
  import { useAppStore } from '@/store/modules/app';
  import { useMessage } from '@/hooks/web/useMessage';

  const appStore = useAppStore();
  const { tenantId } = useGlobSetting();
  const { createMessage } = useMessage();

  const tenantList = ref<any[]>([]);
  const selectedKeys = ref<string[]>([]);

  baseInfoApi(tenantId).then((res) => {
    if (res.length == 0) {
      createMessage.error('租户信息查询失败');
    } else {
      if (!appStore.getTenantId) {
        appStore.setTenantId(res[0].id);
      }
      res.forEach((e) => {
        tenantList.value.push({
          event: e.id,
          text: e.name,
        });
      });
    }
  });

  watchEffect(() => {
    selectedKeys.value = [appStore.getTenantId];
  });

  const getLocaleText = computed(() => {
    const key = selectedKeys.value[0];
    if (!key) {
      return '';
    }
    return tenantList.value.find((item) => item.event === key)?.text;
  });

  const handleTenantEvent = (menu: DropMenu) => {
    appStore.setTenantId(menu.event as string);
  };
</script>

<style lang="less">
  .app-locale-picker-overlay {
    .ant-dropdown-menu-item {
      min-width: 160px;
    }
  }
</style>
