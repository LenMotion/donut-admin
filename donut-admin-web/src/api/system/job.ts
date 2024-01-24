import { defHttp } from '@/utils/http/axios';

const basic = '/system/job';

enum Api {
  Basic = basic,
  List = basic + '/list',
  Detail = basic + '/',
  Status = basic + '/status',
  Refresh = basic + '/refresh',
  LogList = basic + '/log/list',
  Exec = basic + '/exec/',
}

export const listApi = (params) => {
  return defHttp.get<Recordable[]>({ url: Api.List, params });
};

export const logListApi = (params) => {
  return defHttp.get<Recordable[]>({ url: Api.LogList, params });
};

export const saveApi = (data) => {
  return defHttp.post<boolean>({ url: basic, data });
};

export const deleteApi = (id: number) => {
  return defHttp.delete<boolean>({ url: Api.Detail + id });
};

export const statusApi = (data) => {
  return defHttp.put<boolean>({ url: Api.Status, data });
};

export const execApi = (id: number) => {
  return defHttp.put<boolean>({ url: Api.Exec + id });
};
