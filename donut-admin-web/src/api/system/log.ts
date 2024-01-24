import { defHttp } from '@/utils/http/axios';

const basic = '/system/log';

enum Api {
  LoginList = basic + '/loginLogList',
  ExportLoginLog = basic + '/exportLoginLog',
  OperationList = basic + '/operationLogList',
  UserLoginList = basic + '/userLoginLogList',
  UserOperationList = basic + '/userOperationLogList',
}

export const loginLogListApi = (params) => {
  return defHttp.get<Recordable[]>({ url: Api.LoginList, params });
};

export const exportLoginLogApi = (params) => {
  return defHttp.get<string>({ url: Api.ExportLoginLog, params });
};

export const operationLogListApi = (params) => {
  return defHttp.get<Recordable[]>({ url: Api.OperationList, params });
};

export const userLoginLogListApi = (params) => {
  return defHttp.get<Recordable>({ url: Api.UserLoginList, params });
};

export const userOperationLogListApi = (params) => {
  return defHttp.get<Recordable>({ url: Api.UserOperationList, params });
};
