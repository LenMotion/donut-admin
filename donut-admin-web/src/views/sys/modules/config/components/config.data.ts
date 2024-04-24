import { BasicColumn, FormSchema } from '@/components/Table';
import { menuTreeApi } from '@/api/system/menu';
import { getBase64WithFile } from '@/components/Upload/src/helper';

export const settingList = [
  {
    key: '1',
    name: '系统设置',
    component: 'ConfigForm',
  },
  {
    key: '2',
    name: '其他设置',
    component: 'ConfigTable',
  },
];

export const formSchema: FormSchema[] = [
  {
    field: 'SYSTEM_LOGO',
    label: '系统LOGO',
    required: true,
    component: 'ImageUpload',
    colProps: { span: 24 },
    componentProps: ({ formModel }) => {
      return {
        api: (file) => {
          console.log(file);
          return new Promise((resolve) => {
            getBase64WithFile(file.file).then((res) => {
              console.log(res);
              formModel.SYSTEM_LOGO = res.result;
              resolve({});
            });
          });
        },
      };
    },
    // @ts-ignore
    // slot: 'logo',
  },
  {
    field: 'SYSTEM_NAME',
    label: '系统名称',
    required: true,
    component: 'Input',
  },
  {
    field: 'SYSTEM_TITLE',
    label: '系统标题',
    required: true,
    component: 'Input',
  },
  {
    field: 'SYSTEM_DESCRIPTION',
    label: '系统描述',
    required: true,
    component: 'Input',
  },
  {
    field: 'SITE_COPYRIGHT',
    label: '页脚信息',
    required: true,
    component: 'Input',
  },
  {
    field: 'LOGIN_CAPTCHA_SWITCH',
    label: '验证码开关',
    required: true,
    component: 'Switch',
    componentProps: {
      checkedValue: 'true',
      unCheckedValue: 'false',
    },
    colProps: { span: 6 },
  },
  {
    field: 'MAX_DEPT_LEVEL',
    label: '最大部门层级',
    required: true,
    rules: [{ pattern: /^(0|[1-9]\d*)$/, message: '不能小于0' }],
    component: 'InputNumber',
    helpMessage: '小于等于0表示不限制',
    colProps: { span: 6 },
  },
  {
    field: 'DEFAULT_MENU',
    label: '默认菜单',
    required: false,
    component: 'ApiTreeSelect',
    helpMessage: '不管用户有没有这个菜单，只要有账号都会赋予配置的这些菜单！比如：首页、个人中心等',
    componentProps: {
      api: menuTreeApi,
      treeCheckable: true,
      multiple: true,
      valueField: 'id',
      labelField: 'title',
    },
    colProps: { span: 12 },
  },
  {
    field: 'ACCOUNT_LOCK_COUNT',
    label: '密码错误次数',
    required: true,
    rules: [{ pattern: /^(0|[1-9]\d*)$/, message: '不能小于0' }],
    component: 'InputNumber',
    helpMessage: '密码连续输入错误次数，为0不做处理',
    colProps: { span: 6 },
  },
  {
    field: 'ACCOUNT_LOCK_TIME',
    label: '锁定时长(分钟)',
    required: true,
    rules: [{ pattern: /^(0|[1-9]\d*)$/, message: '不能小于0' }],
    component: 'InputNumber',
    helpMessage: '密码连续输入错误次数，账号锁定时间，为0不做处理',
    colProps: { span: 6 },
  },
  {
    field: 'USER_DEFAULT_PASSWORD',
    label: '用户默认密码',
    required: true,
    component: 'InputPassword',
    colProps: { span: 12 },
  },
];

export const columns: BasicColumn[] = [
  {
    title: '配置名称',
    dataIndex: 'configName',
  },
  {
    title: '配置key',
    dataIndex: 'configKey',
  },
  {
    title: '配置value',
    dataIndex: 'configValue',
  },
  {
    title: '备注',
    dataIndex: 'remark',
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
  },
];

export const searchFormSchema: FormSchema[] = [
  {
    field: 'configKey',
    label: '配置key',
    component: 'Input',
    colProps: { span: 8 },
  },
];

export const editFormSchema: FormSchema[] = [
  {
    field: 'id',
    label: '字典Id',
    show: false,
    component: 'Input',
  },
  {
    field: 'configName',
    label: '配置名称',
    required: true,
    component: 'Input',
  },
  {
    field: 'configKey',
    label: '配置key',
    required: true,
    component: 'Input',
  },
  {
    field: 'configValue',
    label: '配置value',
    required: true,
    component: 'Input',
  },
  {
    field: 'remark',
    label: '备注',
    component: 'InputTextArea',
  },
];
