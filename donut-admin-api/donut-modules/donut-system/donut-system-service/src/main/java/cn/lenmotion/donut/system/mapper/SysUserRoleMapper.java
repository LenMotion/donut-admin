package cn.lenmotion.donut.system.mapper;

import cn.lenmotion.donut.system.entity.po.SysRole;
import cn.lenmotion.donut.system.entity.po.SysUserRole;
import cn.lenmotion.donut.system.entity.query.RoleUserQuery;
import cn.lenmotion.donut.system.entity.vo.RoleUserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @author lenmotion
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 获取用户的角色
     * @param userId
     * @return
     */
    List<SysRole> getRoleIdsByUserId(Long userId);

    /**
     * 根据角色id查询角色关联的用户
     * @param query
     * @return
     */
    IPage<RoleUserVO> selectUserList(IPage<?> page, @Param("query") RoleUserQuery query);

    /**
     * 根据角色id查询角色关联的用户
     * @param query
     * @return
     */
    IPage<RoleUserVO> unbindUserList(IPage<?> page, @Param("query") RoleUserQuery query);

}