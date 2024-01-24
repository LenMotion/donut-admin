package cn.lenmotion.donut.system.remote;

import cn.lenmotion.donut.core.enums.DataScopeEnum;

import java.util.Set;

/**
 * @author lenmotion
 */
public interface SysPermissionRemoteService {

    /**
     * 获取角色数据权限
     *
     * @param userId 用户信息
     * @return 角色权限信息
     */
    Set<String> getRolePermission(Long userId);

    /**
     * 获取菜单数据权限
     *
     * @param userId 用户信息
     * @return 菜单权限信息
     */
    Set<String> getMenuPermission(Long userId);

    /**
     * 获取部门的权限信息
     * @param userId
     * @return
     */
    Set<String> getDeptAncestors(Long userId);

    /**
     * 获取角色数据权限
     *
     * @param userId 用户信息
     * @return 角色权限信息
     */
    Set<DataScopeEnum> getRoleDataScope(Long userId);

}
