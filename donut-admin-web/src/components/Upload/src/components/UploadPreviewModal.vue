<template>
  <BasicModal
    width="800px"
    :title="t('component.upload.preview')"
    class="upload-preview-modal"
    v-bind="$attrs"
    @register="register"
    :showOkBtn="false"
    cancelText="关闭"
  >
    <FileList :dataSource="fileListRef" :columns="columns" :actionColumn="actionColumn" />
  </BasicModal>
</template>
<script lang="ts" setup>
  import { watch, ref } from 'vue';
  import FileList from './FileList.vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { previewProps } from '../props';
  import { downloadByUrl } from '@/utils/file/download';
  import { createPreviewColumns, createPreviewActionColumn } from './data';
  import { useI18n } from '@/hooks/web/useI18n';
  import { isArray } from '@/utils/is';
  import { PreviewFileInfo, UploadResultStatus } from '../types/typing';

  const props = defineProps(previewProps);

  const emit = defineEmits(['list-change', 'register', 'delete']);

  const columns = createPreviewColumns() as any[];
  const actionColumn = createPreviewActionColumn({ handleRemove, handleDownload }) as any;

  const [register] = useModalInner();
  const { t } = useI18n();

  const fileListRef = ref<PreviewFileInfo[]>([]);
  watch(
    () => props.value,
    (value) => {
      if (!isArray(value)) value = [];
      fileListRef.value = value.map((item) => {
        return {
          ...item,
          thumbUrl: item.url,
          status: UploadResultStatus.SUCCESS,
          percent: 100,
        };
      });
    },
    { immediate: true },
  );

  // 删除
  function handleRemove(record: PreviewFileInfo) {
    console.log(record);
    const index = fileListRef.value.findIndex((item) => item.uid === record.uid);
    if (index !== -1) {
      const removed = fileListRef.value.splice(index, 1);
      emit('delete', removed[0].uid);
      emit(
        'list-change',
        fileListRef.value.map((item) => {
          return {
            ext: item.ext,
            name: item.name,
            uid: item.uid,
            url: item.url,
            size: item.size,
          };
        }),
      );
    }
  }

  // 下载
  function handleDownload(record: PreviewFileInfo) {
    const { url = '' } = record;
    downloadByUrl({ url });
  }
</script>
<style lang="less">
  .upload-preview-modal {
    .ant-upload-list {
      display: none;
    }

    .ant-table-wrapper .ant-spin-nested-loading {
      padding: 0;
    }
  }
</style>
