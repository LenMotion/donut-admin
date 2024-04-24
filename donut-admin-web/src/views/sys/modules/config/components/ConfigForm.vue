<template>
  <div>
    <BasicForm @register="registerForm" @submit="handleSubmit">
      <template #logo="{ model, field }">
        {{ field }}
      </template>
    </BasicForm>
  </div>
</template>

<script setup lang="ts">
  import { BasicForm, useForm } from '@/components/Form';

  import { systemConfigGetApi, systemConfigSaveApi } from '@/api/system/config';
  import { formSchema } from './config.data';
  import { useMessage } from '@/hooks/web/useMessage';

  const { createMessage } = useMessage();
  systemConfigGetApi().then((res) => {
    res.DEFAULT_MENU = res.DEFAULT_MENU ? res.DEFAULT_MENU.split(',') : [];
    setFieldsValue(res);
  });

  const handleSubmit = async () => {
    try {
      const values = await validate();
      setProps({ submitButtonOptions: { loading: true } });
      values.DEFAULT_MENU = values.DEFAULT_MENU ? values.DEFAULT_MENU.join(',') : '';
      await systemConfigSaveApi(values);
      createMessage.success('保存成功');
    } finally {
      setProps({ submitButtonOptions: { loading: false } });
    }
  };

  const [registerForm, { setFieldsValue, validate, setProps }] = useForm({
    schemas: formSchema,
    labelWidth: 140,
    rowProps: { gutter: 50 },
    baseColProps: { span: 12 },
    showResetButton: false,
    submitButtonOptions: { text: '提交' },
  });
</script>
