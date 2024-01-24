package cn.lenmotion.donut.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.lenmotion.donut.core.annotation.OperationLog;
import cn.lenmotion.donut.core.entity.PageResult;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.core.utils.PageUtils;
import cn.lenmotion.donut.system.entity.po.SysConfig;
import cn.lenmotion.donut.system.entity.query.ConfigQuery;
import cn.lenmotion.donut.system.service.SysConfigService;
import com.alibaba.fastjson.JSONObject;
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
@Tag(name = "系统配置")
@RestController
@RequestMapping("/system/config")
@RequiredArgsConstructor
public class SysConfigController {

    private final SysConfigService configService;

    @Operation(summary = "获取系统配置")
    @GetMapping("systemConfig")
    @SaCheckPermission("system:config:list")
    public ResponseResult<JSONObject> systemConfig() {
        return ResponseResult.success(configService.selectSystemConfigList());
    }

    @Operation(summary = "获取参数配置列表")
    @SaCheckPermission("system:config:list")
    @GetMapping("/list")
    public ResponseResult<PageResult<SysConfig>> list(ConfigQuery query) {
        IPage<SysConfig> list = configService.selectConfigList(query);
        return ResponseResult.success(PageUtils.getPageResult(list));
    }

    @Operation(summary = "获取参数配置")
    @GetMapping("/{key}")
    public ResponseResult<String> getConfigKey(@PathVariable String key) {
        return ResponseResult.success("获取成功", configService.getConfigByKey(key));
    }

    @Operation(summary = "修改系统配置")
    @OperationLog("修改系统配置")
    @PutMapping("systemConfig")
    @SaCheckPermission("system:config:save")
    public ResponseResult<Boolean> updateSystemConfig(@RequestBody JSONObject data) {
        return ResponseResult.success(configService.updateSystemConfig(data));
    }

    @Operation(summary = "新增参数配置")
    @OperationLog("新增参数配置")
    @SaCheckPermission("system:config:save")
    @PostMapping
    public ResponseResult<Boolean> saveOrUpdate(@Validated @RequestBody SysConfig config) {
        return ResponseResult.success(configService.saveOrUpdate(config));
    }

    @Operation(summary = "删除参数配置")
    @OperationLog("删除参数配置")
    @SaCheckPermission("system:config:remove")
    @DeleteMapping("/{configIds}")
    public ResponseResult<Boolean> remove(@PathVariable List<Long> configIds) {
        return ResponseResult.success(configService.removeByIds(configIds));
    }

}
