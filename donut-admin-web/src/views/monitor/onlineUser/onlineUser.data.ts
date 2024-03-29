import { BasicColumn } from '@/components/Table';

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
    title: '访问时间',
    dataIndex: 'loginTime',
  },
  {
    title: '过期时间（秒）',
    dataIndex: 'timeout',
  },
];
