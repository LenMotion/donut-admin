export interface MenuResultModel {
  id: number; // 菜单ID
  title: string; // 菜单名称
  name: string; // 页面名称 全局唯一
  parentId: number; // 父菜单ID，默认为0
  menuType: 'M' | 'C' | 'F'; // 菜单类型（0目录 1菜单 2按钮）
  path: string; // 路由地址
  currentActiveMenu: string | null; // 当前激活的菜单。用于配置详情页时左侧激活的菜单路径
  redirect: string | null; // 重定向
  component: string | null; // 组件路径
  ignoreKeepAlive: boolean; // 是否忽略缓存（0不忽略 1忽略）
  hideMenu: boolean; // 菜单状态（0显示 1隐藏）
  hideChildrenInMenu: boolean; // 隐藏所有子菜单
  status: string; // 菜单状态（0正常 1停用）
  perms: string | null; // 权限标识
  icon: string; // 菜单图标
  orderNo: number; // 显示顺序
  query: string | null; // 路由参数
  frame: boolean; // 是否为外链（0否 1是）
  createBy: string; // 创建者
  createTime: string | null; // 创建时间
  updateBy: string; // 更新者
  updateTime: string | null; // 更新时间
  remark: string; // 备注
}
