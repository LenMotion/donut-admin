import { defHttp } from '@/utils/http/axios';

const basic = '/system/config';

enum Api {
  Basic = basic,
  List = basic + '/list',
  Detail = basic + '/',
  SystemConfig = basic + '/systemConfig',
}

export const systemConfigGetApi = () => {
  return defHttp.get<Recordable>({ url: Api.SystemConfig });
};

export const systemConfigSaveApi = (data) => {
  return defHttp.put<boolean>({ url: Api.SystemConfig, data });
};

export const listApi = (params) => {
  return defHttp.get<Recordable[]>({ url: Api.List, params });
};

export const configByKeyApi = (key) => {
  return defHttp.get<string>({ url: Api.Detail + key });
};

export const saveApi = (data) => {
  return defHttp.post<boolean>({ url: Api.Basic, data });
};

export const deleteApi = (id: number) => {
  return defHttp.delete<boolean>({ url: Api.Detail + id });
};
