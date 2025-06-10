package cn.lenmotion.donut.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.lenmotion.donut.core.annotation.OperationLog;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.core.entity.PageResult;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.core.utils.AssertUtils;
import cn.lenmotion.donut.core.utils.PageUtils;
import cn.lenmotion.donut.system.entity.po.SysTenant;
import cn.lenmotion.donut.system.entity.query.SysTenantQuery;
import cn.lenmotion.donut.system.entity.request.SysTenantRequest;
import cn.lenmotion.donut.system.entity.vo.RoleMenuIdVO;
import cn.lenmotion.donut.system.entity.vo.TenantBaseVO;
import cn.lenmotion.donut.system.service.SysTenantService;
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
@Tag(name = "租户表")
@RestController
@RequestMapping("/system/sysTenant")
@RequiredArgsConstructor
public class SysTenantController {

    private final SysTenantService sysTenantService;

    @SaCheckPermission("system:sysTenant:list")
    @Operation(summary = "租户表列表")
    @GetMapping("/list")
    public ResponseResult<PageResult<SysTenant>> list(SysTenantQuery query) {
        IPage<SysTenant> list = sysTenantService.selectPageList(query);
        return ResponseResult.success(PageUtils.getPageResult(list));
    }

    @SaIgnore
    @Operation(summary = "根据id查询租户名称")
    @GetMapping("/baseInfo/{ids}")
    public ResponseResult<List<TenantBaseVO>> baseInfoByIds(@PathVariable List<Long> ids) {
        return ResponseResult.success(sysTenantService.baseInfoByIds(ids));
    }

    @SaCheckPermission("system:sysTenant:save")
    @Operation(summary = "新增或修改租户表")
    @OperationLog("新增或修改租户表")
    @PostMapping
    public ResponseResult<Boolean> saveOrUpdate(@Validated @RequestBody SysTenantRequest sysTenant) {
        return ResponseResult.success(sysTenantService.saveOrUpdate(sysTenant));
    }

    @SaCheckPermission("system:sysTenant:save")
    @Operation(summary = "租户超管菜单列表")
    @GetMapping("/menuIdList/{tenantId}")
    public ResponseResult<RoleMenuIdVO> list(@PathVariable Long tenantId) {
        return ResponseResult.success(sysTenantService.listMenuIdByTenantId(tenantId));
    }

    @SaCheckPermission("system:sysTenant:remove")
    @Operation(summary = "删除租户表")
    @OperationLog("删除租户表")
    @DeleteMapping("/{ids}")
    public ResponseResult<Boolean> remove(@PathVariable List<Long> ids) {
        for (Long id : ids) {
            AssertUtils.isFalse(BaseConstants.SUPER_ID.equals(id), "超级租户不可操作");
        }
        return ResponseResult.success(sysTenantService.removeByIds(ids));
    }

    @SaCheckPermission("system:sysTenant:status")
    @Operation(summary = "更新租户表状态")
    @OperationLog("更新租户表状态")
    @PutMapping("status")
    public ResponseResult<Boolean> updateStatus(@Validated @RequestBody BaseUpdateStatus request) {
        AssertUtils.isFalse(BaseConstants.SUPER_ID.equals(request.getId()), "超级租户不可操作");
        return ResponseResult.success(sysTenantService.updateStatus(request));
    }

}
