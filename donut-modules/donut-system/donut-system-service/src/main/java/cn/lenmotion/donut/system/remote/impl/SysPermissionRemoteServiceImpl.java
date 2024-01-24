package cn.lenmotion.donut.system.remote.impl;

import cn.lenmotion.donut.core.enums.DataScopeEnum;
import cn.lenmotion.donut.core.utils.EnumUtils;
import cn.lenmotion.donut.system.entity.po.SysRole;
import cn.lenmotion.donut.system.remote.SysPermissionRemoteService;
import cn.lenmotion.donut.system.service.SysRoleMenuService;
import cn.lenmotion.donut.system.service.SysUserDeptService;
import cn.lenmotion.donut.system.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class SysPermissionRemoteServiceImpl implements SysPermissionRemoteService {

    private final SysRoleMenuService roleMenuService;
    private final SysUserRoleService userRoleService;
    private final SysUserDeptService userDeptService;

    @Override
    public Set<String> getRolePermission(Long userId) {
        return userRoleService.getRoleKeysByUserId(userId);
    }

    @Override
    public Set<String> getMenuPermission(Long userId) {
        return roleMenuService.getPermsByUserId(userId);
    }

    @Override
    public Set<String> getDeptAncestors(Long userId) {
        return userDeptService.getDeptAncestorsByUserId(userId);
    }

    @Override
    public Set<DataScopeEnum> getRoleDataScope(Long userId) {
        List<SysRole> roleList = userRoleService.getRolesByUserId(userId);

        return roleList.stream()
                .map(SysRole::getDataScope)
                .map(e -> EnumUtils.getByCode(DataScopeEnum.class, e))
                .collect(Collectors.toSet());
    }

}
