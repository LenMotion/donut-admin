import { BasicColumn, FormSchema } from '@/components/Table';
import { tableListApi } from '@/api/gen/code';
import { listApi } from '@/api/system/dictType';

export const columns: BasicColumn[] = [
  {
    title: '表名',
    dataIndex: 'tableName',
  },
  {
    title: '功能名',
    dataIndex: 'featureName',
  },
  {
    title: '所属模块',
    dataIndex: 'menuId',
    customRender: ({ record }) => record.transMap?.title,
  },
  {
    title: '模块名称',
    dataIndex: 'moduleName',
  },
  {
    title: '作者',
    dataIndex: 'author',
  },
  {
    title: '包名',
    dataIndex: 'packageName',
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
    field: 'tableName',
    label: '表名',
    component: 'Input',
  },
  {
    field: 'featureName',
    label: '功能名',
    component: 'Input',
  },
];

export const formSchema = (callback: Function): FormSchema[] => {
  return [
    {
      field: 'id',
      label: 'Id',
      show: false,
      component: 'Input',
    },
    {
      field: 'tableName',
      label: '表名',
      required: true,
      component: 'ApiSelect',
      componentProps: ({ formActionType }) => {
        return {
          api: tableListApi,
          labelField: 'tableName',
          valueField: 'tableName',
          onChange: (value, genTable) => {
            if (value && genTable) {
              const { tableName, featureName, className } = genTable;
              formActionType.setFieldsValue({ tableName, featureName, className });
              callback(value);
            }
          },
        };
      },
    },
    {
      field: 'featureName',
      label: '功能名',
      required: true,
      component: 'Input',
    },
    {
      field: 'packageName',
      label: '包名',
      required: true,
      defaultValue: 'cn.lenmotion.donut',
      component: 'Input',
    },
    {
      field: 'moduleName',
      label: '模块名称',
      required: true,
      component: 'Input',
    },
    {
      field: 'menuId',
      label: '上级菜单',
      required: true,
      component: 'TreeSelect',
      componentProps: {
        fieldNames: {
          label: 'title',
          value: 'id',
        },
        getPopupContainer: () => document.body,
      },
    },
    {
      field: 'author',
      label: '作者',
      required: true,
      defaultValue: 'lenmotion',
      component: 'InputNumber',
    },
    {
      field: 'superClass',
      label: '继承父类',
      required: false,
      component: 'Select',
      componentProps: {
        options: [
          { label: 'BaseCreatePo', value: 'cn.lenmotion.donut.core.entity.BaseCreatePo' },
          { label: 'BasePo', value: 'cn.lenmotion.donut.core.entity.BasePo' },
        ],
      },
    },
    {
      field: 'className',
      label: '类名',
      required: true,
      component: 'Input',
    },
    {
      field: 'statusApi',
      label: '生成状态接口',
      defaultValue: true,
      required: true,
      component: 'Switch',
    },
    {
      field: 'remark',
      label: '备注',
      component: 'InputTextArea',
    },
  ];
};

export const fieldColumns: BasicColumn[] = [
  {
    dataIndex: 'columnName',
    title: '列名',
    fixed: 'left',
  },
  {
    dataIndex: 'columnType',
    title: '类型',
  },
  {
    dataIndex: 'columnRemark',
    title: '备注',
    fixed: 'left',
    editRow: true,
  },
  {
    dataIndex: 'fieldName',
    title: 'Java字段名',
    editRow: true,
  },
  {
    dataIndex: 'javaTypeClass',
    title: 'java类型',
    editRow: true,
    editComponent: 'Select',
    editComponentProps: () => {
      return {
        options: [
          { label: 'String', value: 'java.lang.String' },
          { label: 'Integer', value: 'java.lang.Integer' },
          { label: 'Long', value: 'java.lang.Long' },
          { label: 'BigDecimal', value: 'java.math.BigDecimal' },
          { label: 'Float', value: 'java.lang.Float' },
          { label: 'Double', value: 'java.lang.Double' },
          { label: 'LocalDate', value: 'java.time.LocalDate' },
          { label: 'LocalTime', value: 'java.time.LocalTime' },
          { label: 'LocalDateTime', value: 'java.time.LocalDateTime' },
          { label: 'Boolean', value: 'java.lang.Boolean' },
        ],
      };
    },
  },
  {
    dataIndex: 'idField',
    title: '主键',
    editRow: true,
    editComponent: 'Checkbox',
    editValueMap: (value) => {
      return value ? '是' : '否';
    },
  },
  {
    dataIndex: 'searchField',
    title: '查询列',
    editRow: true,
    editComponent: 'Checkbox',
    editValueMap: (value) => {
      return value ? '是' : '否';
    },
  },
  {
    dataIndex: 'searchFieldType',
    title: '查询列组件',
    editRow: true,
    editComponent: 'Select',
    editComponentProps: () => {
      return {
        options: [
          { label: 'Input', value: 'Input' },
          { label: 'InputNumber', value: 'InputNumber' },
          { label: 'Select', value: 'Select' },
          { label: 'Checkbox', value: 'Checkbox' },
          { label: 'Switch', value: 'Switch' },
          { label: 'DatePicker', value: 'DatePicker' },
          { label: 'TimePicker', value: 'TimePicker' },
          { label: 'RangePicker', value: 'RangePicker' },
          { label: 'AutoComplete', value: 'AutoComplete' },
        ],
      };
    },
  },
  {
    dataIndex: 'editField',
    title: '编辑列',
    editRow: true,
    editComponent: 'Checkbox',
    editValueMap: (value) => {
      return value ? '是' : '否';
    },
  },
  {
    dataIndex: 'editFieldType',
    title: '编辑列组件',
    editRow: true,
    editComponent: 'Select',
    editComponentProps: () => {
      return {
        options: [
          { label: 'Input', value: 'Input' },
          { label: 'InputNumber', value: 'InputNumber' },
          { label: 'Select', value: 'Select' },
          { label: 'Checkbox', value: 'Checkbox' },
          { label: 'Switch', value: 'Switch' },
          { label: 'DatePicker', value: 'DatePicker' },
          { label: 'TimePicker', value: 'TimePicker' },
          { label: 'AutoComplete', value: 'AutoComplete' },
        ],
      };
    },
  },
  {
    dataIndex: 'tableField',
    title: '列表列',
    editRow: true,
    editComponent: 'Checkbox',
    editValueMap: (value) => {
      return value ? '是' : '否';
    },
  },
  {
    dataIndex: 'dictKey',
    title: '关联字典key',
    width: 240,
    editRow: true,
    editComponent: 'ApiSelect',
    editComponentProps: () => {
      return {
        api: listApi,
        params: { pageSize: 999 },
        labelField: 'dictName',
        valueField: 'dictKey',
        resultField: 'items',
        showSearch: true,
        filterOption: (input: string, option: any) => {
          console.log(option);
          return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
        },
      };
    },
  },
  {
    dataIndex: 'sortIndex',
    width: 120,
    title: '排序',
    editRow: true,
    editComponent: 'InputNumber',
  },
];
