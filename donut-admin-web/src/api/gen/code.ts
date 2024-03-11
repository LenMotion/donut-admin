import { defHttp } from '@/utils/http/axios';

const basic = '/gen/code';

enum Api {
  Basic = basic,
  List = basic + '/list',
  TableList = basic + '/tableList',
  TableColumns = basic + '/tableColumns/',
  TableColumnsByTable = basic + '/tableColumnsByTable/',
  Detail = basic + '/',
  Preview = basic + '/preview',
}

export const listApi = (params) => {
  return defHttp.get<Recordable[]>({ url: Api.List, params });
};

export const deleteApi = (id: number) => {
  return defHttp.delete<boolean>({ url: Api.Detail + id });
};

export const saveApi = (data) => {
  return defHttp.post<boolean>({ url: Api.Basic, data });
};

export const tableListApi = (data) => {
  return defHttp.get<Recordable[]>({ url: Api.TableList, data });
};

export const tableColumnsApi = (tableName: string) => {
  return defHttp.get<Recordable[]>({ url: Api.TableColumns + tableName });
};

export const tableColumnsByTableApi = (tableId: number) => {
  return defHttp.get<Recordable[]>({ url: Api.TableColumnsByTable + tableId });
};

export const previewApi = (params: any) => {
  return defHttp.get<Recordable>({ url: Api.Preview, params });
};
