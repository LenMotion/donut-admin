import { BasicColumn, FormSchema } from '@/components/Table';
import { listApi } from '@/api/system/dictType';
import { enableListApi } from '@/api/gen/datasource';
import { tableListApi } from '@/api/gen/code';
import { menuTreeApi } from '@/api/system/menu';

export const fieldColumns: BasicColumn[] = [
  {
    dataIndex: 'sortIndex',
    width: 120,
    title: '排序',
    editRow: true,
    editComponent: 'InputNumber',
  },
  {
    dataIndex: 'columnName',
    title: '列名',
    fixed: 'left',
  },
  {
    dataIndex: 'columnType',
    title: 'SQL类型',
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
];

export const formSchema = (): FormSchema[] => {
  return [
    {
      field: 'id',
      label: 'Id',
      show: false,
      component: 'Input',
    },
    {
      field: 'datasourceId',
      label: '数据源',
      required: true,
      component: 'ApiSelect',
      componentProps: () => {
        return {
          api: enableListApi,
          labelField: 'name',
          valueField: 'id',
        };
      },
    },
    {
      field: 'tableName',
      label: '表名',
      required: true,
      component: 'ApiSelect',
      componentProps: ({ formModel, formActionType }) => {
        return {
          immediate: false,
          params: { datasourceId: formModel.datasourceId },
          api: tableListApi,
          labelField: 'tableName',
          valueField: 'tableName',
          onChange: (value, genTable) => {
            if (value && genTable) {
              const { tableName, featureName, className } = genTable;
              formActionType.setFieldsValue({ tableName, featureName, className });
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
      component: 'ApiTreeSelect',
      componentProps: {
        api: menuTreeApi,
        // fieldNames: {
        //   label: 'title',
        //   value: 'id',
        // },
        valueField: 'id',
        labelField: 'title',
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
