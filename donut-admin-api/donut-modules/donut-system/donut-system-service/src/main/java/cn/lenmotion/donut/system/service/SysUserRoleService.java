package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.system.entity.po.SysRole;
import cn.lenmotion.donut.system.entity.po.SysUserRole;
import cn.lenmotion.donut.system.entity.query.RoleUserQuery;
import cn.lenmotion.donut.system.entity.request.RoleBindUserRequest;
import cn.lenmotion.donut.system.entity.request.RoleUnbindUserRequest;
import cn.lenmotion.donut.system.entity.vo.RoleUserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * @author lenmotion
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 获取角色关联的数量
     */
    long countByRoleId(Long roleId);

    /**
     * 获取用户的角色
     */
    Set<String> getRoleKeysByUserId(Long userId);

    /**
     * 获取用户的角色
     */
    List<Long> getRoleIdsByUserId(Long userId);

    /**
     * 获取用户的角色
     */
    List<SysRole> getRolesByUserId(Long userId);

    /**
     * 保存用户的角色
     */
    void insertUserRole(Long userId, List<Long> roleIdList);

    /**
     * 根据角色id查询角色关联的用户
     */
    IPage<RoleUserVO> selectUserList(RoleUserQuery query);

    /**
     * 根据角色id查询角色关联的用户
     */
    IPage<RoleUserVO> unbindUserList(RoleUserQuery query);

    /**
     * 角色绑定用户
     */
    Boolean bindUser(RoleBindUserRequest request);

    /**
     * 角色解绑用户
     */
    Boolean unbind(SysUserRole userRole);

}
