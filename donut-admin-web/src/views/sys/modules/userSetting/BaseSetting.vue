<template>
  <CollapseContainer title="基本设置" :canExpan="false">
    <Row :gutter="24" class="pt-50px pb-50px">
      <Col :span="12">
        <BasicForm @register="register" />
        <div class="text-center mt-10px">
          <a-button type="primary" @click="handleSubmit"> 保存 </a-button>
        </div>
      </Col>
      <Col :offset="4" :span="8">
        <div class="change-avatar">
          <div class="mb-2">头像</div>
          <ImageUpload :value="avatar" @change="updateAvatar" />
        </div>
      </Col>
    </Row>
  </CollapseContainer>
</template>
<script lang="ts" setup>
  import { Row, Col } from 'ant-design-vue';
  import { computed, onMounted } from 'vue';
  import { BasicForm, useForm } from '@/components/Form';
  import { CollapseContainer } from '@/components/Container';
  import { ImageUpload } from '@/components/Upload';

  import { useMessage } from '@/hooks/web/useMessage';

  import { getUserInfo, updateUserInfo, updateUserAvatar } from '@/api/sys/user';
  import { baseSetSchemas } from './data';
  import { useUserStore } from '@/store/modules/user';

  const { createMessage } = useMessage();
  const userStore = useUserStore();

  const [register, { setFieldsValue, validate }] = useForm({
    labelWidth: 80,
    schemas: baseSetSchemas,
    showActionButtonGroup: false,
  });

  onMounted(async () => {
    const data = await getUserInfo();
    setFieldsValue({ ...data });
  });

  const avatar = computed(() => {
    const { avatar } = userStore.getUserInfo;
    return avatar;
  });

  function updateAvatar(avatar) {
    if (avatar) {
      updateUserAvatar({ avatar }).then(() => {
        createMessage.success('更新成功！');
        userStore.getUserInfoAction();
      });
    }
  }

  async function handleSubmit() {
    const values = await validate();

    updateUserInfo(values).then(() => {
      userStore.getUserInfoAction();
      createMessage.success('更新成功！');
    });
  }
</script>

<style lang="less" scoped>
  .change-avatar {
    img {
      display: block;
      margin-bottom: 15px;
      border-radius: 50%;
    }
  }
</style>
