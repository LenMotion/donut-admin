<template>
  <CollapseContainer title="修改密码" :canExpan="false">
    <div class="py-8 bg-white flex flex-col justify-center items-center">
      <BasicForm @register="register" />
      <div class="flex justify-center">
        <a-button @click="resetFields"> 重置 </a-button>
        <a-button class="!ml-4" type="primary" @click="handleSubmit"> 确认 </a-button>
      </div>
    </div>
  </CollapseContainer>
</template>
<script lang="ts" setup>
  import { BasicForm, useForm } from '@/components/Form';
  import { CollapseContainer } from '@/components/Container';

  import { passwordFormSchema } from './data';
  import { updateUserPassword } from '@/api/sys/user';
  import { useUserStore } from '@/store/modules/user';
  import { useGlobSetting } from '@/hooks/setting';
  import { useMessage } from '@/hooks/web/useMessage';
  import JSEncrypt from 'jsencrypt';

  const { publicKey } = useGlobSetting();
  const { createMessage } = useMessage();

  const jsEncrypt = new JSEncrypt();
  jsEncrypt.setPublicKey(publicKey);

  const userStore = useUserStore();

  defineOptions({ name: 'ChangePassword' });

  const [register, { validate, resetFields }] = useForm({
    baseColProps: { span: 24 },
    labelWidth: 100,
    showActionButtonGroup: false,
    schemas: passwordFormSchema,
  });

  async function handleSubmit() {
    try {
      const values = await validate();
      const { passwordOld, passwordNew } = values;
      console.log(passwordOld, passwordNew);

      updateUserPassword({
        newPassword: jsEncrypt.encrypt(passwordNew),
        oldPassword: jsEncrypt.encrypt(passwordOld),
      }).then(() => {
        createMessage.success('密码修改成功，请重新登录');
        userStore.logout(true);
      });
    } catch (error) {
      console.error(error);
    }
  }
</script>
