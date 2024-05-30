<template>
  <div>
    <Popover trigger="hover" title="提示" v-model:open="visible">
      <template #content>
        <div class="flex">
          <p class="h-32px line-height-32px">请点击</p>
          <a-button type="link" @click="openExportLogModal()">这里</a-button>
          <p class="h-32px line-height-32px">查看进度、下载文件!</p>
        </div>
      </template>
      <a-button type="warning" v-auth="perms" @click="handleExport" :loading="loading">
        {{ canExport ? text : `${time}秒后重试` }}
      </a-button>
    </Popover>

    <ExportLogModal @register="registerModal" />
  </div>
</template>

<script setup lang="ts">
  import { Popover } from 'ant-design-vue';
  import { ref } from 'vue';
  import { useMessage } from '@/hooks/web/useMessage';
  import ExportLogModal from './ExportLogModal.vue';
  import { useModal } from '@/components/Modal';

  const loading = ref(false);
  const visible = ref(false);
  const canExport = ref(true);
  const time = ref(120);
  const timer = ref<any>(null);

  const { createMessage } = useMessage();
  const [registerModal, { openModal: openExportLogModal }] = useModal();

  const props = defineProps({
    text: {
      type: String,
      required: true,
    },
    perms: {
      type: String,
      requeired: false,
    },
    doExport: {
      type: Function,
      required: true,
    },
  });

  const handleExport = () => {
    if (!canExport.value) {
      return createMessage.warning('请勿高频执行导出操作...');
    }
    props.doExport();
    visible.value = true;
    createMessage.success('导出成功');
    canExport.value = false;
    timer.value = setInterval(() => {
      time.value = time.value - 1;
      if (time.value <= 0) {
        time.value = 120;
        canExport.value = true;
        clearInterval(timer.value);
      }
    }, 1000);
  };
</script>

<style scoped lang="scss">
  .flex {
    :deep(.ant-btn-link) {
      padding: 4px 2px;
    }
  }
</style>
