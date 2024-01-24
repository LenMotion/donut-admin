import { defHttp } from '@/utils/http/axios';

const basic = '/system/dictType';

enum Api {
  Basic = basic,
  List = basic + '/list',
  Detail = basic + '/',
  Status = basic + '/status',
}

export const listApi = (params) => {
  return defHttp.get<Recordable[]>({ url: Api.List, params });
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
