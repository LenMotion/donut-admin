import { BasicColumn, FormSchema } from '@/components/Table';
import { dictSelectProps } from '@/api/system/dictData';

export const columns: BasicColumn[] = [
  {
    title: '字典标签',
    dataIndex: 'dictLabel',
    slots: { customRender: 'dictLabel' },
  },
  {
    title: '字典键值',
    dataIndex: 'dictValue',
  },
  {
    title: '序号',
    dataIndex: 'dictSort',
    width: 80,
  },
  {
    title: '状态',
    dataIndex: 'status',
    slots: { customRender: 'status' },
    width: 100,
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
    field: 'dictLabel',
    label: '字典标签',
    component: 'Input',
    colProps: { span: 8 },
  },
  {
    field: 'status',
    label: '状态',
    component: 'Select',
    componentProps: dictSelectProps('sys_base_status'),
    colProps: { span: 8 },
  },
];

export const formSchema: FormSchema[] = [
  {
    field: 'id',
    label: '字典Id',
    show: false,
    component: 'Input',
  },
  {
    field: 'dictKey',
    label: '字典Id',
    show: false,
    component: 'Input',
  },
  {
    field: 'dictLabel',
    label: '字典标签',
    required: true,
    component: 'Input',
  },
  {
    field: 'dictValue',
    label: '数据键值',
    required: true,
    component: 'Input',
  },
  {
    field: 'listClass',
    label: '颜色对象',
    required: true,
    component: 'Select',
    componentProps: {
      options: [
        { label: 'blue', value: 'blue' },
        { label: 'cyan', value: 'cyan' },
        { label: 'green', value: 'green' },
        { label: 'purple', value: 'purple' },
        { label: 'orange', value: 'orange' },
        { label: 'pink', value: 'pink' },
        { label: 'red', value: 'red' },
      ],
    },
  },
  {
    field: 'preview',
    label: '预览',
    // colSlot: 'preview',
    // @ts-ignore
    slot: 'preview',
    component: 'Input',
  },
  {
    field: 'dictSort',
    label: '序号',
    required: true,
    component: 'InputNumber',
  },
  {
    field: 'remark',
    label: '备注',
    component: 'InputTextArea',
  },
];
