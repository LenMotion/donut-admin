import { defHttp } from '@/utils/http/axios';

const basic = '/system/notice';

enum Api {
  Basic = basic,
  List = basic + '/list',
  Detail = basic + '/',
  Status = basic + '/status',
  UserNoticeList = basic + '/userNoticeList',
  Read = basic + '/read/',
}

export const listApi = (params) => {
  return defHttp.get<Recordable[]>({ url: Api.List, params });
};

export const userNoticeListApi = (params) => {
  return defHttp.get<Recordable>({ url: Api.UserNoticeList, params });
};

export const detailApi = (id) => {
  return defHttp.get<Recordable>({ url: Api.Detail + id });
};

export const saveApi = (data) => {
  return defHttp.post<boolean>({ url: Api.Basic, data });
};

export const deleteApi = (id: number) => {
  return defHttp.delete<boolean>({ url: Api.Detail + id });
};

export const statusApi = (data) => {
  return defHttp.put<boolean>({ url: Api.Status, data });
};

export const readApi = (id) => {
  return defHttp.post<boolean>({ url: Api.Read + id });
};
