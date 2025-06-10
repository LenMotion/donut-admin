package cn.lenmotion.donut.system.controller;

import cn.lenmotion.donut.core.entity.PageResult;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.core.utils.PageUtils;
import cn.lenmotion.donut.system.entity.po.SysExportLog;
import cn.lenmotion.donut.system.entity.query.SysExportLogQuery;
import cn.lenmotion.donut.system.service.SysExportLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lenmotion
 */
@Tag(name = "导出记录")
@RestController
@RequestMapping("/system/exportLog")
@RequiredArgsConstructor
public class SysExportLogController {

    private final SysExportLogService sysExportLogService;

    @Operation(summary = "导出记录列表")
    @GetMapping("/list")
    public ResponseResult<PageResult<SysExportLog>> list(SysExportLogQuery query) {
        IPage<SysExportLog> list = sysExportLogService.selectPageList(query);
        return ResponseResult.success(PageUtils.getPageResult(list));
    }

}
