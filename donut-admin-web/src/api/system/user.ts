import { defHttp } from '@/utils/http/axios';
import { useGlobSetting } from '@/hooks/setting';

const basic = '/system/user';

enum Api {
  Basic = basic,
  List = basic + '/list',
  Detail = basic + '/',
  Status = basic + '/status',
  Export = basic + '/export',
  Import = basic + '/importData',
  ExportTemp = basic + '/exportTemplate',
}

export const listApi = (params) => {
  return defHttp.get<Recordable[]>({ url: Api.List, params });
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

export const exportApi = (params) => {
  return defHttp.get<string>({ url: Api.Export, params });
};

export const exportTempApi = () => {
  return defHttp.get<string>({ url: Api.ExportTemp });
};

const { apiUrl } = useGlobSetting();
export const importApi = (params) => {
  // return defHttp.post<Recordable>({ url: Api.Import, data });
  return defHttp.uploadFile<Recordable>(
    {
      url: apiUrl + Api.Import,
    },
    params,
  );
};
