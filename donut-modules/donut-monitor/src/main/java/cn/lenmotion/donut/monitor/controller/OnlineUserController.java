package cn.lenmotion.donut.monitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.lenmotion.donut.core.entity.BasePageQuery;
import cn.lenmotion.donut.core.entity.PageResult;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.monitor.entity.OnlineUserVO;
import cn.lenmotion.donut.monitor.service.OnlineUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author LenMotion
 */
@Slf4j
@Tag(name = "在线用户监控")
@RestController
@RequestMapping("/monitor/onlineUser")
@RequiredArgsConstructor
public class OnlineUserController {

    private final OnlineUserService onlineUserService;

    @GetMapping("list")
    @SaCheckPermission("monitor:onlineUser:list")
    @Operation(summary = "在线用户列表")
    public ResponseResult<PageResult<OnlineUserVO>> onlineUserList(BasePageQuery query) {
        return ResponseResult.success(onlineUserService.getOnlineUserList(query));
    }

    @DeleteMapping("kickOut/{id}")
    @SaCheckPermission("monitor:onlineUser:kickOut")
    @Operation(summary = "踢出用户")
    public ResponseResult<Boolean> kickOutUser(@PathVariable("id") Long id) {
        return ResponseResult.success(onlineUserService.kickOutUser(id));
    }

}
