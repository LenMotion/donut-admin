<template>
  <div>
    <Space>
      <a-button type="primary" @click="openUploadModal" preIcon="carbon:cloud-upload">
        {{ t('component.upload.upload') }}
      </a-button>
      <Tooltip placement="bottom" v-if="showPreview">
        <template #title>
          {{ t('component.upload.uploaded') }}
          <template v-if="fileList.length">
            {{ fileList.length }}
          </template>
        </template>
        <a-button @click="openPreviewModal">
          <Icon icon="bi:eye" />
          <template v-if="fileList.length && showPreviewNumber">
            {{ fileList.length }}
          </template>
        </a-button>
      </Tooltip>
    </Space>
    <UploadModal
      v-bind="bindValue"
      :previewFileList="fileList"
      :fileListOpenDrag="fileListOpenDrag"
      :fileListDragOptions="fileListDragOptions"
      @register="registerUploadModal"
      @change="handleChange"
      @delete="handleDelete"
    />

    <UploadPreviewModal
      :value="fileList"
      @register="registerPreviewModal"
      @list-change="handlePreviewChange"
      @delete="handlePreviewDelete"
    />
  </div>
</template>
<script lang="ts" setup>
  import { ref, watch, unref, computed, useAttrs } from 'vue';
  import { Recordable } from '@vben/types';
  import Icon from '@/components/Icon/Icon.vue';
  import { Tooltip, Space } from 'ant-design-vue';
  import { useModal } from '@/components/Modal';
  import { uploadContainerProps } from './props';
  import { omit } from 'lodash-es';
  import { useI18n } from '@/hooks/web/useI18n';
  import { isArray } from '@/utils/is';
  import UploadModal from './components/UploadModal.vue';
  import UploadPreviewModal from './components/UploadPreviewModal.vue';
  import { fileInfoApi } from '@/api/system/upload';
  import { FileInfo } from '@/api/system/model/uploadModel';

  defineOptions({ name: 'BasicUpload' });

  const props = defineProps(uploadContainerProps);

  const emit = defineEmits(['change', 'delete', 'preview-delete', 'update:value']);

  const attrs = useAttrs();
  const { t } = useI18n();
  // 上传modal
  const [registerUploadModal, { openModal: openUploadModal }] = useModal();

  //   预览modal
  const [registerPreviewModal, { openModal: openPreviewModal }] = useModal();

  const fileList = ref<FileInfo[]>([]);

  const showPreview = computed(() => {
    const { emptyHidePreview } = props;
    if (!emptyHidePreview) return true;
    return emptyHidePreview ? fileList.value.length > 0 : true;
  });

  const bindValue = computed(() => {
    const value = { ...attrs, ...props };
    return omit(value, 'onChange');
  });

  watch(
    () => props.value,
    (value = []) => {
      fileList.value = [];
      if (value && value.length > 0) {
        fileInfoApi(value).then((res) => {
          console.log(res);
          fileList.value = res;
        });
      }
    },
    { immediate: true },
  );

  // 上传modal保存操作
  function handleChange(files: FileInfo[]) {
    console.log(files);
    fileList.value = [...unref(fileList), ...(files || [])];
    emitUpdate();
  }

  // 预览modal保存操作
  function handlePreviewChange(files: FileInfo[]) {
    fileList.value = [...(files || [])];
    emitUpdate();
  }

  const emitUpdate = () => {
    console.log(fileList.value);
    const ids = fileList.value.map((item) => item.uid);
    const value = isArray(props.value) ? ids : ids.join(',');
    emit('update:value', value);
    emit('change', value);
  };

  function handleDelete(record: Recordable<any>) {
    emit('delete', record);
  }

  function handlePreviewDelete(uid: string) {
    emit('preview-delete', uid);
  }
</script>
