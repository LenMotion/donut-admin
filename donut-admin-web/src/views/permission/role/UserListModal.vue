<template>
  <BasicModal
    @register="registerModal"
    width="700px"
    v-bind="$attrs"
    :title="title"
    @ok="handleChoise"
  >
    <BasicTable @register="registerTable" />
  </BasicModal>
</template>

<script setup lang="ts">
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { BasicTable, useTable } from '@/components/Table';
  import { unbindUserListApi, roleUserBindApi } from '@/api/system/role';
  import { ref } from 'vue';
  import { useMessage } from '@/hooks/web/useMessage';

  const title = ref('');
  const { createMessage } = useMessage();
  const emit = defineEmits(['success']);

  const [registerModal, { closeModal }] = useModalInner((data) => {
    title.value = `用户列表`;
    const roleId = data.record.roleId;
    getForm().setFieldsValue({ roleId, keyword: '' });
    reload({ searchInfo: { roleId } });
  });

  const [registerTable, { reload, getForm, getSelectRowKeys }] = useTable({
    api: unbindUserListApi,
    immediate: false,
    useSearchForm: true,
    canResize: false,
    rowKey: 'id',
    columns: [
      {
        title: '用户名',
        dataIndex: 'username',
      },
      {
        title: '昵称',
        dataIndex: 'nickName',
      },
      {
        title: '所属部门',
        dataIndex: 'transMap.deptName',
        customRender: ({ record }) => record?.transMap?.deptName,
      },
    ],
    formConfig: {
      labelWidth: 90,
      showResetButton: false,
      schemas: [
        {
          field: 'roleId',
          label: '角色ID',
          component: 'Input',
          colProps: { span: 12 },
          show: false,
        },
        {
          field: 'keyword',
          label: '用户名/昵称',
          component: 'Input',
          colProps: { span: 12 },
        },
      ],
    },
    showTableSetting: false,
    rowSelection: {
      type: 'checkbox',
    },
  });

  function handleChoise() {
    const userIds = getSelectRowKeys();
    console.log(userIds);
    if (userIds.length == 0) {
      createMessage.error('请选择用户');
      return;
    }

    const values = getForm().getFieldsValue();

    const data = {
      roleId: values.roleId,
      userIds,
    };
    roleUserBindApi(data).then(() => {
      emit('success');
      closeModal();
    });
  }
</script>
