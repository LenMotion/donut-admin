/**
 * @description: Login interface parameters
 */
export interface LoginParams {
  username: string;
  password: string;
  code: string;
  uuid: string;
}

export interface CaptchaImageResultModel {
  img: string;
  uuid: string;
  captchaOnOff: boolean;
}

export interface RoleInfo {
  roleName: string;
  value: string;
}

/**
 * @description: Login interface return value
 */
export interface LoginResultModel {
  tokenName: string;
  tokenValue: string;
  isLogin: boolean;
  loginId: any; // 或者根据实际情况指定类型
  loginType: string;
  tokenTimeout: number;
  sessionTimeout: number;
  tokenSessionTimeout: number;
  tokenActiveTimeout: number;
  loginDevice: string;
  tag: string;
}

/**
 * @description: Get user information return value
 */
export interface GetUserInfoModel {
  roles: string[];
  perms: string[];
  // 用户id
  userId: string | number;
  // 用户名
  username: string;
  // 头像
  avatarUrl: string;
  // 介绍
  desc?: string;
  id: number;
  deptId: number;
  nickName: string;
  email: string;
  phoneNumber: string;
  sex: string;
  status: string;
  transMap: Map<string, any>;
  quickNav: string[];
  quickNavMenus: any[];
}
