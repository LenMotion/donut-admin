import { dictSelectProps } from '@/api/system/dictData';
import { DescItem } from '@/components/Description';
import { BasicColumn, FormSchema } from '@/components/Table';

export const columns: BasicColumn[] = [
  {
    title: '操作人员',
    dataIndex: 'username',
  },
  {
    title: '操作人员昵称',
    dataIndex: 'nickName',
  },
  {
    title: '系统功能',
    dataIndex: 'title',
  },
  {
    title: '请求方式',
    dataIndex: 'requestMethod',
    width: 120,
  },
  {
    title: '主机',
    dataIndex: 'ip',
    width: 120,
  },
  {
    title: '操作状态',
    dataIndex: 'status',
    customRender: ({ record }) => record.transMap?.statusName,
  },
  {
    title: '操作时间',
    dataIndex: 'createTime',
    width: 160,
  },
];

export const searchFormSchema: FormSchema[] = [
  {
    field: 'title',
    label: '操作功能',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: '用户名',
    field: 'username',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    field: 'requestMethod',
    label: '请求方式',
    component: 'Select',
    componentProps: {
      options: [
        { label: 'GET', value: 'GET' },
        { label: 'POST', value: 'POST' },
        { label: 'PUT', value: 'PUT' },
        { label: 'DELETE', value: 'DELETE' },
      ],
    },
    colProps: { span: 6 },
  },
  {
    label: '状态',
    field: 'status',
    component: 'ApiSelect',
    colProps: { span: 6 },
    componentProps: dictSelectProps('sys_operation_status'),
  },
];

export const detailDescItems: DescItem[] = [
  {
    field: 'username',
    label: '用户名',
    labelMinWidth: 70,
  },
  {
    field: 'nickName',
    label: '用户昵称',
  },
  {
    field: 'title',
    label: '操作功能',
  },
  {
    field: 'url',
    label: '请求地址',
  },
  {
    field: 'method',
    label: '执行方法',
  },
  {
    field: 'requestMethod',
    label: '请求方式',
  },
  {
    field: 'ip',
    label: 'ip',
  },
  {
    field: 'status',
    label: '状态',
    render: (_, data) => data.transMap?.statusName,
  },
  {
    field: 'params',
    label: '参数',
  },
  {
    field: 'errorMsg',
    label: '错误信息',
  },
  {
    field: 'createTime',
    label: '请求时间',
  },
];
