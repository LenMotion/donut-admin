package cn.lenmotion.donut.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.lenmotion.donut.core.annotation.OperationLog;
import cn.lenmotion.donut.core.entity.PageResult;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.core.utils.PageUtils;
import cn.lenmotion.donut.system.entity.po.SysFileStorage;
import cn.lenmotion.donut.system.entity.query.FileStorageQuery;
import cn.lenmotion.donut.system.service.SysFileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lenmotion
 */
@RestController
@Tag(name = "文件记录管理", description = "文件记录管理")
@RequestMapping("/system/fileStorage")
@AllArgsConstructor
public class SysFileStorageController {

    private final SysFileStorageService sysFileStorageService;

    @SaCheckPermission("system:fileStorage:list")
    @OperationLog("文件记录列表")
    @GetMapping("/list")
    public ResponseResult<PageResult<SysFileStorage>> list(FileStorageQuery fileStorageQuery) {
        var page = sysFileStorageService.selectPage(fileStorageQuery);
        return ResponseResult.success(PageUtils.getPageResult(page));
    }

}
