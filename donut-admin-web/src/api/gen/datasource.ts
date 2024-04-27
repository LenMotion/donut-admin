import { defHttp } from '@/utils/http/axios';
import { PageResult } from '../model/baseModel';

const basic = '/generator/genDatasource';

enum Api {
  Basic = basic,
  List = basic + '/list',
  Detail = basic + '/',
  Status = basic + '/status',
  EnableList = basic + '/enableList',
  CheckConnection = basic + '/checkConnection/',
}

export const listApi = (params) => {
  return defHttp.get<PageResult<Recordable>>({ url: Api.List, params });
};

export const enableListApi = () => {
  return defHttp.get<Recordable[]>({ url: Api.EnableList });
};

export const checkConnectionApi = (id) => {
  return defHttp.post<boolean>({ url: Api.CheckConnection + id });
};

export const saveApi = (data) => {
  return defHttp.post<boolean>({ url: Api.Basic, data });
};

export const statusApi = (data) => {
  return defHttp.put<boolean>({ url: Api.Status, data });
};

export const deleteApi = (id: number) => {
  return defHttp.delete<boolean>({ url: Api.Detail + id });
};
