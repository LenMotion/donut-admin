<template>
  <div>
    <BasicTable @register="registerTable">
      <template #action="{ record }">
        <TableAction
          :actions="[
            {
              icon: 'ant-design:delete-outlined',
              color: 'error',
              auth: 'monitor:onlineUser:kickOut',
              popConfirm: {
                title: '是否确认强制退出？因sa-token的设定，踢人后在线列表还是会展示该token！',
                confirm: handleLogout.bind(null, record),
              },
            },
          ]"
        />
      </template>
    </BasicTable>
  </div>
</template>
<script lang="ts" setup>
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { listApi, kickOutApi } from '@/api/monitor/onlineUser';

  import { columns } from './onlineUser.data';

  defineOptions({ name: 'OnlineUserManagement' });

  const [registerTable, { reload }] = useTable({
    title: '在线用户列表',
    api: listApi,
    columns,
    useSearchForm: true,
    showTableSetting: true,
    bordered: true,
    showIndexColumn: true,
    actionColumn: {
      width: 80,
      title: '操作',
      dataIndex: 'action',
      slots: { customRender: 'action' },
      fixed: 'right',
    },
  });

  const handleLogout = (record: Recordable) => {
    kickOutApi(record.id).then(() => {
      reload();
    });
  };
</script>
