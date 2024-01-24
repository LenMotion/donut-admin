import { BasicColumn, FormSchema } from '@/components/Table';
import { menuTreeApi } from '@/api/system/menu';

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
    field: 'SYSTEM_NAME',
    label: '系统名称',
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
  },
  {
    field: 'USER_DEFAULT_PASSWORD',
    label: '用户默认密码',
    required: true,
    component: 'InputPassword',
  },
  {
    field: 'MAX_DEPT_LEVEL',
    label: '最大部门层级',
    required: true,
    component: 'InputNumber',
    helpMessage: '小于等于0表示不限制',
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
