package cn.lenmotion.donut.system.remote.impl;

import cn.lenmotion.donut.system.entity.po.SysUser;
import cn.lenmotion.donut.system.remote.SysUserRemoteService;
import cn.lenmotion.donut.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class SysUserRemoteServiceImpl implements SysUserRemoteService {

    private final SysUserService userService;

    @Override
    public SysUser getByUsername(String username) {
        return userService.getByUsername(username);
    }

    @Override
    public void updateUser(SysUser user) {
        userService.updateById(user);
    }

    @Override
    public SysUser getById(Long userId) {
        return userService.getById(userId);
    }
}
