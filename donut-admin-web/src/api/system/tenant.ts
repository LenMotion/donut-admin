import { defHttp } from '@/utils/http/axios';
import { PageResult } from '../model/baseModel';

const basic = '/system/sysTenant';

enum Api {
  Basic = basic,
  List = basic + '/list',
  Detail = basic + '/',
  Status = basic + '/status',
  BaseInfo = basic + '/baseInfo/',
  MenuIdList = basic + '/menuIdList/',
}

export const listApi = (params) => {
  return defHttp.get<PageResult<Recordable>>({ url: Api.List, params });
};

export const baseInfoApi = (ids: string) => {
  return defHttp.get<Recordable[]>({ url: Api.BaseInfo + ids });
};

export const menuIdListApi = (id: string) => {
  return defHttp.get<Recordable>({ url: Api.MenuIdList + id });
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
