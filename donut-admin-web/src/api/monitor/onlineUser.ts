import { defHttp } from '@/utils/http/axios';

const basic = '/monitor/onlineUser';

enum Api {
  List = basic + '/list',
  KickOut = basic + '/kickOut/',
}

export const listApi = () => {
  return defHttp.get<Recordable>({ url: Api.List });
};

export const kickOutApi = (id: string) => {
  return defHttp.delete<boolean>({ url: Api.KickOut + id });
};
