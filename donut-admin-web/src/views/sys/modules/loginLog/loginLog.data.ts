import { BasicColumn, FormSchema } from '@/components/Table';
import { dictSelectProps } from '@/api/system/dictData';

export const columns: BasicColumn[] = [
  {
    title: '用户名',
    dataIndex: 'username',
    width: 180,
  },
  {
    title: 'ip',
    dataIndex: 'ip',
  },
  {
    title: '操作系统',
    dataIndex: 'os',
  },
  {
    title: '浏览器',
    dataIndex: 'browser',
  },
  {
    title: '登录状态',
    dataIndex: 'status',
    customRender: ({ record }) => record.transMap?.statusName,
  },
  {
    title: '描述',
    dataIndex: 'msg',
  },
  {
    title: '访问时间',
    dataIndex: 'loginTime',
    width: 160,
  },
];

export const searchFormSchema: FormSchema[] = [
  {
    field: 'username',
    label: '用户名',
    component: 'Input',
    colProps: { xl: 6, lg: 8 },
  },
  {
    label: '地址',
    field: 'ip',
    component: 'Input',
    colProps: { xl: 6, lg: 8 },
  },
  {
    field: 'status',
    label: '状态',
    component: 'ApiSelect',
    componentProps: dictSelectProps('sys_login_status'),
    colProps: { xl: 6, lg: 8 },
  },
];
