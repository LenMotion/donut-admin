package cn.lenmotion.donut.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import cn.lenmotion.donut.core.annotation.OperationLog;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.core.enums.BaseStatusEnum;
import cn.lenmotion.donut.system.entity.po.SysDept;
import cn.lenmotion.donut.system.entity.query.DeptQuery;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.system.entity.vo.DeptTreeResponse;
import cn.lenmotion.donut.system.service.SysDeptService;
import com.fhs.core.trans.anno.IgnoreTrans;
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
 * @author LenMotion
 */
@Tag(name = "部门管理")
@RestController
@RequestMapping("/system/dept")
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptService deptService;

    @Operation(summary = "获取部门树")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = DeptTreeResponse.class)))
    @SaCheckPermission("system:dept:list")
    @GetMapping("/tree")
    @IgnoreTrans
    public ResponseResult<List<Tree<Long>>> deptTree(DeptQuery query) {
        return ResponseResult.success(deptService.getDeptTree(query));
    }

    @Operation(summary = "获取已启用部门树")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = DeptTreeResponse.class)))
    @GetMapping("/enableTree")
    @IgnoreTrans
    public ResponseResult<List<Tree<Long>>> enableDeptTree() {
        DeptQuery query = new DeptQuery();
        query.setStatus(BaseStatusEnum.ENABLED.getCode());
        return ResponseResult.success(deptService.getDeptTree(query));
    }

    @SaCheckPermission("system:dept:save")
    @Operation(summary = "新增或修改部门")
    @OperationLog("新增或修改部门")
    @PostMapping
    public ResponseResult<Long> saveOrUpdate(@Validated @RequestBody SysDept dept) {
        return ResponseResult.success(deptService.saveOrUpdateRequest(dept));
    }

    @SaCheckPermission("system:dept:status")
    @Operation(summary = "更新状态")
    @OperationLog("更新部门状态")
    @PutMapping("status")
    public ResponseResult<Boolean> updateStatus(@Validated @RequestBody BaseUpdateStatus request) {
        return ResponseResult.success(deptService.updateStatus(request));
    }

    @SaCheckPermission("system:dept:remove")
    @Operation(summary = "删除部门")
    @OperationLog("删除部门")
    @DeleteMapping("/{deptIds}")
    public ResponseResult<Boolean> remove(@PathVariable List<Long> deptIds) {
        return ResponseResult.success(deptService.removeByIds(deptIds));
    }

}
