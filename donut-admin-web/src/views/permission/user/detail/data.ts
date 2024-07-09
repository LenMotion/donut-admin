import { DescItem } from '@/components/Description';
import { BasicColumn } from '@/components/Table';
import { calcAge } from '@/utils/dateUtil';

export const userInfoSchema: DescItem[] = [
  {
    field: 'username',
    label: '用户名',
  },
  {
    field: 'userCode',
    label: '用户编码',
  },
  {
    field: 'nickName',
    label: '昵称',
  },
  {
    field: 'realName',
    label: '真实姓名',
  },
  {
    field: 'sex',
    label: '性别',
    render: (_, data) => data.transMap?.sexName || '-',
  },
  {
    field: 'deptName',
    label: '部门',
  },
  {
    field: 'postName',
    label: '岗位',
  },
  {
    field: 'birthday',
    label: '生日',
    render: (_, data) => `${data.birthday} (${calcAge(data.birthday)}岁) ` || '-',
  },
  {
    field: 'phoneNumber',
    label: '电话号码',
  },
  {
    field: 'email',
    label: '邮箱地址',
  },
  {
    field: 'status',
    label: '状态',
    render: (_, data) => data.transMap?.statusName || '-',
  },
  {
    field: 'createTime',
    label: '创建时间',
  },
  {
    field: 'idType',
    label: '证件类型',
    render: (_, data) => data.transMap?.idTypeName || '-',
  },
  {
    field: 'idCard',
    label: '证件号码',
  },
  {
    field: 'nation',
    label: '民族',
    render: (_, data) => data.transMap?.nationName || '-',
  },
  {
    field: 'cultureType',
    label: '文化程度',
    render: (_, data) => data.transMap?.cultureTypeName || '-',
  },
  {
    field: 'politicalOutlook',
    label: '政治面貌',
    render: (_, data) => data.transMap?.politicalOutlookName || '-',
  },
  {
    field: 'entryDate',
    label: '入职时间',
  },
  {
    field: 'loginIp',
    label: '最近登录IP',
  },
  {
    field: 'loginDate',
    label: '最近登录时间',
  },
  {
    field: 'address',
    label: '地址',
    span: 2,
  },
  {
    field: 'remark',
    label: '备注',
    span: 2,
  },
  {
    field: 'roleNames',
    label: '关联角色',
    render: (_, data) => data.transMap?.roleName || '-',
    span: 4,
  },
];

export const columns: BasicColumn[] = [
  {
    title: '操作内容',
    dataIndex: 'title',
  },
  {
    title: '请求方式',
    dataIndex: 'requestMethod',
  },
  {
    title: 'ip地址',
    dataIndex: 'ip',
  },
  {
    title: 'ip地址',
    dataIndex: 'ip',
  },
  {
    title: '状态',
    dataIndex: 'status',
    customRender: ({ record }) => record.transMap?.statusName,
  },
  {
    title: '操作时间',
    dataIndex: 'createTime',
  },
];
