import { defHttp } from '@/utils/http/axios';
import { MenuResultModel } from './model/menuModel';

const basic = '/system/menu';

enum Api {
  Basic = basic,
  Tree = basic + '/tree',
  Detail = basic + '/',
  UserMenu = basic + '/user/',
  Status = basic + '/status',
}

export const menuTreeApi = () => {
  return defHttp.get<MenuResultModel[]>({ url: Api.Tree });
};

export const userMenuApi = (userId) => {
  return defHttp.get<MenuResultModel[]>({ url: Api.UserMenu + userId });
};

export const saveApi = (data) => {
  return defHttp.post<MenuResultModel>({ url: Api.Basic, data });
};

export const statusApi = (data) => {
  return defHttp.put<boolean>({ url: Api.Status, data });
};

export const deleteApi = (id: number) => {
  return defHttp.delete<boolean>({ url: Api.Detail + id });
};
