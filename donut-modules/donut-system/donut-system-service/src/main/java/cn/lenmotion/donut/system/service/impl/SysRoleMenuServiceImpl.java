package cn.lenmotion.donut.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.system.entity.po.SysMenu;
import cn.lenmotion.donut.system.entity.po.SysRoleMenu;
import cn.lenmotion.donut.system.entity.vo.RoleMenuIdVO;
import cn.lenmotion.donut.system.mapper.SysRoleMenuMapper;
import cn.lenmotion.donut.system.service.SysRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lenmotion
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Override
    public void clearRoleMenu(Collection<?> roleIdList) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleMenu::getRoleId, roleIdList);
        this.remove(queryWrapper);
    }

    @Override
    public long countByMenuId(Serializable menuId) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getMenuId, menuId);
        return this.count(queryWrapper);
    }

    @Override
    public void saveRoleMenu(Long roleId, Long[] menuIds, boolean halfMenu) {
        if (ArrayUtil.isEmpty(menuIds)) {
            return;
        }

        for (Long menuId : menuIds) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setHalfMenu(halfMenu);
            this.save(sysRoleMenu);
        }
    }

    @Override
    public RoleMenuIdVO getMenuIdListByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId, roleId);

        List<SysRoleMenu> roleMenus = list(queryWrapper);

        RoleMenuIdVO roleMenuIdVO = new RoleMenuIdVO();
        roleMenuIdVO.setMenuIds(roleMenus.stream().filter(e -> !e.getHalfMenu()).map(SysRoleMenu::getMenuId).collect(Collectors.toSet()));
        roleMenuIdVO.setHalfMenuIds(roleMenus.stream().filter(SysRoleMenu::getHalfMenu).map(SysRoleMenu::getMenuId).collect(Collectors.toSet()));
        return roleMenuIdVO;
    }

    @Override
    public Set<String> getPermsByUserId(Long userId) {
        if (userId == null) {
            return CollUtil.newHashSet();
        }
        if (BaseConstants.SUPER_ID.equals(userId)) {
            return Collections.singleton("*:*:*");
        }
        return getBaseMapper().getPermsByUserId(userId);
    }

    @Override
    public List<SysMenu> getMenuListByUserId(Long userId) {
        return getBaseMapper().getMenuListByUserId(userId);
    }

}
