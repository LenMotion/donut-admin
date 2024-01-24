import { BasicColumn, FormSchema } from '@/components/Table';

export const columns: BasicColumn[] = [
  {
    title: '预览',
    dataIndex: 'url',
    slots: { customRender: 'url' },
  },
  {
    title: '原始文件名',
    dataIndex: 'originalFilename',
  },
  {
    title: '文件大小(kb)',
    dataIndex: 'size',
    format: (text) => (parseInt(text) / 1024 / 1024).toFixed(2),
  },
  {
    title: '基础存储路径',
    dataIndex: 'basePath',
  },
  {
    title: '文件扩展名',
    dataIndex: 'ext',
  },
  {
    title: 'MIME类型',
    dataIndex: 'contentType',
  },
  {
    title: '存储平台',
    dataIndex: 'platform',
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
  },
];

export const searchFormSchema: FormSchema[] = [
  {
    label: '原始文件名',
    field: 'originalFilename',
    component: 'Input',
    colProps: { span: 6 },
  },
];
