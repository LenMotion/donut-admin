package cn.lenmotion.donut.system.mapper;

import cn.lenmotion.donut.system.entity.po.SysMenu;
import cn.lenmotion.donut.system.entity.po.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author lenmotion
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

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

}