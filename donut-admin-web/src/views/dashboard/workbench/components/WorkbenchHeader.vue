<template>
  <div class="lg:flex">
    <Avatar :src="userinfo.avatarUrl || headerImg" :size="72" class="!mx-auto !block" />
    <div class="md:ml-6 flex flex-col justify-center md:mt-0 mt-2">
      <h1 class="md:text-lg text-md">{{ desc }}</h1>
      <span class="text-secondary">
        每一次的努力，都是成为更好版本的一步。坚持，你一定能迎来成功的时刻！
      </span>
    </div>
    <div class="flex flex-1 justify-end md:mt-0 mt-4">
      <img class="xl:h-72px h-30" src="../../../../assets/svg/illustration.svg" />
    </div>
  </div>
</template>
<script lang="ts" setup>
  import { computed, ref } from 'vue';
  import { Avatar } from 'ant-design-vue';
  import { useUserStore } from '@/store/modules/user';
  import headerImg from '@/assets/images/header.jpg';

  const userStore = useUserStore();
  const userinfo = computed(() => userStore.getUserInfo);

  const desc = ref('');

  function getTimeOfDay() {
    const currentTime = new Date();
    const currentHour = currentTime.getHours();

    if (currentHour >= 0 && currentHour < 6) {
      desc.value = `${userinfo.value.nickName}，凌晨了，要注意保持好的作息哦，早睡早起身体好！`;
    } else if (currentHour >= 6 && currentHour < 9) {
      desc.value = `${userinfo.value.nickName}，早上好！新的一天开始了，加油迎接挑战！`;
    } else if (currentHour >= 9 && currentHour < 12) {
      desc.value = `${userinfo.value.nickName}，上午好！保持专注，完成任务更加轻松！`;
    } else if (currentHour >= 12 && currentHour < 14) {
      desc.value = `${userinfo.value.nickName}，中午了，休息一下吧，吃个健康的午餐！`;
    } else if (currentHour >= 14 && currentHour < 18) {
      desc.value = `${userinfo.value.nickName}，下午好！继续努力，你正在取得进展！`;
    } else if (currentHour >= 18 && currentHour < 21) {
      desc.value = `${userinfo.value.nickName}，傍晚了，适度的休息有助于提高工作效率！`;
    } else {
      desc.value = `${userinfo.value.nickName}，深夜了，注意保重身体，早点休息吧！`;
    }
  }

  getTimeOfDay();
</script>
