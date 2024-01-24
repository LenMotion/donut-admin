import { BasicColumn, FormSchema } from '@/components/Table';
import { dictSelectProps } from '@/api/system/dictData';
import { Tinymce } from '@/components/Tinymce';
import { h } from 'vue';
import { TreeSelect } from 'ant-design-vue';

export const columns: BasicColumn[] = [
  {
    title: '标题',
    dataIndex: 'noticeTitle',
  },
  {
    title: '类型',
    dataIndex: 'noticeTypeName',
    customRender: ({ record }) => record.transMap.noticeTypeName,
  },
  {
    title: '发送范围',
    dataIndex: 'noticeSendTypeName',
    customRender: ({ record }) => record.transMap.noticeSendTypeName,
  },
  {
    title: '状态',
    dataIndex: 'status',
    slots: { customRender: 'status' },
  },
  {
    title: '发布时间',
    dataIndex: 'publishTime',
  },
  {
    title: '阅读量',
    dataIndex: 'readNum',
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
    field: 'noticeTitle',
    label: '标题',
    component: 'Input',
    colProps: { xl: 6, lg: 8 },
  },
  {
    field: 'noticeType',
    label: '类型',
    component: 'ApiSelect',
    componentProps: dictSelectProps('sys_notice_type'),
    colProps: { xl: 6, lg: 8 },
  },
  {
    field: 'noticeSendType',
    label: '发送范围',
    component: 'ApiSelect',
    componentProps: dictSelectProps('sys_notice_send_type'),
    colProps: { xl: 6, lg: 8 },
  },
  {
    field: 'status',
    label: '状态',
    component: 'ApiSelect',
    componentProps: dictSelectProps('sys_base_status'),
    colProps: { xl: 6, lg: 8 },
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
    field: 'noticeTitle',
    label: '标题',
    component: 'Input',
    required: true,
  },
  {
    field: 'noticeType',
    label: '类型',
    component: 'ApiSelect',
    componentProps: dictSelectProps('sys_notice_type'),
    required: true,
  },
  {
    field: 'noticeSendType',
    label: '发送范围',
    component: 'ApiSelect',
    componentProps: dictSelectProps('sys_notice_send_type'),
    required: true,
  },
  {
    field: 'deptIds',
    label: '所属部门',
    component: 'TreeSelect',
    required: true,
    ifShow: ({ values }) => values.noticeSendType == '1' || values.noticeSendType == '2',
    componentProps: {
      treeCheckable: true,
      multiple: true,
      showCheckedStrategy: TreeSelect.SHOW_PARENT,
      fieldNames: {
        label: 'deptName',
        value: 'id',
      },
    },
  },
  {
    field: 'noticeContent',
    component: 'Input',
    label: '内容',
    required: true,
    render: ({ model, field }) => {
      return h(Tinymce, {
        value: model[field],
        onChange: (value: string) => {
          model[field] = value;
        },
      });
    },
  },
  {
    field: 'remark',
    label: '备注',
    component: 'InputTextArea',
  },
];
