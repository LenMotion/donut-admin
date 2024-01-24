<template>
  <Card title="快捷导航">
    <CardGrid
      v-if="userInfo.quickNavMenus && userInfo.quickNavMenus.length > 0"
      v-for="item in userInfo.quickNavMenus"
      :key="item.title"
      @click="handleNav(item)"
    >
      <span class="flex flex-col items-center">
        <Icon v-if="item.icon" :icon="item.icon" :color="item.color" size="20" />
        <span class="text-md mt-2 truncate">{{ item.title }}</span>
      </span>
    </CardGrid>
    <Empty description="未配置快捷导航，请在个人设置中心配置" v-else />
  </Card>
</template>
<script lang="ts" setup>
  import { Card, CardGrid,Empty } from 'ant-design-vue';
  import Icon from '@/components/Icon/Icon.vue';

  import { useUserStore } from '@/store/modules/user';
  import { computed } from 'vue';
  import { useRouter } from 'vue-router';

  const router = useRouter();

  const userStore = useUserStore();

  const userInfo = computed(() => userStore.getUserInfo);

  const handleNav = (item: any) => {
    router.push({
      name: item.name,
    });
  };
</script>
