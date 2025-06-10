package cn.lenmotion.donut.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.lenmotion.donut.core.entity.PageResult;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.core.utils.PageUtils;
import cn.lenmotion.donut.system.entity.po.SysLoginLog;
import cn.lenmotion.donut.system.entity.query.LoginLogQuery;
import cn.lenmotion.donut.system.entity.query.OperationLogQuery;
import cn.lenmotion.donut.system.entity.vo.OperationLogVO;
import cn.lenmotion.donut.system.service.SysLoginLogService;
import cn.lenmotion.donut.system.service.SysOperationLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lenmotion
 */
@Tag(name = "系统日志")
@RestController
@RequestMapping("/system/log")
@RequiredArgsConstructor
public class SysLogController {

    private final SysLoginLogService loginLogService;
    private final SysOperationLogService operationLogService;

    @Operation(summary = "登录日志")
    @SaCheckPermission(value = {"system:loginLog:list", "system:user:detail"}, mode = SaMode.OR)
    @GetMapping("/loginLogList")
    public ResponseResult<PageResult<SysLoginLog>> logList(LoginLogQuery query) {
        var page = loginLogService.selectPage(query);
        return ResponseResult.success(PageUtils.getPageResult(page));
    }

    @Operation(summary = "当前用户登录日志")
    @GetMapping("/userLoginLogList")
    public ResponseResult<PageResult<SysLoginLog>> userLogList(LoginLogQuery query) {
        query.setUserId(StpUtil.getLoginIdAsLong());
        var page = loginLogService.selectPage(query);
        return ResponseResult.success(PageUtils.getPageResult(page));
    }

    @Operation(summary = "登录日志")
    @SaCheckPermission(value = "system:loginLog:export")
    @GetMapping("/exportLoginLog")
    public ResponseResult<Boolean> exportLoginLog(LoginLogQuery query) {
        loginLogService.exportLog(query);
        return ResponseResult.success(true);
    }

    @Operation(summary = "操作日志")
    @SaCheckPermission(value = {"system:operationLog:list", "system:user:detail"}, mode = SaMode.OR)
    @GetMapping("/operationLogList")
    public ResponseResult<PageResult<OperationLogVO>> operationLogList(OperationLogQuery query) {
        var page = operationLogService.selectPage(query);
        return ResponseResult.success(PageUtils.getPageResult(page));
    }

    @Operation(summary = "当前登录用户的操作日志")
    @GetMapping("/userOperationLogList")
    public ResponseResult<PageResult<OperationLogVO>> userOperationLogList(OperationLogQuery query) {
        query.setUserId(StpUtil.getLoginIdAsLong());
        var page = operationLogService.selectPage(query);
        return ResponseResult.success(PageUtils.getPageResult(page));
    }

}
