package cn.lenmotion.donut.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.lenmotion.donut.core.annotation.OperationLog;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.entity.LoginInfo;
import cn.lenmotion.donut.core.entity.PageResult;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.core.utils.PageUtils;
import cn.lenmotion.donut.system.entity.po.SysRole;
import cn.lenmotion.donut.system.entity.query.RoleQuery;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.system.entity.query.RoleUserQuery;
import cn.lenmotion.donut.system.entity.request.RoleBindUserRequest;
import cn.lenmotion.donut.system.entity.request.RoleUnbindUserRequest;
import cn.lenmotion.donut.system.entity.request.SysRoleRequest;
import cn.lenmotion.donut.system.entity.vo.RoleMenuIdVO;
import cn.lenmotion.donut.system.entity.vo.RoleUserVO;
import cn.lenmotion.donut.system.service.SysRoleMenuService;
import cn.lenmotion.donut.system.service.SysRoleService;
import cn.lenmotion.donut.system.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lenmotion
 */
@Tag(name = "角色管理")
@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;
    private final SysRoleMenuService roleMenuService;
    private final SysUserRoleService userRoleService;

    @SaCheckPermission("system:role:list")
    @Operation(summary = "角色列表")
    @GetMapping("/list")
    public ResponseResult<PageResult<SysRole>> list(RoleQuery roleQuery) {
        IPage<SysRole> list = sysRoleService.selectRolePage(roleQuery);
        return ResponseResult.success(PageUtils.getPageResult(list));
    }

    @GetMapping("userList")
    @Operation(summary = "角色绑定的用户")
    @SaCheckPermission("system:role:user:list")
    public ResponseResult<PageResult<RoleUserVO>> userList(@Validated RoleUserQuery query) {
        return ResponseResult.success(PageUtils.getPageResult(userRoleService.selectUserList(query)));
    }

    @GetMapping("unbindUserList")
    @Operation(summary = "角色绑定的用户")
    @SaCheckPermission("system:role:user:bind")
    public ResponseResult<PageResult<RoleUserVO>> unbindUserList(@Validated RoleUserQuery query) {
        return ResponseResult.success(PageUtils.getPageResult(userRoleService.unbindUserList(query)));
    }

    @PostMapping("bind")
    @Operation(summary = "角色绑定用户")
    @SaCheckPermission("system:role:user:bind")
    public ResponseResult<Boolean> bindUser(@Validated @RequestBody RoleBindUserRequest request) {
        return ResponseResult.success(userRoleService.bindUser(request));
    }

    @PostMapping("unbind/{id}")
    @Operation(summary = "角色解绑用户")
    @SaCheckPermission("system:role:user:unbind")
    public ResponseResult<Boolean> unbind(@PathVariable Long id) {
        return ResponseResult.success(userRoleService.unbind(userRoleService.getById(id)));
    }

    @GetMapping("options")
    @Operation(summary = "角色选择框")
    public ResponseResult<List<SysRole>> options() {
        var loginInfo = (LoginInfo) StpUtil.getSession().get(BaseConstants.SESSION_LOGIN_INFO);
        return ResponseResult.success(sysRoleService.selectRolesByLoginInfo(loginInfo));
    }

    @SaCheckPermission("system:role:save")
    @Operation(summary = "新增或修改角色")
    @OperationLog("新增或修改角色")
    @PostMapping
    public ResponseResult<Boolean> saveOrUpdate(@Validated @RequestBody SysRoleRequest role) {
        return ResponseResult.success(sysRoleService.saveOrUpdate(role));
    }

    @SaCheckPermission("system:role:save")
    @Operation(summary = "角色菜单列表")
    @GetMapping("/menuIdList/{roleId}")
    public ResponseResult<RoleMenuIdVO> list(@PathVariable Long roleId) {
        return ResponseResult.success(roleMenuService.getMenuIdListByRoleId(roleId));
    }

    @SaCheckPermission("system:role:remove")
    @Operation(summary = "删除角色")
    @OperationLog("删除角色")
    @DeleteMapping("/{roleIds}")
    public ResponseResult<Boolean> remove(@PathVariable List<Long> roleIds) {
        return ResponseResult.success(sysRoleService.removeByIds(roleIds));
    }

    @SaCheckPermission("system:role:status")
    @Operation(summary = "更新角色状态")
    @OperationLog("更新角色状态")
    @PutMapping("status")
    public ResponseResult<Boolean> updateStatus(@Validated @RequestBody BaseUpdateStatus request) {
        return ResponseResult.success(sysRoleService.updateStatus(request));
    }

}
