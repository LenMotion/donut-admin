<template>
  <div>
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button type="primary" v-auth="'system:dict:add'" @click="handleCreate"
          >新增字典</a-button
        >
      </template>
      <template #dictKey="{ record }">
        <a-button type="link" @click="openDataDetail(record)">
          {{ record.dictKey }}
        </a-button>
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
    <DictTypeDrawer @register="registerDrawer" @success="reload()" />
    <DictDataListDrawer @register="registerDataListDrawer" />
  </div>
</template>
<script lang="ts" setup>
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { listApi, deleteApi, statusApi } from '@/api/system/dictType';

  import { useDrawer } from '@/components/Drawer';
  import DictTypeDrawer from './DictTypeDrawer.vue';
  import DictDataListDrawer from './DictDataListDrawer.vue';
  import BaseStatusSwitch from '@/components/Donut/BaseStatusSwitch/index.vue';

  import { columns, searchFormSchema } from './dictType.data';

  defineOptions({ name: 'DictManagement' });

  const [registerDrawer, { openDrawer }] = useDrawer();
  const [registerDataListDrawer, { openDrawer: openDataDrawer }] = useDrawer();
  const [registerTable, { reload }] = useTable({
    title: '字典列表',
    api: listApi,
    columns,
    formConfig: {
      labelWidth: 120,
      schemas: searchFormSchema,
      baseColProps: { xl: 8, xxl: 6 },
    },
    useSearchForm: true,
    showTableSetting: true,
    bordered: true,
    showIndexColumn: true,
    actionColumn: {
      width: 80,
      title: '操作',
      dataIndex: 'action',
      slots: { customRender: 'action' },
      fixed: undefined,
    },
  });

  function handleCreate() {
    openDrawer(true, {
      isUpdate: false,
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
    });
  }

  function openDataDetail(record: Recordable) {
    openDataDrawer(true, { record });
  }
</script>
