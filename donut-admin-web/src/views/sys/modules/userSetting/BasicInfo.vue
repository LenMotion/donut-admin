<template>
  <CollapseContainer title="基础信息" class="pb-10px">
    <Description size="middle" :column="4" :schema="userInfoSchema" :data="detail" />
  </CollapseContainer>
</template>

<script setup lang="ts">
  import { CollapseContainer } from '@/components/Container';
  import { Description } from '@/components/Description';
  import { detailApi } from '@/api/system/user';
  import { userInfoSchema } from './data';
  import { ref } from 'vue';
  import { useUserStore } from '@/store/modules/user';

  const detail = ref({});

  const userStore = useUserStore();
  const userInfo = userStore.getUserInfo;

  detailApi(userInfo.id).then((res) => {
    detail.value = res;
  });
</script>
