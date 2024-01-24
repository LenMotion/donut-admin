import { BasicColumn } from '@/components/Table';
import { FormSchema } from '@/components/Table';
import { dictSelectProps } from '@/api/system/dictData';

export const columns: BasicColumn[] = [
  {
    title: '任务名称',
    dataIndex: 'name',
    slots: { customRender: 'name' },
  },
  {
    title: '任务表达式',
    dataIndex: 'cron',
  },
  {
    title: '任务执行器',
    dataIndex: 'taskClass',
  },
  {
    title: '执行参数',
    dataIndex: 'params',
  },
  {
    title: '任务状态',
    dataIndex: 'status',
    slots: { customRender: 'status' },
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
  },
  {
    title: '创建人',
    dataIndex: 'createBy',
  },
];

export const searchFormSchema: FormSchema[] = [
  {
    label: '任务名称',
    field: 'name',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: '任务执行器',
    field: 'taskClass',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: '任务状态',
    field: 'status',
    component: 'ApiSelect',
    componentProps: dictSelectProps('sys_base_status'),
    colProps: { span: 6 },
  },
];

export const logSearchFormSchema: FormSchema[] = [
  {
    label: '执行类型',
    field: 'type',
    component: 'ApiSelect',
    componentProps: dictSelectProps('sys_job_exec_type'),
    colProps: { span: 6 },
  },
  {
    label: '执行状态',
    field: 'status',
    component: 'ApiSelect',
    componentProps: dictSelectProps('sys_job_exec_status'),
    colProps: { span: 6 },
  },
  {
    label: '执行时间',
    field: 'createTime',
    component: 'RangePicker',
    colProps: { span: 12 },
    componentProps: {
      format: 'YYYY-MM-DD HH:mm:ss',
      placeholder: ['开始时间', '结束时间'],
      showTime: { format: 'HH:mm:ss' },
    },
  },
];

export const formSchema: FormSchema[] = [
  {
    label: 'id',
    field: 'id',
    component: 'Input',
    show: false,
  },
  {
    label: '任务名称',
    field: 'name',
    required: true,
    component: 'Input',
  },
  {
    label: '任务表达式',
    field: 'cron',
    required: true,
    component: 'Input',
    helpMessage: 'cron生成规则参考：https://www.pppet.net/',
    rules: [
      {
        pattern: /^(\S+)\s+(\S+)\s+(\S+)\s+(\S+)\s+(\S+)\s+(\S+)\s?$/,
        // pattern: /^(\*|([0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])|\*\/([0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])) (\*|([0-9]|1[0-9]|2[0-3])|\*\/([0-9]|1[0-9]|2[0-3])) (\*|([1-9]|1[0-9]|2[0-9]|3[0-1])|\*\/([1-9]|1[0-9]|2[0-9]|3[0-1])) (\*|([1-9]|1[0-2])|\*\/([1-9]|1[0-2])) (\*|([0-6])|\*\/([0-6])) ?$/,
        message: '请输入正确的cron表达式',
      },
    ],
  },
  {
    label: '任务执行器',
    field: 'taskClass',
    required: true,
    component: 'Input',
  },
  {
    label: '执行参数',
    field: 'params',
    component: 'Input',
  },
  {
    label: '任务状态',
    field: 'status',
    component: 'RadioButtonGroup',
    defaultValue: '0',
    show: false,
    componentProps: {
      options: [
        { label: '正常', value: '0' },
        { label: '禁用', value: '1' },
      ],
    },
  },
];

export const logColumns: BasicColumn[] = [
  {
    title: '执行方式',
    dataIndex: 'type',
    customRender: ({ record }) => record.transMap?.typeName,
  },
  {
    title: '执行状态',
    dataIndex: 'status',
    customRender: ({ record }) => record.transMap?.statusName,
    // customRender: ({ text }) => h(DictTag, { info: text }),
  },
  {
    title: '开始时间',
    dataIndex: 'createTime',
  },
  {
    title: '结束时间',
    dataIndex: 'endTime',
  },
  {
    title: '耗时',
    dataIndex: 'time',
    format: (text) => {
      const milliseconds = parseInt(text);
      if (milliseconds < 1000) {
        return milliseconds + ' 毫秒';
      } else if (milliseconds < 60000) {
        return (milliseconds / 1000).toFixed(2) + ' 秒';
      } else if (milliseconds < 3600000) {
        return (milliseconds / 60000).toFixed(2) + ' 分钟';
      } else if (milliseconds < 86400000) {
        return (milliseconds / 3600000).toFixed(2) + ' 小时';
      } else {
        return (milliseconds / 86400000).toFixed(2) + ' 天';
      }
    },
  },
  {
    title: '错误信息',
    dataIndex: 'errorMsg',
  },
];
