package cn.lenmotion.donut.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.system.entity.po.SysMenu;
import cn.lenmotion.donut.system.entity.po.SysRoleMenu;
import cn.lenmotion.donut.system.entity.vo.RoleMenuIdVO;
import cn.lenmotion.donut.system.mapper.SysMenuMapper;
import cn.lenmotion.donut.system.mapper.SysRoleMenuMapper;
import cn.lenmotion.donut.system.service.SysRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    private final SysMenuMapper menuMapper;

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

    @Override
    public void clearAllRoleMenu(Collection<Long> menuIds) {
        if (CollUtil.isEmpty(menuIds)) {
            return;
        }
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleMenu::getMenuId, menuIds);
        this.remove(queryWrapper);
    }

    @Override
    public void setHalfMenu(Collection<Long> menuIds) {
        if (CollUtil.isEmpty(menuIds)) {
            return;
        }
        List<SysMenu> menuList = menuMapper.selectBatchIds(menuIds);
        // 查找上级菜单，把有这些菜单的全部设置为半选
        Set<Long> parentIds = new HashSet<>();
        for (SysMenu menu : menuList) {
            this.findParent(menu, parentIds);
        }
        LambdaUpdateWrapper<SysRoleMenu> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(SysRoleMenu::getMenuId, parentIds)
                .set(SysRoleMenu::getHalfMenu, true);
        this.update(null, updateWrapper);
    }

    /**
     * 查找上级的菜单
     */
    private void findParent(SysMenu sysMenu, Set<Long> parentIds) {
        if (sysMenu == null || sysMenu.getParentId().equals(BaseConstants.PARENT_ID)) {
            return;
        }
        parentIds.add(sysMenu.getParentId());
        this.findParent(menuMapper.selectById(sysMenu.getParentId()), parentIds);
    }
}
