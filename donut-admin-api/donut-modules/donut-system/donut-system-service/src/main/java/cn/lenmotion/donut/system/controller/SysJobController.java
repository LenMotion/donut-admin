package cn.lenmotion.donut.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.lenmotion.donut.core.annotation.OperationLog;
import cn.lenmotion.donut.core.entity.PageResult;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.core.utils.PageUtils;
import cn.lenmotion.donut.system.entity.po.SysJob;
import cn.lenmotion.donut.system.entity.po.SysJobLog;
import cn.lenmotion.donut.system.entity.query.JobLogQuery;
import cn.lenmotion.donut.system.entity.query.JobQuery;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.system.service.SysJobService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 定时任务Controller
 *
 * @author LenMotion
 */
@Tag(name = "定时任务")
@RestController
@RequestMapping("/system/job")
@RequiredArgsConstructor
public class SysJobController {
    private final SysJobService sysJobService;

    @SaCheckPermission("system:job:list")
    @GetMapping("/list")
    @Operation(summary = "定时任务列表")
    public ResponseResult<PageResult<SysJob>> list(JobQuery baseQuery) {
        IPage<SysJob> page = sysJobService.selectPageList(baseQuery);
        return ResponseResult.success(PageUtils.getPageResult(page));
    }

    @SaCheckPermission("system:job:save")
    @Operation(summary = "保存定时任务")
    @OperationLog("保存定时任务")
    @PostMapping
    public ResponseResult<Boolean> saveOrUpdate(@RequestBody @Valid SysJob sysJob) {
        return ResponseResult.success(sysJobService.saveOrUpdate(sysJob));
    }

    @SaCheckPermission("system:job:status")
    @Operation(summary = "修改定时任务状态")
    @OperationLog("修改定时任务状态")
    @PutMapping("status")
    public ResponseResult<Boolean> updateStatus(@RequestBody @Valid BaseUpdateStatus  request) {
        return ResponseResult.success(sysJobService.updateStatus(request));
    }

    @SaCheckPermission("system:job:exec")
    @Operation(summary = "立即执行定时任务")
    @OperationLog("立即执行定时任务")
    @PutMapping("exec/{id}")
    public ResponseResult<Boolean> execNot(@PathVariable Long id) {
        sysJobService.execNow(id);
        return ResponseResult.success();
    }

    @SaCheckPermission("system:job:log")
    @Operation(summary = "定时任务执行日志")
    @GetMapping("log/list")
    public ResponseResult<PageResult<SysJobLog>> logList(JobLogQuery baseQuery) {
        var page = sysJobService.selectLogPageList(baseQuery);
        return ResponseResult.success(PageUtils.getPageResult(page));
    }

    @SaCheckPermission("system:job:remove")
    @Operation(summary = "删除定时任务")
    @OperationLog("删除定时任务")
    @DeleteMapping("/{ids}")
    public ResponseResult<Boolean> remove(@PathVariable Long[] ids) {
        return ResponseResult.success((sysJobService.removeByIds(Arrays.asList(ids))));
    }

}
