<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    :title="isUpdate ? '更新信息' : '新增信息'"
    :mask-closable="false"
    width="80%"
    @ok="handleSubmit"
    :showOkBtn="stepCurrent === 1"
    :keyboard="false"
  >
    <template #footer>
      <Space wrap>
        <a-button @click="closeModal">取消</a-button>
        <a-button v-if="stepCurrent === 1" type="warning" @click="stepCurrent = 0">上一步</a-button>
        <a-button v-if="stepCurrent === 1" type="primary" @click="handleSubmit">保存</a-button>
      </Space>
    </template>
    <div class="w-550px my-10px mx-auto">
      <Steps :current="stepCurrent" :items="[{ title: '基本信息' }, { title: '字段信息' }]" />
    </div>
    <div class="mt-30px">
      <ColumnForm v-show="stepCurrent === 0" ref="columnFormRef" @next-step="handleNextStep" />
      <ColumnTable v-show="stepCurrent === 1" ref="columnTableRef" />
    </div>
  </BasicModal>
</template>

<script setup lang="ts">
  import { ref } from 'vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { tableColumnsByTableApi, saveApi, tableColumnsApi } from '@/api/gen/code';
  import { useMessage } from '@/hooks/web/useMessage';
  import { Steps } from 'ant-design-vue';
  import ColumnTable from './components/ColumnTable.vue';
  import ColumnForm from './components/ColumnForm.vue';
  import { Space } from 'ant-design-vue';

  const { createMessage: msg } = useMessage();
  const isUpdate = ref(true);
  const emit = defineEmits(['success', 'register']);

  const stepCurrent = ref(0);
  const columnTableRef = ref<any>(null);
  const columnFormRef = ref<any>(null);
  const record = ref<Recordable>({});

  const [registerModal, { setModalProps, closeModal }] = useModalInner((data) => {
    isUpdate.value = data?.isUpdate;
    stepCurrent.value = 0;
    columnFormRef.value?.resetFields();
    columnTableRef.value?.resetTable([]);
    if (data?.isUpdate) {
      columnFormRef.value?.setFieldsValue({ ...data.record });
    }
  });

  /**
   * 下一步
   */
  const handleNextStep = (values: Recordable) => {
    record.value = values;
    stepCurrent.value = 1;
    const columns = columnTableRef.value?.getDataSource();

    if (columns == undefined || columns.length === 0) {
      if (isUpdate.value) {
        tableColumnsByTableApi(values.id).then((res) => {
          columnTableRef.value?.resetTable(res);
        });
      } else {
        tableColumnsApi(values.tableName).then((res) => {
          columnTableRef.value?.setTableData(res);
        });
      }
    }
  };

  /**
   * 保存
   */
  const handleSubmit = async () => {
    try {
      const values = record.value;
      values.columns = columnTableRef.value?.getDataSource();
      setModalProps({ confirmLoading: true });
      await saveApi(values);
      msg.success('保存成功');
      closeModal();
      emit('success');
    } finally {
      setModalProps({ confirmLoading: false });
    }
  };
</script>
