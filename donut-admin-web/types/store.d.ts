import { ErrorTypeEnum } from '@/enums/exceptionEnum';
import { MenuModeEnum, MenuTypeEnum } from '@/enums/menuEnum';
// Lock screen information
export interface LockInfo {
  // Password required
  pwd?: string | undefined;
  // Is it locked?
  isLock?: boolean;
}

export interface ApiAddress {
  key: string;
  val: string;
}

// Error-log information
export interface ErrorLogInfo {
  // Type of error
  type: ErrorTypeEnum;
  // Error file
  file: string;
  // Error name
  name?: string;
  // Error message
  message: string;
  // Error stack
  stack?: string;
  // Error detail
  detail: string;
  // Error url
  url: string;
  // Error time
  time?: string;
}

export interface UserInfo {
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
  homePath?: string;
  quickNav: string[];
  quickNavMenus: any[];
}

export interface BeforeMiniState {
  menuCollapsed?: boolean;
  menuSplit?: boolean;
  menuMode?: MenuModeEnum;
  menuType?: MenuTypeEnum;
}

export interface TableSetting {
  size: Nullable<SizeType>;
  showIndexColumn: Nullable<boolean>;
  columns: Recordable<Nullable<Array<ColumnOptionsType>>>;
  showRowSelection: Nullable<boolean>;
}
