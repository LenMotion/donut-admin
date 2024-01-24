import { defHttp } from '@/utils/http/axios';

const basic = '/system/fileStorage';

enum Api {
  Basic = basic,
  List = basic + '/list',
}

export const listApi = (params) => {
  return defHttp.get<Recordable[]>({ url: Api.List, params });
};
