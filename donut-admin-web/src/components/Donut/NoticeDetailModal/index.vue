<template>
  <div>
    <BasicModal
      v-bind="$attrs"
      @register="registerModal"
      :title="noticeInfo.noticeTitle"
      width="70%"
      :show-ok-btn="false"
      :show-cancel-btn="false"
      :min-height="500"
    >
      <h1 class="text-center blod">{{ noticeInfo.noticeTitle }}</h1>
      <div class="text-center mt-10px color-gray">{{ noticeInfo.publishTime }}</div>
      <div class="mt-20px" v-html="noticeInfo.noticeContent"> </div>
    </BasicModal>
  </div>
</template>

<script setup lang="ts">
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { ref } from 'vue';
  import { readApi } from '@/api/system/notice';

  const emit = defineEmits(['register', 'read']);
  const noticeInfo = ref({
    noticeTitle: '',
    publishTime: '',
    noticeContent: '',
  });

  const [registerModal] = useModalInner((data) => {
    noticeInfo.value = data.notice;

    if (!data.notice.read) {
      readApi(data.notice.id).then(() => {
        emit('read');
      });
    }
  });
</script>
