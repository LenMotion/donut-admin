package cn.lenmotion.donut.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.lenmotion.donut.core.annotation.OperationLog;
import cn.lenmotion.donut.core.entity.PageResult;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.core.utils.PageUtils;
import cn.lenmotion.donut.system.entity.po.SysPost;
import cn.lenmotion.donut.system.entity.query.PostQuery;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.system.service.SysPostService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LenMotion
 */
@Tag(name = "岗位管理")
@RestController
@RequestMapping("/system/post")
@RequiredArgsConstructor
public class SysPostController {
    private final SysPostService postService;

    @Operation(summary = "获取岗位列表")
    @SaCheckPermission("system:post:list")
    @GetMapping("/list")
    public ResponseResult<PageResult<SysPost>> list(PostQuery postQuery) {
        IPage<SysPost> list = postService.selectPostList(postQuery);
        return ResponseResult.success(PageUtils.getPageResult(list));
    }

    @Operation(summary = "根据组织获取岗位列表")
    @GetMapping("options")
    public ResponseResult<List<SysPost>> selectList(PostQuery postQuery) {
        return ResponseResult.success(postService.selectDeptPostList(postQuery));
    }

    @SaCheckPermission("system:post:save")
    @Operation(summary = "新增或修改岗位")
    @OperationLog("新增或修改岗位")
    @PostMapping
    public ResponseResult<Boolean> saveOrUpdate(@Validated @RequestBody SysPost post) {
        return ResponseResult.success(postService.saveOrUpdate(post));
    }

    @SaCheckPermission("system:post:status")
    @Operation(summary = "更新岗位状态")
    @OperationLog("更新岗位状态")
    @PutMapping("status")
    public ResponseResult<Boolean> updateStatus(@Validated @RequestBody BaseUpdateStatus request) {
        return ResponseResult.success(postService.updateStatus(request));
    }

    @SaCheckPermission("system:post:remove")
    @Operation(summary = "删除岗位")
    @OperationLog("删除岗位")
    @DeleteMapping("/{postIds}")
    public ResponseResult<Boolean> remove(@PathVariable List<Long> postIds) {
        return ResponseResult.success(postService.removeByIds(postIds));
    }
}
