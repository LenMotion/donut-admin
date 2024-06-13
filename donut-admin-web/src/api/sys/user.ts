import { defHttp } from '@/utils/http/axios';
import {
  LoginParams,
  LoginResultModel,
  GetUserInfoModel,
  CaptchaImageResultModel,
} from './model/userModel';

import { ErrorMessageMode } from '#/axios';

enum Api {
  Login = '/auth/login',
  CaptchaImage = '/auth/captchaImage',
  Logout = '/auth/logout',
  GetUserInfo = '/system/profile/userInfo',
  UpdateUserInfo = '/system/profile/info',
  UpdateUserAvatar = '/system/profile/avatar',
  UpdateUserPassword = '/system/profile/password',
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

export function doLogout() {
  return defHttp.get({ url: Api.Logout });
}
