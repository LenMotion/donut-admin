import { DescItem } from '@/components/Description/index';

export const schemas: DescItem[] = [
  { field: 'tcp_port', label: '端口' },
  { field: 'connected_clients', label: '客户端数' },
  { field: 'uptime_in_days', label: '运行时间(天)' },
  { field: 'connected_clients', label: '客户端数' },
  { field: 'uptime_in_days', label: '运行时间(天)' },
  { field: 'maxmemory_human', label: '内存配置' },
  { field: 'used_cpu_user_children', label: '使用CPU' },
  { field: 'maxmemory_human', label: '内存配置' },
  {
    field: 'aof_enabled',
    label: 'AOF是否开启',
    render: (val: any) => (val == '0' ? '否' : '是'),
  },
  { field: 'rdb_last_bgsave_status', label: 'RDB是否成功' },
  { field: 'dbSize', label: 'Key数量' },
  {
    field: 'instantaneous_input_kbps',
    label: '网络入口/出口',
    render: (_, record: Recordable) => {
      const { instantaneous_input_kbps, instantaneous_output_kbps } = record;
      return instantaneous_input_kbps + 'kps/' + instantaneous_output_kbps + 'kps';
    },
  },
];
