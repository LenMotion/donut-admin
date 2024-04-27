import { BasicColumn, FormSchema } from '@/components/Table';
import { dictSelectProps } from '@/api/system/dictData';

export const columns: BasicColumn[] = [
  {
    title: '数据源类型',
    dataIndex: 'type',
    customRender: ({ record }) => record.transMap?.typeName,
  },
  {
    title: '名称',
    dataIndex: 'name',
  },
  {
    title: 'host',
    dataIndex: 'host',
  },
  {
    title: '端口',
    dataIndex: 'port',
  },
  {
    title: '库名',
    dataIndex: 'schemaName',
  },
  {
    title: '测试连接',
    dataIndex: 'checkConnection',
    format: (text) => (text ? '是' : '否'),
  },
  {
    title: '状态',
    dataIndex: 'status',
    slots: { customRender: 'status' },
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
  },
];

export const searchFormSchema: FormSchema[] = [
  {
    field: 'type',
    label: '数据源类型',
    component: 'ApiSelect',
    componentProps: dictSelectProps('gen_datasource_type'),
  },
  {
    field: 'name',
    label: '名称',
    component: 'Input',
  },
  {
    field: 'host',
    label: 'host',
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
    field: 'type',
    label: '数据源类型',
    required: true,
    component: 'ApiSelect',
    componentProps: dictSelectProps('gen_datasource_type'),
  },
  {
    field: 'name',
    label: '名称',
    required: true,
    component: 'Input',
  },
  {
    field: 'host',
    label: 'host',
    required: true,
    component: 'Input',
  },
  {
    field: 'port',
    label: '端口',
    required: true,
    component: 'Input',
  },
  {
    field: 'schemaName',
    label: '库名',
    required: true,
    component: 'Input',
  },
  {
    field: 'username',
    label: '用户名',
    required: true,
    component: 'Input',
  },
  {
    field: 'password',
    label: '密码',
    required: ({ values }) => !values.id,
    component: 'InputPassword',
    componentProps: {
      visibilityToggle: false,
    },
  },
];
