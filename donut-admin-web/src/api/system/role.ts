import { defHttp } from '@/utils/http/axios';

const basic = '/system/role';

enum Api {
  Basic = basic,
  List = basic + '/list',
  Detail = basic + '/',
  Options = basic + '/options',
  Status = basic + '/status',
  menuIdList = basic + '/menuIdList/',
}

export const listApi = (params) => {
  return defHttp.get<Recordable[]>({ url: Api.List, params });
};

export const roleUserListApi = (params) => {
  return defHttp.get<Recordable[]>({ url: Api.Detail + 'userList', params });
};

export const unbindUserListApi = (params) => {
  return defHttp.get<Recordable[]>({ url: Api.Detail + 'unbindUserList', params });
};

export const roleUserUnbindApi = (id: string) => {
  return defHttp.post<boolean>({ url: Api.Detail + 'unbind/' + id });
}; 

export const roleUserBindApi = (data: Recordable) => {
  return defHttp.post<boolean>({ url: Api.Detail + 'bind', data });
};

export const optionsApi = () => {
  return defHttp.get<Recordable[]>({ url: Api.Options });
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

export const menuIdApi = (id: number) => {
  return defHttp.get<Recordable>({ url: Api.menuIdList + id });
};
