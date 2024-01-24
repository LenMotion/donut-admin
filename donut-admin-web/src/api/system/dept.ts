import { defHttp } from '@/utils/http/axios';

const basic = '/system/dept';

enum Api {
  Basic = basic,
  Tree = basic + '/tree',
  EnableTree = basic + '/enableTree',
  Detail = basic + '/',
  Status = basic + '/status',
}

export const treeApi = () => {
  return defHttp.get<Recordable[]>({ url: Api.Tree });
};

export const enableTreeApi = () => {
  return defHttp.get<Recordable[]>({ url: Api.EnableTree });
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
