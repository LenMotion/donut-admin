<template>
  <LoginFormTitle v-show="getShow" class="enter-x" />
  <Form
    class="p-4 enter-x"
    :model="formData"
    :rules="getFormRules"
    ref="formRef"
    v-show="getShow"
    @keypress.enter="handleLogin"
  >
    <FormItem name="tenantId" class="enter-x">
      <ASelect
        v-if="tenantList.length > 1"
        size="large"
        :value="appStore.tenantId"
        placeholder="请选择登录租户"
        class="fix-auto-fill"
        :options="tenantList"
        :fieldNames="{ label: 'name', value: 'id' }"
        @change="handleTenantEvent"
      />
    </FormItem>
    <FormItem name="account" class="enter-x">
      <Input
        size="large"
        v-model:value="formData.account"
        :placeholder="t('sys.login.userName')"
        class="fix-auto-fill"
      />
    </FormItem>
    <FormItem name="password" class="enter-x">
      <InputPassword
        size="large"
        visibilityToggle
        v-model:value="formData.password"
        :placeholder="t('sys.login.password')"
      />
    </FormItem>
    <FormItem name="code" class="enter-x" v-if="formData.captchaOnOff">
      <div class="flex">
        <div class="flex-1">
          <Input size="large" v-model:value="formData.code" :placeholder="t('sys.login.captcha')" />
        </div>
        <div class="ml-10px w-80px h-40px">
          <AImage
            :width="80"
            :height="40"
            :src="captchaUrl"
            @click="getCaptchaImage"
            :preview="false"
          />
        </div>
      </div>
    </FormItem>

    <ARow class="enter-x">
      <ACol :span="12">
        <FormItem>
          <!-- No logic, you need to deal with it yourself -->
          <Checkbox v-model:checked="rememberMe" size="small">
            {{ t('sys.login.rememberMe') }}
          </Checkbox>
        </FormItem>
      </ACol>
      <ACol :span="12">
        <FormItem :style="{ 'text-align': 'right' }">
          <!-- No logic, you need to deal with it yourself -->
          <Button type="link" size="small" @click="setLoginState(LoginStateEnum.RESET_PASSWORD)">
            {{ t('sys.login.forgetPassword') }}
          </Button>
        </FormItem>
      </ACol>
    </ARow>

    <FormItem class="enter-x">
      <Button type="primary" size="large" block @click="handleLogin" :loading="loading">
        {{ t('sys.login.loginButton') }}
      </Button>
    </FormItem>
  </Form>
</template>
<script lang="ts" setup>
  import { reactive, ref, unref, computed, watch } from 'vue';

  import {
    Checkbox,
    Form,
    Input,
    Row as ARow,
    Col as ACol,
    Button,
    Image as AImage,
    Select as ASelect,
  } from 'ant-design-vue';
  import LoginFormTitle from './LoginFormTitle.vue';

  import { useI18n } from '@/hooks/web/useI18n';
  import { useMessage } from '@/hooks/web/useMessage';
  import { useUserStore } from '@/store/modules/user';
  import { LoginStateEnum, useLoginState, useFormRules, useFormValid } from './useLogin';
  import { useDesign } from '@/hooks/web/useDesign';
  import { useGlobSetting } from '@/hooks/setting';
  import { useAppStore } from '@/store/modules/app';
  import { baseInfoApi } from '@/api/system/tenant';
  import { captchaImageApi } from '@/api/sys/user';
  import JSEncrypt from 'jsencrypt';

  const FormItem = Form.Item;
  const InputPassword = Input.Password;
  const { t } = useI18n();
  const { notification, createErrorModal, createMessage } = useMessage();
  const { prefixCls } = useDesign('login');
  const userStore = useUserStore();
  const { publicKey, tenantId } = useGlobSetting();
  const appStore = useAppStore();

  const { setLoginState, getLoginState } = useLoginState();
  const { getFormRules } = useFormRules();

  const formRef = ref();
  const loading = ref(false);
  const rememberMe = ref(false);

  const captchaUrl = ref('');
  const jsEncrypt = new JSEncrypt();
  jsEncrypt.setPublicKey(publicKey);

  const formData = reactive({
    account: '',
    password: '',
    uuid: '',
    code: '',
    captchaOnOff: false,
  });

  const { validForm } = useFormValid(formRef);

  //onKeyStroke('Enter', handleLogin);

  const getShow = computed(() => unref(getLoginState) === LoginStateEnum.LOGIN);

  async function handleLogin() {
    const data = await validForm();
    if (!data) return;
    try {
      loading.value = true;
      const userInfo = await userStore.login({
        password: jsEncrypt.encrypt(data.password) || '',
        username: data.account,
        mode: 'none', //不要默认的错误提示
        uuid: formData.uuid,
        code: data.code,
      });
      if (userInfo) {
        notification.success({
          message: t('sys.login.loginSuccessTitle'),
          description: `${t('sys.login.loginSuccessDesc')}: ${userInfo.nickName}`,
          duration: 3,
        });
      }
    } catch (error) {
      createErrorModal({
        title: t('sys.api.errorTip'),
        content: (error as unknown as Error).message || t('sys.api.networkExceptionMsg'),
        getContainer: () => document.body.querySelector(`.${prefixCls}`) || document.body,
      });
      getCaptchaImage();
    } finally {
      loading.value = false;
    }
  }

  const getCaptchaImage = () => {
    captchaImageApi().then((res) => {
      const { img, uuid, captchaOnOff } = res;
      captchaUrl.value = 'data:image/gif;base64,' + img;
      formData.uuid = uuid;
      formData.captchaOnOff = captchaOnOff;
    });
  };

  getCaptchaImage();
  watch(
    () => appStore.getTenantId,
    () => getCaptchaImage(),
  );

  const tenantList = ref<any[]>([]);
  baseInfoApi(tenantId).then((res) => {
    if (res.length == 0) {
      createMessage.error('租户信息查询失败');
    } else {
      if (!appStore.getTenantId) {
        appStore.setTenantId(res[0].id);
      }
      tenantList.value = res;
    }
  });

  const handleTenantEvent = (tenantId: string) => {
    appStore.setTenantId(tenantId);
  };
</script>
