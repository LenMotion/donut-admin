<template>
  <div>
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button type="primary" @click="handleCreate" v-auth="'system:job:save'"
          >新增定时任务</a-button
        >
      </template>
      <template #name="{ record }">
        <a-button type="link" @click="handleLog(record)">{{ record.name }}</a-button>
      </template>
      <template #status="{ record }">
        <BaseStatusSwitch
          :value="record.status"
          :api="statusApi"
          :id="record.id"
          @success="reload"
          perm-code="system:job:status"
        />
      </template>
      <template #action="{ record }">
        <TableAction
          :actions="[
            {
              icon: 'ri:run-fill',
              tooltip: '立即执行',
              auth: 'system:job:exec',
              popConfirm: {
                title: '是否确认立即执行',
                confirm: handleExec.bind(null, record),
              },
            },
            {
              icon: 'clarity:note-edit-line',
              auth: 'system:job:save',
              onClick: handleEdit.bind(null, record),
            },
            {
              icon: 'ant-design:delete-outlined',
              color: 'error',
              auth: 'system:job:remove',
              popConfirm: {
                title: '是否确认删除',
                confirm: handleDelete.bind(null, record),
              },
            },
          ]"
        />
      </template>
    </BasicTable>
    <JobDrawer @register="registerDrawer" @success="reload()" />
    <JobLogDrawer @register="registerLogDrawer" />
  </div>
</template>
<script lang="ts" setup>
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { listApi, deleteApi, statusApi, execApi } from '@/api/system/job';
  import { useDrawer } from '@/components/Drawer';
  import { columns, searchFormSchema } from './job.data';
  import JobDrawer from './JobDrawer.vue';
  import BaseStatusSwitch from '@/components/Donut/BaseStatusSwitch/index.vue';
  import { useMessage } from '@/hooks/web/useMessage';
  import JobLogDrawer from './JobLogDrawer.vue';

  defineOptions({ name: 'JobManagement' });

  const [registerDrawer, { openDrawer }] = useDrawer();
  const [registerLogDrawer, LogAction] = useDrawer();
  const [registerTable, { reload }] = useTable({
    title: '定时任务列表',
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
    actionColumn: {
      width: 120,
      title: '操作',
      dataIndex: 'action',
      slots: { customRender: 'action' },
      fixed: 'right',
    },
  });

  const { createMessage } = useMessage();

  function handleCreate() {
    openDrawer(true, {
      isUpdate: false,
    });
  }

  function handleEdit(record: Recordable) {
    if (record.status == '0') {
      createMessage.error('修改任务必须要停止任务！');
      return;
    }

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

  function handleExec(record: Recordable) {
    execApi(record.id).then(() => {
      createMessage.success('执行成功,请稍后再执行日志中查看执行结果');
    });
  }

  function handleLog(record: Recordable) {
    LogAction.openDrawer(true, {
      record,
    });
  }
</script>
