package cn.lenmotion.donut.system.mapper;

import cn.lenmotion.donut.system.entity.po.SysRole;
import cn.lenmotion.donut.system.entity.po.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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

}