<template>
  <div>
    <BasicModal
      @register="registerModal"
      title="导入"
      :min-height="500"
      :show-ok-btn="false"
      cancel-text="关闭"
      :mask-closable="false"
    >
      <Spin :spinning="loading">
        <AUploadDragger
          name="file"
          :multiple="false"
          accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
          :show-upload-list="false"
          :custom-request="customRequest"
        >
          <p class="ant-upload-drag-icon">
            <inbox-outlined />
          </p>
          <p class="ant-upload-text">点击选择文件或拖拽文件到此处上传，导入只做新增操作</p>
        </AUploadDragger>

        <div class="text-center mt-20px" v-if="hasDownload">
          <div>
            <a-button color="success" type="link" class="ml-2" @click="downloadTemp"
              >下载模板</a-button
            >
          </div>
        </div>

        <div class="mt-20px text-center" v-show="uploadResult.getDone">
          <Row>
            <Col :span="8">
              <Statistic title="总条数" :value="uploadResult.total" />
            </Col>
            <Col :span="8">
              <Statistic title="成功" :value="uploadResult.success" />
            </Col>
            <Col :span="8">
              <Statistic title="失败" :value="uploadResult.failed" />
            </Col>
            <Col :span="24" class="mt-10px">
              <div class="mb-5px" v-for="item in uploadResult.failedMessage" :key="item">
                {{ item }}
              </div>
            </Col>
          </Row>
        </div>
      </Spin>
    </BasicModal>
  </div>
</template>
<script setup lang="ts">
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { Upload, Spin, Statistic, Row, Col } from 'ant-design-vue';
  import { InboxOutlined } from '@ant-design/icons-vue';
  import { PropType, ref, computed } from 'vue';
  import { downloadByUrl } from '@/utils/file/download';
  import { isFunction } from '@/utils/is';
  import { warn } from '@/utils/log';
  import { useMessage } from '@/hooks/web/useMessage';
  import { ResultEnum } from '@/enums/httpEnum';

  const props = defineProps({
    uploadApi: {
      type: Function as PropType<PromiseFn>,
      default: null,
      required: true,
    },
    templateApi: {
      type: Function as PropType<PromiseFn>,
      default: null,
      required: false,
    },
  });

  const uploadResult = ref({
    total: 0,
    success: 0,
    failed: 0,
    failedMessage: [],
    getDone: false,
  });

  const loading = ref(false);

  const AUploadDragger = Upload.Dragger;
  const { createMessage } = useMessage();

  const hasDownload = computed(() => props.templateApi && isFunction(props.templateApi));

  const [registerModal] = useModalInner(() => {
    uploadResult.value = {
      total: 0,
      success: 0,
      failed: 0,
      failedMessage: [],
      getDone: false,
    };
  });

  const downloadTemp = () => {
    const { templateApi } = props;
    if (!templateApi || !isFunction(templateApi)) {
      return warn('download template api must exist and be a function');
    }
    templateApi().then((url) => downloadByUrl({ url }));
  };

  const customRequest = async (e) => {
    console.log(e);
    const { uploadApi } = props;
    if (!uploadApi || !isFunction(uploadApi)) {
      return warn('upload api must exist and be a function');
    }

    loading.value = true;

    try {
      const { data } = await uploadApi({ data: {}, file: e.file });
      const { code, msg, result } = data;
      if (code !== ResultEnum.SUCCESS) {
        createMessage.error(msg);
      } else {
        uploadResult.value = result;
        uploadResult.value.getDone = true;
      }
    } finally {
      loading.value = false;
    }
  };
</script>
