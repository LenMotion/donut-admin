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
  import { formSchema } from './role.data';
  import { BasicDrawer, useDrawerInner } from '@/components/Drawer';
  import { BasicTree, TreeItem } from '@/components/Tree';

  import { saveApi, menuIdApi } from '@/api/system/role';
  import { menuTreeApi } from '@/api/system/menu';
  import { useMessage } from '@/hooks/web/useMessage';

  defineOptions({ name: 'ConfigDrawer' });

  const treeData = ref<TreeItem[]>([]);
  const checkedKeys = ref<string[]>([]);
  const menuTreeRef = ref(null);
  let checkedMenu: number[] = [];
  let halfCheckedMenu = [];

  const emit = defineEmits(['success', 'register']);
  const isUpdate = ref(true);

  const { createMessage } = useMessage();

  const [registerForm, { resetFields, setFieldsValue, validate }] = useForm({
    labelWidth: 120,
    schemas: formSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 24 },
  });

  const [registerDrawer, { setDrawerProps, closeDrawer, changeLoading }] = useDrawerInner(
    async (data) => {
      try {
        resetFields();
        setDrawerProps({ confirmLoading: false });
        // 需要在setFieldsValue之前先填充treeData，否则Tree组件可能会报key not exist警告
        isUpdate.value = !!data?.isUpdate;

        changeLoading(true);
        const treeDataResult = await menuTreeApi();
        treeData.value = treeDataResult as any as TreeItem[];
        checkedMenu = [];
        halfCheckedMenu = [];
        if (unref(isUpdate)) {
          const { menuIds, halfMenuIds } = await menuIdApi(data.record.id);
          checkedMenu = menuIds;
          halfCheckedMenu = halfMenuIds;
        }
        data.record.menuIds = checkedMenu;

        setFieldsValue({ ...data.record });
      } finally {
        changeLoading(false);
      }
    },
  );

  const getTitle = computed(() => (!unref(isUpdate) ? '新增角色' : '编辑角色'));

  async function handleSubmit() {
    try {
      const values = await validate();
      setDrawerProps({ confirmLoading: true });
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
