import { BasicColumn, FormSchema } from '@/components/Table';
import { h } from 'vue';
import { Tag } from 'ant-design-vue';
import Icon from '@/components/Icon/Icon.vue';
import { dictSelectProps } from '@/api/system/dictData';

export const columns: BasicColumn[] = [
  {
    title: '菜单名称',
    dataIndex: 'title',
    width: 200,
    align: 'left',
  },
  {
    title: '图标',
    dataIndex: 'icon',
    width: 50,
    customRender: ({ record }) => {
      return record.icon ? h(Icon, { icon: record.icon }) : undefined;
    },
  },
  {
    title: '组件名称',
    dataIndex: 'name',
    width: 150,
  },
  {
    title: '路由地址',
    dataIndex: 'path',
    width: 180,
  },
  {
    title: '组件',
    dataIndex: 'component',
  },
  {
    title: '权限标识',
    dataIndex: 'perms',
    width: 180,
  },
  {
    title: '排序',
    dataIndex: 'orderNo',
    sorter: true,
    width: 50,
  },
  {
    title: '状态',
    dataIndex: 'status',
    width: 80,
    customRender: ({ record }) => {
      const status = record.status;
      const enable = ~~status === 0;
      const color = enable ? 'green' : 'red';
      const text = enable ? '启用' : '停用';
      return h(Tag, { color: color }, () => text);
    },
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    width: 180,
  },
];

const isDir = (type: string) => type === '0';
const isMenu = (type: string) => type === '1';
const isButton = (type: string) => type === '2';

export const searchFormSchema: FormSchema[] = [
  {
    field: 'menuName',
    label: '菜单名称',
    component: 'Input',
    colProps: { span: 8 },
  },
  {
    field: 'status',
    label: '状态',
    component: 'Select',
    componentProps: {
      options: [
        { label: '启用', value: '0' },
        { label: '停用', value: '1' },
      ],
    },
    colProps: { span: 8 },
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
    field: 'menuType',
    label: '菜单类型',
    component: 'RadioButtonGroup',
    defaultValue: '0',
    componentProps: {
      options: [
        { label: '目录', value: '0' },
        { label: '菜单', value: '1' },
        { label: '按钮', value: '2' },
      ],
    },
    colProps: { lg: 24, md: 24 },
  },
  {
    field: 'title',
    label: '菜单名称',
    component: 'Input',
    required: true,
  },
  {
    field: 'name',
    label: '组件名称',
    component: 'Input',
    required: true,
    componentProps: { placeholder: '唯一名称，大写字母开头' },
    ifShow: ({ values }) => !isButton(values.menuType),
  },
  {
    field: 'parentId',
    label: '上级菜单',
    component: 'TreeSelect',
    required: true,
    componentProps: {
      fieldNames: {
        label: 'title',
        value: 'id',
      },
      getPopupContainer: () => document.body,
    },
  },
  {
    field: 'icon',
    label: '图标',
    component: 'IconPicker',
    ifShow: ({ values }) => !isButton(values.menuType),
  },
  {
    field: 'path',
    label: '路由地址',
    component: 'Input',
    required: true,
    ifShow: ({ values }) => !isButton(values.menuType),
  },
  {
    field: 'component',
    label: '组件路径',
    component: 'Input',
    required: ({ values }) => !isButton(values.menuType),
    ifShow: ({ values }) => !isButton(values.menuType),
  },
  {
    field: 'perms',
    label: '权限标识',
    component: 'Input',
    required: ({ values }) => isButton(values.menuType),
    ifShow: ({ values }) => !isDir(values.menuType),
  },
  {
    field: 'orderNo',
    label: '排序',
    component: 'InputNumber',
    required: true,
  },
  {
    field: 'redirect',
    label: '重定向',
    component: 'Input',
    ifShow: ({ values }) => isDir(values.menuType),
  },
  {
    field: 'currentActiveMenu',
    label: '激活路由',
    component: 'Input',
    ifShow: ({ values }) => isMenu(values.menuType),
    componentProps: { placeholder: '详情页时左侧激活的菜单路径' },
  },
  {
    field: 'status',
    label: '状态',
    component: 'ApiSelect',
    defaultValue: '0',
    required: true,
    componentProps: dictSelectProps('sys_base_status', false),
  },
  {
    field: 'frame',
    label: '是否外链',
    component: 'RadioButtonGroup',
    defaultValue: false,
    componentProps: {
      options: [
        { label: '否', value: false },
        { label: '是', value: true },
      ],
    },
    ifShow: ({ values }) => !isButton(values.menuType),
  },

  {
    field: 'ignoreKeepAlive',
    label: '忽略缓存',
    component: 'RadioButtonGroup',
    defaultValue: true,
    componentProps: {
      options: [
        { label: '是', value: true },
        { label: '否', value: false },
      ],
    },
    ifShow: ({ values }) => isMenu(values.menuType),
  },

  {
    field: 'hideMenu',
    label: '是否隐藏',
    component: 'RadioButtonGroup',
    defaultValue: false,
    componentProps: {
      options: [
        { label: '否', value: false },
        { label: '是', value: true },
      ],
    },
    ifShow: ({ values }) => !isButton(values.menuType),
  },
  {
    field: 'hideChildrenInMenu',
    label: '隐藏子菜单',
    component: 'RadioButtonGroup',
    defaultValue: false,
    componentProps: {
      options: [
        { label: '否', value: false },
        { label: '是', value: true },
      ],
    },
    ifShow: ({ values }) => isDir(values.menuType),
  },
];
