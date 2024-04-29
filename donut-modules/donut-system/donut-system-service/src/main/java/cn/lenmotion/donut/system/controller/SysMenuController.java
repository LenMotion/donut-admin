package cn.lenmotion.donut.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.lenmotion.donut.core.annotation.OperationLog;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.system.entity.po.SysMenu;
import cn.lenmotion.donut.system.entity.vo.MenuTreeResponse;
import cn.lenmotion.donut.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单信息
 *
 * @author lenmotion
 */
@Tag(name = "菜单管理")
@RestController
@RequestMapping("/system/menu")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService menuService;

    @SaCheckPermission("system:menu:list")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MenuTreeResponse.class)))
    @Operation(summary = "菜单结构树")
    @GetMapping("tree")
    public ResponseResult<?> treeList() {
        return ResponseResult.success(menuService.getMenuTree());
    }

    @SaCheckPermission("system:menu:save")
    @Operation(summary = "新增或修改菜单")
    @OperationLog("新增或修改菜单")
    @PostMapping
    public ResponseResult<Long> saveOrUpdate(@Validated @RequestBody SysMenu menu) {
        return ResponseResult.success(menuService.saveOrUpdateMenu(menu));
    }

    @SaCheckPermission("system:menu:remove")
    @Operation(summary = "删除菜单")
    @OperationLog("删除菜单")
    @DeleteMapping("/{menuIds}")
    public ResponseResult<Boolean> remove(@PathVariable List<Long> menuIds) {
        return ResponseResult.success(menuService.removeByIds(menuIds));
    }

    @SaCheckPermission("system:menu:status")
    @Operation(summary = "更新菜单状态")
    @OperationLog("更新菜单状态")
    @PutMapping("status")
    public ResponseResult<Boolean> updateStatus(@Validated @RequestBody BaseUpdateStatus request) {
        return ResponseResult.success(menuService.updateStatus(request));
    }

    @SaCheckPermission("system:user:detail")
    @GetMapping("user/{userId}")
    @Operation(summary = "获取用户的菜单")
    public ResponseResult<List<SysMenu>> getMenuListByUserId(@PathVariable Long userId) {
        return ResponseResult.success(menuService.getMenuListByUserId(userId));
    }

}