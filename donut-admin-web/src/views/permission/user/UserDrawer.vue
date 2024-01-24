<template>
  <BasicDrawer
    v-bind="$attrs"
    @register="registerDrawer"
    showFooter
    :title="getTitle"
    width="50%"
    @ok="handleSubmit"
    :mask-closable="false"
  >
    <Tabs v-model:activeKey="activeKey">
      <TabPane tab="基本信息" key="basic" />
      <TabPane tab="其他信息" key="other" />
    </Tabs>
    <div v-show="activeKey == 'basic'">
      <BasicForm @register="registerForm">
        <template #avatar="{ model }">
          <CropperAvatar :uploadApi="uploadApi as any" :value="model.transMap?.avatarUrl" />
        </template>
      </BasicForm>
      <BasicTable @register="registerTable">
        <template #toolbar>
          <a-button type="primary" @click="handleAddUserDept">添加任职</a-button>
        </template>
        <template #deptId="{ record }">
          <TreeSelect
            placeholder="请选择组织"
            :tree-data="treeData"
            v-model:value="record.deptId"
            :field-names="fieldNames"
            @change="handleDeptChange(record, $event)"
          />
        </template>
        <template #postId="{ record }">
          <ASelect
            :options="record.postOptions"
            v-model:value="record.postId"
            :field-names="{ label: 'postName', value: 'id' }"
            placeholder="请选择岗位"
          />
        </template>
        <template #action="{ record }">
          <TableAction
            :actions="[
              {
                icon: 'ant-design:delete-outlined',
                color: 'error',
                auth: 'system:user:remove',
                popConfirm: {
                  title: '是否确认删除',
                  confirm: deleteTableDataRecord.bind(null, record.key),
                },
              },
            ]"
          />
        </template>
      </BasicTable>
    </div>
    <BasicForm v-show="activeKey == 'other'" @register="registerOtherForm" />
  </BasicDrawer>
</template>
<script lang="ts" setup>
  import { ref, computed, unref } from 'vue';
  import { BasicForm, useForm, FormProps } from '@/components/Form/index';
  import { formSchema, deptColumns, fieldNames, otherFormSchema } from './user.data';
  import { BasicDrawer, useDrawerInner } from '@/components/Drawer';
  import { BasicTable, useTable, TableAction } from '@/components/Table';

  import { saveApi, detailApi } from '@/api/system/user';
  import { optionsApi } from '@/api/system/post';
  import { enableTreeApi } from '@/api/system/dept';
  import { useMessage } from '@/hooks/web/useMessage';
  import { TreeSelect, Select as ASelect, Tabs, TabPane } from 'ant-design-vue';
  import { CropperAvatar } from '@/components/Cropper';
  import { uploadApi } from '@/api/sys/upload';

  defineOptions({ name: 'UserDrawer' });

  const emit = defineEmits(['success', 'register']);
  const isUpdate = ref(true);
  const activeKey = ref('basic');
  const treeData = ref<Recordable[]>([]);

  const { createMessage } = useMessage();

  const [
    registerTable,
    { insertTableDataRecord, deleteTableDataRecord, getDataSource, setTableData },
  ] = useTable({
    title: '其他任职',
    immediate: false,
    columns: deptColumns,
    showIndexColumn: false,
    pagination: false,
    actionColumn: {
      width: 60,
      title: '操作',
      dataIndex: 'action',
      slots: { customRender: 'action' },
    },
  });

  const formProps: FormProps = {
    labelWidth: 90,
    layout: 'vertical',
    showActionButtonGroup: false,
    rowProps: { gutter: 15 },
    baseColProps: { lg: 12, md: 24 },
  };

  enableTreeApi().then((res) => (treeData.value = res));

  const [registerForm, { resetFields, setFieldsValue, validate, updateSchema }] = useForm({
    ...formProps,
    schemas: formSchema,
  });
  const [registerOtherForm, otherFormAction] = useForm({
    ...formProps,
    schemas: otherFormSchema,
  });

  const [registerDrawer, { setDrawerProps, closeDrawer }] = useDrawerInner(async (data) => {
    resetFields();
    setDrawerProps({ confirmLoading: false });
    isUpdate.value = !!data?.isUpdate;

    updateSchema([
      {
        field: 'deptId',
        componentProps: () => {
          return {
            treeData: treeData.value,
            fieldNames,
            onChange: async (value) => {
              updateSchema([
                {
                  field: 'postId',
                  componentProps: { options: value ? await optionsApi({ deptId: value }) : [] },
                },
              ]);
            },
          };
        },
      },
    ]);

    setTableData([]);
    if (unref(isUpdate)) {
      const detail = await detailApi(data.record.id);

      setFieldsValue({ ...detail });
      otherFormAction.setFieldsValue({ ...detail });
      setTableData(detail.userDeptList);

      updateSchema([
        {
          field: 'postId',
          componentProps: { options: await optionsApi({ deptId: detail.deptId }) },
        },
      ]);
    }
  });

  const getTitle = computed(() => (!unref(isUpdate) ? '新增用户' : '编辑用户'));

  async function handleSubmit() {
    try {
      setDrawerProps({ confirmLoading: true });
      const values = await validate();
      const otherValues = await otherFormAction.validate();
      const userDeptList = getDataSource();

      const deptList: Recordable[] = [];
      for (let i in userDeptList) {
        const { deptId, postId } = userDeptList[i];
        if (!deptId || !postId) {
          return createMessage.error('请选择部门及岗位');
        }

        deptList.push({ deptId, postId });
      }

      await saveApi({ ...values, ...otherValues, userDeptList: deptList });
      createMessage.success('保存成功');
      closeDrawer();
      emit('success');
    } finally {
      setDrawerProps({ confirmLoading: false });
    }
  }

  const handleAddUserDept = () => {
    insertTableDataRecord({ deptId: null, postId: null });
  };

  const handleDeptChange = async (record: Recordable, value) => {
    record.postOptions = value ? await optionsApi({ deptId: value }) : [];
  };
</script>

<style scoped lang="scss">
  :deep(.ant-tabs-tab) {
    padding: 0 0 12px;
  }
  :deep(.vben-basic-table) {
    height: 0;
  }
  :deep(.ant-tree-select),
  :deep(.ant-select) {
    width: 100%;
  }
</style>
