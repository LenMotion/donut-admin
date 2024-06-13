import { BasicColumn, FormSchema } from '@/components/Table';
import { dictSelectProps } from '@/api/system/dictData';
import { optionsApi } from '@/api/system/role';

export const columns: BasicColumn[] = [
  {
    title: '用户名',
    fixed: 'left',
    dataIndex: 'username',
  },
  {
    title: '用户编号',
    dataIndex: 'userCode',
  },
  {
    title: '用户昵称',
    dataIndex: 'nickName',
  },
  {
    title: '所属部门',
    dataIndex: 'deptName',
  },
  {
    title: '所属岗位',
    dataIndex: 'postName',
  },
  {
    title: '角色',
    dataIndex: 'roleNames',
    customRender: ({ record }) => record.transMap?.roleName,
  },
  {
    title: '手机号',
    dataIndex: 'phoneNumber',
  },
  {
    title: '性别',
    dataIndex: 'sex',
    width: 60,
    customRender: ({ record }) => record.transMap?.sexName,
  },
  {
    title: '生日',
    width: 100,
    dataIndex: 'birthday',
  },
  {
    title: '状态',
    width: 80,
    dataIndex: 'status',
    fixed: 'right',
    slots: { customRender: 'status' },
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
  },
];

export const searchFormSchema: FormSchema[] = [
  {
    field: 'username',
    label: '用户名',
    component: 'Input',
    colProps: { span: 8 },
  },
  {
    field: 'userCode',
    label: '用户编号',
    component: 'Input',
    colProps: { span: 8 },
  },
  {
    field: 'nickName',
    label: '用户昵称',
    component: 'Input',
    colProps: { span: 8 },
  },
  {
    field: 'status',
    label: '状态',
    component: 'Select',
    componentProps: dictSelectProps('sys_base_status'),
    colProps: { span: 8 },
  },
];

export const formSchema: FormSchema[] = [
  {
    field: 'id',
    label: 'id',
    component: 'Input',
    show: false,
  },
  {
    field: 'avatar',
    label: '头像',
    colProps: { lg: 24, md: 24 },
    slot: 'avatar',
  },
  {
    field: 'username',
    label: '用户名',
    component: 'Input',
    required: true,
    rules: [{ pattern: /^[a-zA-Z][a-zA-Z0-9_]{3,19}$/, message: '请输入字母开头4-20位的用户名' }],
    dynamicDisabled: ({ values }) => values.id != '' && values.id != undefined,
  },
  {
    field: 'userCode',
    label: '用户编号',
    component: 'Input',
    rules: [{ pattern: /^[a-zA-Z0-9]{1,15}$/, message: '请输入字母开头15位以内的用户编号' }],
    required: true,
  },
  {
    field: 'nickName',
    label: '用户昵称',
    component: 'Input',
    rules: [{ max: 20, message: '用户昵称长度不能超过20位' }],
    required: true,
  },
  {
    field: 'realName',
    label: '真实姓名',
    component: 'Input',
  },
  {
    field: 'sex',
    label: '性别',
    component: 'ApiSelect',
    required: true,
    componentProps: dictSelectProps('sys_base_sex'),
  },
  {
    field: 'birthday',
    label: '生日',
    component: 'DatePicker',
    required: true,
    componentProps: {
      valueFormat: 'YYYY-MM-DD',
    },
  },
  {
    field: 'deptId',
    label: '所属部门',
    component: 'TreeSelect',
    required: true,
    componentProps: {
      fieldNames: {
        label: 'deptName',
        value: 'id',
      },
    },
  },
  {
    field: 'postId',
    label: '所属岗位',
    component: 'Select',
    required: true,
    componentProps: {
      fieldNames: {
        label: 'postName',
        value: 'id',
      },
    },
  },
  {
    field: 'roleIds',
    label: '关联角色',
    component: 'ApiSelect',
    required: true,
    colProps: { lg: 24, md: 24 },
    componentProps: {
      api: optionsApi,
      mode: 'multiple',
      fieldNames: {
        label: 'roleName',
        value: 'id',
      },
    },
  },
  {
    field: 'phoneNumber',
    label: '手机号',
    component: 'Input',
  },
  {
    field: 'email',
    label: '邮箱',
    rules: [{ type: 'email', message: '请输入正确的邮箱' }],
    component: 'Input',
  },
];

export const otherFormSchema: FormSchema[] = [
  {
    field: 'nation',
    label: '民族',
    component: 'ApiSelect',
    componentProps: dictSelectProps('sys_base_nation'),
  },
  {
    field: 'cultureType',
    label: '文化程度',
    component: 'ApiSelect',
    componentProps: dictSelectProps('sys_culture_type'),
  },
  {
    field: 'idType',
    label: '证件类型',
    component: 'ApiSelect',
    componentProps: dictSelectProps('sys_id_type'),
  },
  {
    field: 'idCard',
    label: '证件号码',
    component: 'Input',
  },
  {
    field: 'politicalOutlook',
    label: '政治面貌',
    component: 'ApiSelect',
    componentProps: dictSelectProps('sys_base_political_outlook'),
  },
  {
    field: 'entryDate',
    label: '入职时间',
    component: 'DatePicker',
  },
  {
    field: 'address',
    label: '地址',
    component: 'Input',
    colProps: { lg: 24, md: 24 },
  },
  {
    field: 'remark',
    label: '备注',
    component: 'InputTextArea',
    colProps: { lg: 24, md: 24 },
  },
];

export const deptColumns: BasicColumn[] = [
  {
    title: '所属部门',
    dataIndex: 'deptId',
    align: 'left',
    slots: { customRender: 'deptId' },
  },
  {
    title: '所属岗位',
    dataIndex: 'postId',
    align: 'left',
    slots: { customRender: 'postId' },
  },
];

export const fieldNames = {
  label: 'deptName',
  value: 'id',
};
