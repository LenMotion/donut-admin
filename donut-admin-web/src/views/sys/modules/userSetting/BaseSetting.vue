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
          <CropperAvatar
            :uploadApi="basicUploadApi"
            :value="avatar"
            btnText="更换头像"
            :btnProps="{ preIcon: 'ant-design:cloud-upload-outlined' }"
            @change="updateAvatar"
            width="150"
          />
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
  import { CropperAvatar } from '@/components/Cropper';

  import { useMessage } from '@/hooks/web/useMessage';

  import headerImg from '@/assets/images/header.jpg';
  import { getUserInfo, updateUserInfo, updateUserAvatar } from '@/api/sys/user';
  import { baseSetschemas } from './data';
  import { useUserStore } from '@/store/modules/user';
  import { uploadApi } from '@/api/sys/upload';

  const { createMessage } = useMessage();
  const userStore = useUserStore();

  const basicUploadApi = uploadApi as any;

  const [register, { setFieldsValue, validate }] = useForm({
    labelWidth: 80,
    schemas: baseSetschemas,
    showActionButtonGroup: false,
  });

  onMounted(async () => {
    const data = await getUserInfo();
    setFieldsValue({ ...data });
  });

  const avatar = computed(() => {
    const { avatarUrl } = userStore.getUserInfo;
    return avatarUrl || headerImg;
  });

  function updateAvatar({ data }) {
    if (data) {
      updateUserAvatar({ avatar: data }).then(() => {
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
