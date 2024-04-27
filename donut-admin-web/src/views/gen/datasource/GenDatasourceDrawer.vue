<template>
  <BasicDrawer
    v-bind="$attrs"
    @register="registerDrawer"
    showFooter
    :title="getTitle"
    width="500px"
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm" />
  </BasicDrawer>
</template>
<script lang="ts" setup>
  import { ref, computed, unref } from 'vue';
  import { BasicForm, useForm } from '@/components/Form/index';
  import { formSchema } from './genDatasource.data';
  import { BasicDrawer, useDrawerInner } from '@/components/Drawer';
  import { saveApi } from '@/api/gen/datasource';
  import { useMessage } from '@/hooks/web/useMessage';
  import JSEncrypt from 'jsencrypt';
  import { useGlobSetting } from '@/hooks/setting';

  const { publicKey } = useGlobSetting();
  const jsEncrypt = new JSEncrypt();
  jsEncrypt.setPublicKey(publicKey);

  defineOptions({ name: 'GenDatasourceDrawer' });

  const emit = defineEmits(['success', 'register']);
  const isUpdate = ref(true);

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

    if (unref(isUpdate)) {
      console.log(jsEncrypt.decrypt(data.record.password));
      setFieldsValue({ ...data.record, password: '' });
    }
  });

  const getTitle = computed(() => (!unref(isUpdate) ? '新增数据源' : '编辑数据源'));

  async function handleSubmit() {
    try {
      setDrawerProps({ confirmLoading: true });
      const values = await validate();
      values.password = values.password ? jsEncrypt.encrypt(values.password) : null;
      await saveApi(values);

      createMessage.success('保存成功');
      closeDrawer();
      emit('success');
    } finally {
      setDrawerProps({ confirmLoading: false });
    }
  }
</script>
