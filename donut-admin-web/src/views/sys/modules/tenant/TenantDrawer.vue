<template>
  <BasicDrawer
    v-bind="$attrs"
    @register="registerDrawer"
    showFooter
    :title="getTitle"
    width="500px"
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm">
      <template #menu="{ model, field }">
        <BasicTree
          ref="menuTreeRef"
          v-model:value="model[field]"
          @check="checkTree"
          :treeData="treeData"
          :fieldNames="{ title: 'title', key: 'id' }"
          :checkedKeys="checkedKeys"
          checkable
          toolbar
          title="菜单分配"
        />
      </template>
    </BasicForm>
  </BasicDrawer>
</template>
<script lang="ts" setup>
  import { ref, computed, unref } from 'vue';
  import { BasicForm, useForm } from '@/components/Form/index';
  import { formSchema } from './tenant.data';
  import { BasicDrawer, useDrawerInner } from '@/components/Drawer';
  import { menuTreeApi } from '@/api/system/menu';

  import { saveApi, menuIdListApi } from '@/api/system/tenant';
  import { useMessage } from '@/hooks/web/useMessage';
  import { TreeItem, BasicTree } from '@/components/Tree';

  defineOptions({ name: 'SysTenantDrawer' });

  const emit = defineEmits(['success', 'register']);
  const isUpdate = ref(true);

  const treeData = ref<TreeItem[]>([]);
  const checkedKeys = ref<string[]>([]);
  const menuTreeRef = ref(null);
  let checkedMenu: number[] = [];
  let halfCheckedMenu = [];

  const getMenuTree = async () => {
    const treeDataResult = await menuTreeApi();
    treeData.value = treeDataResult as any as TreeItem[];
  };
  getMenuTree();

  const { createMessage } = useMessage();

  const [registerForm, { resetFields, setFieldsValue, validate }] = useForm({
    labelWidth: 90,
    schemas: formSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 24 },
  });

  const [registerDrawer, { setDrawerProps, closeDrawer }] = useDrawerInner(async (data) => {
    resetFields();
    setDrawerProps({ confirmLoading: false });
    isUpdate.value = !!data?.isUpdate;

    checkedMenu = [];
    halfCheckedMenu = [];
    if (unref(isUpdate)) {
      const { menuIds, halfMenuIds } = await menuIdListApi(data.record.id);
      checkedMenu = menuIds;
      halfCheckedMenu = halfMenuIds;
      data.record.menuIds = checkedMenu;
      setFieldsValue({ ...data.record });
    }
  });

  const getTitle = computed(() => (!unref(isUpdate) ? '新增租户' : '编辑租户'));

  async function handleSubmit() {
    try {
      setDrawerProps({ confirmLoading: true });
      const values = await validate();
      values.menuIds = checkedMenu;
      values.halfMenuIds = halfCheckedMenu;
      await saveApi(values);

      createMessage.success('保存成功');
      closeDrawer();
      emit('success');
    } finally {
      setDrawerProps({ confirmLoading: false });
    }
  }

  const checkTree = (checkData, e) => {
    checkedMenu = checkData;
    halfCheckedMenu = e.halfCheckedKeys;
  };
</script>
