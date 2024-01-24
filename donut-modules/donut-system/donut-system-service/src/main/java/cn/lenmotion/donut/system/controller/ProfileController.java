package cn.lenmotion.donut.system.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.StrUtil;
import cn.lenmotion.donut.core.annotation.OperationLog;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.system.entity.covert.UserConverter;
import cn.lenmotion.donut.system.entity.po.SysUser;
import cn.lenmotion.donut.system.entity.request.UserAvatarRequest;
import cn.lenmotion.donut.system.entity.request.UserProfileRequest;
import cn.lenmotion.donut.system.entity.request.UserPwdRequest;
import cn.lenmotion.donut.system.entity.vo.UserInfoVO;
import cn.lenmotion.donut.system.service.SysMenuService;
import cn.lenmotion.donut.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lenmotion
 */
@Tag(name = "用户授权信息")
@RestController
@AllArgsConstructor
@RequestMapping("profile")
public class ProfileController {

    private final SysUserService userService;
    private final SysMenuService menuService;

    @Operation(summary = "获取用户信息")
    @GetMapping("userInfo")
    public ResponseResult<UserInfoVO> getInfo() {
        //获取当前登录用户的ID
        long userId = StpUtil.getLoginIdAsLong();
        //根据ID获取用户信息
        SysUser user = userService.getById(userId);
        //将用户信息转换为VO
        UserInfoVO userInfoVO = UserConverter.INSTANCE.toInfoVO(user);
        // 快速导航菜单
        userInfoVO.setQuickNav(StrUtil.isNotBlank(user.getQuickNav()) ? StrUtil.split(user.getQuickNav(), ",") : new ArrayList<>());
        if (CollUtil.isNotEmpty(userInfoVO.getQuickNav())) {
            userInfoVO.setQuickNavMenus(menuService.listByIds(userInfoVO.getQuickNav()));
        }
        //获取角色列表
        userInfoVO.setRoles(StpUtil.getRoleList());
        //获取权限列表
        userInfoVO.setPerms(StpUtil.getPermissionList());
        //返回用户信息
        return ResponseResult.success(userInfoVO);
    }

    @Operation(summary = "获取路由信息")
    @GetMapping("routers")
    public ResponseResult<List<Tree<Long>>> getRouters() {
        Long userId = StpUtil.getLoginIdAsLong();
        return ResponseResult.success(menuService.getRouteListByUserId(userId));
    }

    @PutMapping("info")
    @Operation(summary = "更新用户信息")
    @OperationLog(value = "更新用户信息")
    public ResponseResult<Boolean> updateUserInfo(@RequestBody @Validated UserProfileRequest userProfileRequest) {
        return ResponseResult.success(userService.updateProfile(userProfileRequest));
    }

    @PutMapping("avatar")
    @Operation(summary = "更新用户头像")
    @OperationLog(value = "更新用户头像")
    public ResponseResult<Boolean> updateAvatar(@RequestBody @Validated UserAvatarRequest userAvatarRequest) {
        return ResponseResult.success(userService.updateAvatar(userAvatarRequest));
    }

    @PutMapping("password")
    @Operation(summary = "更新用户密码")
    @OperationLog(value = "更新用户密码")
    public ResponseResult<Boolean> updateUserPassword(@RequestBody @Validated UserPwdRequest userPwdRequest) {
        return ResponseResult.success(userService.updatePassword(userPwdRequest));
    }

}
