import { BasicColumn, FormSchema } from '@/components/Table';
import { dictSelectProps } from '@/api/system/dictData';

export const columns: BasicColumn[] = [
  {
    title: '角色名称',
    dataIndex: 'roleName',
  },
  {
    title: '角色编码',
    dataIndex: 'roleKey',
  },
  {
    title: '序号',
    dataIndex: 'roleSort',
  },
  {
    title: '数据范围',
    dataIndex: 'dataScope',
    customRender: ({ record }) => record?.transMap?.dataScopeName,
  },
  {
    title: '状态',
    dataIndex: 'status',
    slots: { customRender: 'status' },
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
    field: 'roleName',
    label: '角色名称',
    component: 'Input',
  },
  {
    field: 'roleKey',
    label: '角色编码',
    component: 'Input',
  },
  {
    field: 'status',
    label: '状态',
    component: 'ApiSelect',
    componentProps: dictSelectProps('sys_base_status'),
    colProps: { span: 8 },
  },
];

export const formSchema: FormSchema[] = [
  {
    field: 'id',
    label: '字典Id',
    show: false,
    component: 'Input',
  },
  {
    field: 'roleName',
    label: '角色名称',
    required: true,
    component: 'Input',
  },
  {
    field: 'roleKey',
    label: '角色编码',
    required: true,
    component: 'Input',
  },
  {
    field: 'dataScope',
    label: '数据权限范围',
    required: true,
    component: 'ApiSelect',
    componentProps: dictSelectProps('sys_data_scope'),
  },
  {
    field: 'roleSort',
    label: '序号',
    required: true,
    component: 'InputNumber',
  },
  {
    field: 'remark',
    label: '备注',
    component: 'InputTextArea',
  },
  {
    label: '',
    field: 'menuIds',
    colSlot: 'menu',
    component: 'Input',
  },
];
