<template>
  <BasicForm @register="registerForm" />
</template>

<script lang="ts" setup>
  import { BasicForm, useForm } from '@/components/Form';
  import { formSchema } from './component.data';

  const emit = defineEmits(['nextStep']);

  function customSubmitFunc(): Promise<void> {
    return new Promise<void>(async (resolve, reject) => {
      try {
        const values = await validate();
        emit('nextStep', values);
        resolve();
      } catch (error) {
        reject();
      }
    });
  }

  const [registerForm, { validate, resetFields, setFieldsValue }] = useForm({
    baseColProps: { lg: 8, md: 12, sm: 24 },
    labelWidth: 120,
    schemas: formSchema(),
    actionColOptions: { span: 13 },
    showResetButton: false,
    submitButtonOptions: { text: '下一步' },
    submitFunc: customSubmitFunc,
  });

  defineExpose({
    setFieldsValue,
    resetFields,
  });
</script>
