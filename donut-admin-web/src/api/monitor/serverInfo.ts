import { defHttp } from '@/utils/http/axios';

const basic = '/monitor';

enum Api {
  Basic = basic,
  Redis = basic + '/redis',
  Server = basic + '/server',
}

export const redisInfoApi = () => {
  return defHttp.get<Recordable>({ url: Api.Redis });
};

export const serverInfoApi = () => {
  return defHttp.get<Recordable>({ url: Api.Server });
};
