package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.system.entity.po.SysMenu;
import cn.lenmotion.donut.system.entity.po.SysRoleMenu;
import cn.lenmotion.donut.system.entity.vo.RoleMenuIdVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author lenmotion
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 清除角色的菜单
     *
     * @param list
     */
    void clearRoleMenu(Collection<?> list);

    /**
     * 获取菜单绑定的角色数量
     * @param menuId
     * @return
     */
    long countByMenuId(Serializable menuId);

    /**
     * 保存菜单
     *
     * @param roleId
     * @param menuIds
     * @param halfMenu
     */
    void saveRoleMenu(Long roleId, Long[] menuIds, boolean halfMenu);

    /**
     *  获取角色绑定的菜单ID列表
     * @param roleId
     * @return
     */
    RoleMenuIdVO getMenuIdListByRoleId(Long roleId);

    /**
     * 获取用户的权限
     * @param userId
     * @return
     */
    Set<String> getPermsByUserId(Long userId);

    /**
     * 获取菜单
     * @param userId
     * @return
     */
    List<SysMenu> getMenuListByUserId(Long userId);

    /**
     * 删除所有角色中有此权限的信息
     * @param menuIds
     */
    void clearAllRoleMenu(Collection<Long> menuIds);

    /**
     * 设置半选菜单
     * @param menuIds
     */
    void setHalfMenu(Collection<Long> menuIds);

}
