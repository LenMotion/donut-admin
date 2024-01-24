import { DescItem } from '@/components/Description/index';
import { BasicColumn } from '@/components/Table';
import dayjs from 'dayjs';

export const systemDescItems: DescItem[] = [
  { field: 'cpuModel', label: 'CPU型号' },
  { field: 'cpuNum', label: 'CPU核心数' },
  { field: 'manufacturer', label: '系统名称' },
  { field: 'os', label: '系统' },
  { field: 'osArch', label: '系统架构' },
  { field: 'version', label: '版本' },
  {
    field: 'systemBootTime',
    label: '系统启动时间',
    render: (val) => dayjs(val * 1000).format('YYYY-MM-DD HH:mm:ss'),
  },
  { field: 'ip', label: 'IP地址' },
];

export const jvmDescItems: DescItem[] = [
  { field: 'vendor', label: 'jdk供应商' },
  { field: 'jdkName', label: 'jdk名称' },
  { field: 'jdkVersion', label: 'jdk版本' },
  { field: 'jdkHome', label: 'jdk安装路径' },
  { field: 'projectHome', label: '项目根路径' },
];

export const memoryOptions = (max: number, used: number, color: string) => {
  return {
    tooltip: {
      formatter: '{b} <br/>{a} : ' + used,
    },
    series: [
      {
        type: 'gauge',
        min: 0,
        max,
        itemStyle: {
          color,
        },
        detail: {
          valueAnimation: true,
          formatter: '{value}',
        },
        progress: {
          show: true,
        },
        data: [
          {
            value: parseFloat(used),
            name: '内存消耗',
          },
        ],
      },
    ],
  };
};

export const diskTableSchema: BasicColumn[] = [
  {
    title: '名称',
    dataIndex: 'name',
  },
  {
    title: '挂载点',
    dataIndex: 'mount',
  },
  {
    title: '文件系统类型',
    dataIndex: 'fsType',
  },
  {
    title: '总大小',
    dataIndex: 'totalSpace',
    customRender: ({ text }) => (text / 1073741824).toFixed(2) + 'G',
  },
  {
    title: '可用大小',
    dataIndex: 'freeSpace',
    customRender: ({ text }) => (text / 1073741824).toFixed(2) + 'G',
  },
  {
    title: '已用大小',
    dataIndex: 'usableSpace',
    customRender: ({ text }) => (text / 1073741824).toFixed(2) + 'G',
  },
  {
    title: '已用百分比',
    dataIndex: 'usage',
    customRender: ({ record }) => ((record.usableSpace / record.totalSpace) * 100).toFixed(2) + '%',
  },
];
