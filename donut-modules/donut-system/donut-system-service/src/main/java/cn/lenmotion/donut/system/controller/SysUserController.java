package cn.lenmotion.donut.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.lenmotion.donut.core.annotation.OperationLog;
import cn.lenmotion.donut.core.entity.BaseImportResult;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.core.entity.PageResult;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.core.utils.PageUtils;
import cn.lenmotion.donut.framework.excel.ExcelExportClient;
import cn.lenmotion.donut.framework.excel.ExcelImportClient;
import cn.lenmotion.donut.system.entity.converter.UserConverter;
import cn.lenmotion.donut.system.entity.query.UserQuery;
import cn.lenmotion.donut.system.entity.request.SysUserRequest;
import cn.lenmotion.donut.system.entity.request.UserResetPwdRequest;
import cn.lenmotion.donut.system.entity.vo.UserResponseVO;
import cn.lenmotion.donut.system.entity.vo.imported.UserImportVO;
import cn.lenmotion.donut.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author LenMotion
 */
@Slf4j
@Tag(name = "用户管理")
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService userService;

    @SaCheckPermission("system:user:list")
    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public ResponseResult<PageResult<UserResponseVO>> list(UserQuery userQuery) {
        var page = userService.selectUserPage(userQuery);
        return ResponseResult.success(PageUtils.getPageResult(page));
    }

    @SaCheckPermission("system:user:export")
    @Operation(summary = "导出用户信息")
    @GetMapping("/export")
    public ResponseResult<Boolean> export(UserQuery userQuery) {
        userService.exportUserList(userQuery);
        return ResponseResult.success(true);
    }

    @SaCheckPermission("system:user:import")
    @Operation(summary = "导入用户信息模板")
    @GetMapping("/exportTemplate")
    public ResponseResult<String> exportTemplate() {
        var url = ExcelExportClient.builder()
                .fileName("用户导入模板")
                .ignoreLog()
                .setClass(UserImportVO.class)
                .doExport();
        return ResponseResult.success("导出成功", url);
    }

    @SaCheckPermission("system:user:import")
    @Operation(summary = "导入用户信息")
    @PostMapping("/importData")
    public ResponseResult<BaseImportResult> importData(@RequestBody MultipartFile file) throws IOException {
        List<UserImportVO> list = ExcelImportClient.builder().inputStream(file.getInputStream())
                .setClass(UserImportVO.class)
                .doImport();
        return ResponseResult.success(userService.importUser(UserConverter.INSTANCE.toPo(list)));
    }

    @Operation(summary = "获取用户详情")
    @GetMapping("{id}")
    public ResponseResult<UserResponseVO> get(@PathVariable("id") Long id) {
        return ResponseResult.success(userService.getUserDetail(id));
    }

    @SaCheckPermission("system:user:remove")
    @Operation(summary = "删除用户")
    @OperationLog("删除用户")
    @DeleteMapping("{id}")
    public ResponseResult<Boolean> removeUserById(@PathVariable("id") Long id) {
        return ResponseResult.success(userService.removeById(id));
    }

    @SaCheckPermission("system:user:save")
    @Operation(summary = "新增或修改用户")
    @OperationLog("新增或修改用户")
    @PostMapping
    public ResponseResult<Boolean> saveOrUpdate(@Validated @RequestBody SysUserRequest request) {
        return ResponseResult.success(userService.saveOrUpdate(request));
    }

    @SaCheckPermission("system:user:status")
    @Operation(summary = "更新用户状态")
    @OperationLog("更新用户状态")
    @PutMapping("status")
    public ResponseResult<Boolean> updateUserStatus(@Validated @RequestBody BaseUpdateStatus request) {
        return ResponseResult.success(userService.updateStatus(request));
    }

    @SaCheckPermission("system:user:password")
    @Operation(summary = "重置用户密码")
    @OperationLog("重置用户密码")
    @PutMapping("reset-password")
    public ResponseResult<Boolean> resetUserPassword(@Validated @RequestBody UserResetPwdRequest request) {
        return ResponseResult.success(userService.resetUserPassword(request));
    }

}
