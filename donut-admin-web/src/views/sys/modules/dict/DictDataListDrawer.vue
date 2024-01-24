<template>
  <BasicDrawer
    v-bind="$attrs"
    @register="registerDrawer"
    showFooter
    title="字典信息"
    width="60%"
    @ok="closeListModal"
  >
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button type="primary" @click="handleCreate" v-auth="'system:dict:add'"
          >新增字典</a-button
        >
      </template>
      <template #dictLabel="{ record }">
        <a-tag :color="record.listClass">{{ record.dictLabel }}</a-tag>
      </template>
      <template #status="{ record }">
        <BaseStatusSwitch
          :value="record.status"
          :api="statusApi"
          :id="record.id"
          @success="reload"
          perm-code="system:dict:status"
        />
      </template>
      <template #action="{ record }">
        <TableAction
          :actions="[
            {
              icon: 'clarity:note-edit-line',
              auth: 'system:dict:edit',
              onClick: handleEdit.bind(null, record),
            },
            {
              icon: 'ant-design:delete-outlined',
              color: 'error',
              auth: 'system:dict:remove',
              popConfirm: {
                title: '是否确认删除',
                confirm: handleDelete.bind(null, record),
              },
            },
          ]"
        />
      </template>
    </BasicTable>
  </BasicDrawer>
  <DictDataDrawer @register="registerEditDrawer" @success="reload()" />
</template>
<script lang="ts" setup>
  import { reactive, unref } from 'vue';
  import { BasicTable, useTable, TableAction } from '@/components/Table/index';
  import { columns, searchFormSchema } from './dictData.data';
  import { BasicDrawer, useDrawerInner, useDrawer } from '@/components/Drawer';
  import DictDataDrawer from './DictDataDrawer.vue';
  import { Tag as ATag } from 'ant-design-vue';
  import BaseStatusSwitch from '@/components/Donut/BaseStatusSwitch/index.vue';

  import { listApi, deleteApi, statusApi } from '@/api/system/dictData';

  import { useMessage } from '@/hooks/web/useMessage';

  const { createMessage } = useMessage();

  defineOptions({ name: 'DictDataListManagement' });

  const searchInfo = reactive<Recordable>({});
  const [registerTable, { reload }] = useTable({
    title: '字典列表',
    api: listApi,
    columns,
    formConfig: {
      labelWidth: 120,
      schemas: searchFormSchema,
    },
    useSearchForm: true,
    showTableSetting: true,
    bordered: true,
    showIndexColumn: true,
    immediate: false,
    canResize: false,
    beforeFetch: (params) => {
      params.dictKey = searchInfo.dictKey;
      return params;
    },
    actionColumn: {
      width: 80,
      title: '操作',
      dataIndex: 'action',
      slots: { customRender: 'action' },
      fixed: 'right',
    },
  });

  defineEmits(['register']);

  const [registerEditDrawer, { openDrawer }] = useDrawer();

  const [registerDrawer, { setDrawerProps, closeDrawer }] = useDrawerInner(async (data) => {
    setDrawerProps({ confirmLoading: false });
    searchInfo.dictKey = unref(data).record.dictKey;
    reload();
  });

  function closeListModal() {
    closeDrawer();
  }

  function handleCreate() {
    openDrawer(true, {
      isUpdate: false,
      record: {
        dictKey: searchInfo.dictKey,
      },
    });
  }

  function handleEdit(record: Recordable) {
    openDrawer(true, {
      record,
      isUpdate: true,
    });
  }

  function handleDelete(record: Recordable) {
    deleteApi(record.id).then(() => {
      reload();
      createMessage.success('删除成功');
    });
  }
</script>
