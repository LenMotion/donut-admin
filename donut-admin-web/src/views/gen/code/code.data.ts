import { BasicColumn, FormSchema } from '@/components/Table';

export const columns: BasicColumn[] = [
  {
    title: '表名',
    dataIndex: 'tableName',
  },
  {
    title: '功能名',
    dataIndex: 'featureName',
  },
  {
    title: '所属模块',
    dataIndex: 'menuId',
    customRender: ({ record }) => record.transMap?.title,
  },
  {
    title: '模块名称',
    dataIndex: 'moduleName',
  },
  {
    title: '作者',
    dataIndex: 'author',
  },
  {
    title: '包名',
    dataIndex: 'packageName',
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
    field: 'tableName',
    label: '表名',
    component: 'Input',
  },
  {
    field: 'featureName',
    label: '功能名',
    component: 'Input',
  },
];
