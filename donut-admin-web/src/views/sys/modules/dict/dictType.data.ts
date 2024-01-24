// import { h } from 'vue';
import { BasicColumn, FormSchema } from '@/components/Table';
import { dictSelectProps } from '@/api/system/dictData';
// import { DictTag } from '/@/components/DictTag';

export const columns: BasicColumn[] = [
  {
    title: '字典名称',
    dataIndex: 'dictName',
  },
  {
    title: '字典Key',
    dataIndex: 'dictKey',
    slots: { customRender: 'dictKey' },
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
    title: '创建时间',
    dataIndex: 'createTime',
  },
];

export const searchFormSchema: FormSchema[] = [
  {
    field: 'dictType',
    label: '字典类型',
    component: 'RadioButtonGroup',
    defaultValue: '0',
    componentProps: ({ tableAction }) => {
      return {
        onChange: (e) => {
          if (!(e instanceof Object)) {
            tableAction.reload({ searchInfo: { dictType: e } });
          }
        },
        options: [
          { label: '系统', value: '0' },
          { label: '业务', value: '1' },
        ],
      };
    },
    colProps: { span: 8 },
  },
  {
    field: 'dictName',
    label: '字典名称',
    component: 'Input',
  },
  {
    field: 'dictKey',
    label: '字典Key',
    component: 'Input',
  },
  {
    field: 'status',
    label: '状态',
    component: 'ApiSelect',
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
    field: 'dictType',
    label: '字典类型',
    required: true,
    component: 'RadioButtonGroup',
    defaultValue: '0',
    componentProps: {
      options: [
        { label: '系统', value: '0' },
        { label: '业务', value: '1' },
      ],
    },
  },
  {
    field: 'dictName',
    label: '字典名称',
    required: true,
    component: 'Input',
  },
  {
    field: 'dictKey',
    label: '字典Key',
    required: true,
    component: 'Input',
  },
  {
    field: 'remark',
    label: '备注',
    component: 'InputTextArea',
  },
];
