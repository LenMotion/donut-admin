import { BasicColumn, FormSchema } from '@/components/Table';

export const columns: BasicColumn[] = [
  {
    title: '租户名称',
    dataIndex: 'name',
    slots: { customRender: 'name' },
  },
  {
    title: '租户描述',
    dataIndex: 'description',
    slots: { customRender: 'description' },
  },
  {
    title: '所属集团',
    dataIndex: 'groupName',
    slots: { customRender: 'groupName' },
  },
  {
    title: '状态',
    dataIndex: 'status',
    slots: { customRender: 'status' },
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    slots: { customRender: 'createTime' },
  },
];

export const searchFormSchema: FormSchema[] = [
  {
    field: 'name',
    label: '租户名称',
    component: 'Input',
  },
  {
    field: 'groupName',
    label: '所属集团',
    component: 'Input',
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
    field: 'name',
    label: '租户名称',
    component: 'Input',
    required: true,
  },
  {
    field: 'description',
    label: '租户描述',
    component: 'InputTextArea',
  },
  {
    field: 'groupName',
    label: '所属集团',
    component: 'Input',
  },
  {
    label: '1',
    field: 'menuIds',
    colSlot: 'menu',
    component: 'Input',
  },
];
