<template>
  <ConfigProvider :locale="getAntdLocale" :theme="themeConfig">
    <AppProvider>
      <RouterView />
    </AppProvider>
  </ConfigProvider>
</template>

<script lang="ts" setup>
  import { AppProvider } from '@/components/Application';
  import { useTitle } from '@/hooks/web/useTitle';
  import { useLocale } from '@/locales/useLocale';
  import { ConfigProvider } from 'ant-design-vue';

  import { useDarkModeTheme } from '@/hooks/setting/useDarkModeTheme';
  import 'dayjs/locale/zh-cn';
  import { computed } from 'vue';
  import { useGlobSetting } from '@/hooks/setting';
  import { useAppStore } from './store/modules/app';

  const appStore = useAppStore();
  // support Multi-language
  const { getAntdLocale } = useLocale();

  if (!appStore.getTenantId) {
    const { tenantId } = useGlobSetting();
    appStore.setTenantId(tenantId.split(',')[0]);
  } else {
    appStore.setTenantId(appStore.getTenantId);
  }

  const { isDark, darkTheme } = useDarkModeTheme();

  const themeConfig = computed(() =>
    Object.assign(
      {
        token: {
          colorPrimary: '#0960bd',
          colorSuccess: '#55D187',
          colorWarning: '#EFBD47',
          colorError: '#ED6F6F',
          colorInfo: '#0960bd',
        },
      },
      isDark.value ? darkTheme : {},
    ),
  );
  // Listening to page changes and dynamically changing site titles
  useTitle();
</script>
