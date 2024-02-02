import { defHttp } from '@/utils/http/axios';
import {
  LoginParams,
  LoginResultModel,
  GetUserInfoModel,
  CaptchaImageResultModel,
} from './model/userModel';

import { ErrorMessageMode } from '#/axios';

enum Api {
  Login = '/login',
  CaptchaImage = '/captchaImage',
  Logout = '/logout',
  GetUserInfo = '/profile/userInfo',
  UpdateUserInfo = '/profile/info',
  UpdateUserAvatar = '/profile/avatar',
  UpdateUserPassword = '/profile/password',
  GetPermCode = '/profile/permissionCode',
  TestRetry = '/testRetry',
}

/**
 * @description: user login api
 */
export function loginApi(params: LoginParams, mode: ErrorMessageMode = 'modal') {
  return defHttp.post<LoginResultModel>(
    {
      url: Api.Login,
      params,
    },
    {
      errorMessageMode: mode,
      withToken: false,
    },
  );
}

/**
 * @description: getUserInfo
 */
export function getUserInfo() {
  return defHttp.get<GetUserInfoModel>({ url: Api.GetUserInfo }, { errorMessageMode: 'none' });
}

export function updateUserInfo(data) {
  return defHttp.put<boolean>({ url: Api.UpdateUserInfo, data });
}

export function updateUserAvatar(data) {
  return defHttp.put<boolean>({ url: Api.UpdateUserAvatar, data });
}

export function updateUserPassword(data) {
  return defHttp.put<boolean>({ url: Api.UpdateUserPassword, data });
}

export function captchaImageApi() {
  return defHttp.get<CaptchaImageResultModel>({ url: Api.CaptchaImage }, { withToken: false });
}

export function getPermCode() {
  return defHttp.get<string[]>({ url: Api.GetPermCode });
}

export function doLogout() {
  return defHttp.get({ url: Api.Logout });
}

export function testRetry() {
  return defHttp.get(
    { url: Api.TestRetry },
    {
      retryRequest: {
        isOpenRetry: true,
        count: 5,
        waitTime: 1000,
      },
    },
  );
}
