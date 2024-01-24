package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.system.entity.po.SysRole;
import cn.lenmotion.donut.system.entity.po.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * @author lenmotion
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 获取角色关联的数量
     *
     * @param roleId
     * @return
     */
    long countByRoleId(Long roleId);

    /**
     * 获取用户的角色
     * @param userId
     * @return
     */
    Set<String> getRoleKeysByUserId(Long userId);

    /**
     * 获取用户的角色
     * @param userId
     * @return
     */
    List<Long> getRoleIdsByUserId(Long userId);

    /**
     * 获取用户的角色
     * @param userId
     * @return
     */
    List<SysRole> getRolesByUserId(Long userId);

    /**
     * 保存用户的角色
     * @param userId
     * @param roleIdList
     */
    void insertUserRole(Long userId, List<Long> roleIdList);

}
