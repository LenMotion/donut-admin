import { FormSchema } from '@/components/Form';
import { dictSelectProps } from '@/api/system/dictData';
import { DescItem } from '@/components/Description';
import { h } from 'vue';
import { Avatar } from 'ant-design-vue';
import { getMenuList } from '@/api/sys/menu';

export interface ListItem {
  key: string;
  title: string;
  description: string;
  extra?: string;
  avatar?: string;
  color?: string;
}

// tab的list
export const settingList = [
  {
    key: '0',
    name: '基本信息',
    component: 'BasicInfo',
  },
  {
    key: '1',
    name: '修改信息',
    component: 'BaseSetting',
  },
  {
    key: '2',
    name: '修改密码',
    component: 'ChangePassword',
  },
];

// 基础设置 form
export const baseSetschemas: FormSchema[] = [
  {
    field: 'nickName',
    component: 'Input',
    label: '昵称',
    required: true,
    colProps: { span: 12 },
  },
  {
    field: 'realName',
    component: 'Input',
    label: '真实姓名',
    colProps: { span: 12 },
  },
  {
    field: 'phoneNumber',
    component: 'Input',
    label: '联系电话',
    colProps: { span: 12 },
  },
  {
    field: 'email',
    component: 'Input',
    label: '邮箱',
    colProps: { span: 12 },
  },
  {
    field: 'sex',
    component: 'ApiSelect',
    label: '性别',
    required: true,
    componentProps: dictSelectProps('sys_base_sex'),
    colProps: { span: 12 },
  },
  {
    field: 'age',
    component: 'InputNumber',
    label: '年龄',
    required: true,
    colProps: { span: 12 },
  },
  {
    field: 'nation',
    component: 'ApiSelect',
    label: '民族',
    required: true,
    componentProps: dictSelectProps('sys_base_nation'),
    colProps: { span: 12 },
  },
  {
    field: 'cultureType',
    component: 'ApiSelect',
    label: '文化程度',
    componentProps: dictSelectProps('sys_culture_type'),
    colProps: { span: 12 },
  },
  {
    field: 'address',
    component: 'InputTextArea',
    label: '个人地址',
    defaultValue: '',
    colProps: { span: 24 },
  },
  {
    field: 'quickNav',
    component: 'ApiTreeSelect',
    label: '快捷导航',
    componentProps: {
      api: getMenuList,
      treeCheckable: true,
      multiple: true,
      valueField: 'id',
      labelField: 'title',
    },
    colProps: { span: 24 },
  },
];

export const passwordFormSchema: FormSchema[] = [
  {
    field: 'passwordOld',
    label: '当前密码',
    component: 'InputPassword',
    required: true,
  },
  {
    field: 'passwordNew',
    label: '新密码',
    component: 'StrengthMeter',
    componentProps: {
      placeholder: '新密码',
    },
    rules: [
      {
        required: true,
        message: '请输入新密码',
      },
      {
        pattern: /^(?=.*[0-9])(?=.*[a-zA-Z!@#$%^&*()-_+=])[a-zA-Z0-9!@#$%^&*()-_+=]{6,20}$/,
        message: '密码需要6-20位并且包含数字、字母和特殊字符',
      },
    ],
  },
  {
    field: 'confirmPassword',
    label: '确认密码',
    component: 'InputPassword',
    dynamicRules: ({ values }) => {
      return [
        {
          required: true,
          validator: (_, value) => {
            if (!value) {
              return Promise.reject('密码不能为空');
            }
            if (value !== values.passwordNew) {
              return Promise.reject('两次输入的密码不一致!');
            }
            return Promise.resolve();
          },
        },
      ];
    },
  },
];

export const userInfoSchema: DescItem[] = [
  {
    field: 'avatar',
    label: '头像',
    span: 4,
    render: (_, data) => {
      return h(Avatar, {
        src: data.transMap?.avatarUrl,
        size: 48,
      });
    },
  },
  {
    field: 'username',
    label: '用户名',
  },
  {
    field: 'userCode',
    label: '用户编码',
  },
  {
    field: 'nickName',
    label: '昵称',
  },
  {
    field: 'realName',
    label: '真实姓名',
  },
  {
    field: 'sex',
    label: '性别',
    render: (_, data) => data.transMap?.sexName || '-',
  },
  {
    field: 'deptName',
    label: '部门',
  },
  {
    field: 'postName',
    label: '岗位',
  },
  {
    field: 'age',
    label: '年龄',
  },
  {
    field: 'phoneNumber',
    label: '电话号码',
  },
  {
    field: 'email',
    label: '邮箱地址',
  },
  {
    field: 'status',
    label: '状态',
    render: (_, data) => data.transMap?.statusName || '-',
  },
  {
    field: 'createTime',
    label: '创建时间',
  },
  {
    field: 'idType',
    label: '证件类型',
    render: (_, data) => data.transMap?.idTypeName || '-',
  },
  {
    field: 'idCard',
    label: '证件号码',
  },
  {
    field: 'nation',
    label: '民族',
    render: (_, data) => data.transMap?.nationName || '-',
  },
  {
    field: 'cultureType',
    label: '文化程度',
    render: (_, data) => data.transMap?.cultureTypeName || '-',
  },
  {
    field: 'politicalOutlook',
    label: '政治面貌',
    render: (_, data) => data.transMap?.politicalOutlookName || '-',
  },
  {
    field: 'entryDate',
    label: '入职时间',
  },
  {
    field: 'loginIp',
    label: '最近登录IP',
  },
  {
    field: 'loginDate',
    label: '最近登录时间',
  },
  {
    field: 'address',
    label: '地址',
    span: 2,
  },
  {
    field: 'remark',
    label: '备注',
    span: 2,
  },
  {
    field: 'roleNames',
    label: '关联角色',
    render: (_, data) => data.transMap?.roleName || '-',
    span: 4,
  },
];
