import { FormSchema } from '@/components/Form';
import { BasicColumn } from '@/components/Table';
import { dictSelectProps } from '@/api/system/dictData';

export const searchFormSchema: FormSchema[] = [
  {
    field: 'deptName',
    label: '名称',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    field: 'status',
    label: '状态',
    component: 'ApiSelect',
    componentProps: dictSelectProps('sys_base_status'),
    colProps: { span: 6 },
  },
];

export const columns: BasicColumn[] = [
  {
    title: '名称',
    dataIndex: 'deptName',
  },
  {
    title: '简称',
    dataIndex: 'shortName',
  },
  {
    title: '部门编号',
    dataIndex: 'deptCode',
  },
  {
    title: '负责人',
    dataIndex: 'leader',
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
    title: '排序',
    dataIndex: 'orderNum',
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
  },
];

export const formSchema: FormSchema[] = [
  {
    field: 'id',
    label: 'id',
    show: false,
    component: 'Input',
  },
  {
    field: 'deptName',
    label: '名称',
    required: true,
    component: 'Input',
  },
  {
    field: 'shortName',
    label: '简称',
    required: true,
    component: 'Input',
  },
  {
    field: 'deptCode',
    label: '编号',
    required: true,
    component: 'Input',
  },
  {
    field: 'parentId',
    label: '所属上级',
    component: 'TreeSelect',
    required: true,
    componentProps: {
      fieldNames: {
        label: 'deptName',
        value: 'id',
        children: 'children',
      },
    },
  },
  {
    field: 'leader',
    label: '负责人',
    component: 'Input',
  },
  {
    field: 'phone',
    label: '联系电话',
    component: 'Input',
  },
  {
    field: 'email',
    label: '联系邮箱',
    component: 'Input',
  },
  {
    field: 'orderNum',
    label: '排序',
    required: true,
    defaultValue: 1,
    component: 'InputNumber',
  },
  {
    field: 'remark',
    label: '备注',
    component: 'InputTextArea',
  },
];
