package cn.lenmotion.donut.auth.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import cn.lenmotion.donut.system.remote.SysPermissionRemoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LenMotion
 * sa-token获取用户的权限
 */
@Component
@RequiredArgsConstructor
public class StpInterfaceServiceImpl implements StpInterface {

    private final SysPermissionRemoteService permissionService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return new ArrayList<>(permissionService.getMenuPermission(Long.parseLong(loginId.toString())));
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return new ArrayList<>(permissionService.getRolePermission(Long.parseLong(loginId.toString())));
    }

}
