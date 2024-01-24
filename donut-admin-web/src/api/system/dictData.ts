import { defHttp } from '@/utils/http/axios';

const basic = '/system/dict/data';

enum Api {
  Basic = basic,
  List = basic + '/list',
  Detail = basic + '/',
  Status = basic + '/status',
}

export const listApi = (params) => {
  return defHttp.get<Recordable[]>({ url: Api.List, params });
};

export const saveApi = (data: Recordable) => {
  return defHttp.post<boolean>({ url: Api.Basic, data });
};

export const deleteApi = (id: number) => {
  return defHttp.delete<boolean>({ url: Api.Detail + id });
};

export const statusApi = (data) => {
  return defHttp.put<boolean>({ url: Api.Status, data });
};

export const dictDataApi = (key: string) => {
  return defHttp.get<Recordable[]>({ url: Api.Detail + key });
};

export const dictSelectProps = (dictKey: string, allowClear = true) => {
  return {
    api: listApi,
    params: { dictKey, pageSize: 500 },
    resultField: 'items',
    // 指定label和value的键名
    labelField: 'dictLabel',
    valueField: 'dictValue',
    allowClear,
  };
};
