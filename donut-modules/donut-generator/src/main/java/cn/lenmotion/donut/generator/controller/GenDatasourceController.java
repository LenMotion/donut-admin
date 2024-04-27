package cn.lenmotion.donut.generator.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.lenmotion.donut.core.annotation.OperationLog;
import cn.lenmotion.donut.core.entity.PageResult;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.core.utils.PageUtils;
import cn.lenmotion.donut.generator.entity.po.GenDatasource;
import cn.lenmotion.donut.generator.entity.query.GenDatasourceQuery;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.generator.entity.request.GenDatasourceRequest;
import cn.lenmotion.donut.generator.service.GenDatasourceService;
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
@Tag(name = "数据源")
@RestController
@RequestMapping("/generator/genDatasource")
@RequiredArgsConstructor
public class GenDatasourceController {

    private final GenDatasourceService genDatasourceService;

    @SaCheckPermission("generator:genDatasource:list")
    @Operation(summary = "数据源列表")
    @GetMapping("/list")
    public ResponseResult<PageResult<GenDatasource>> list(GenDatasourceQuery query) {
        IPage<GenDatasource> list = genDatasourceService.selectPageList(query);
        return ResponseResult.success(PageUtils.getPageResult(list));
    }

    @SaCheckPermission("generator:code:list")
    @Operation(summary = "启用的数据源列表")
    @GetMapping("/enableList")
    public ResponseResult<List<GenDatasource>> enableList() {
        return ResponseResult.success(genDatasourceService.selectEnableList());
    }

    @SaCheckPermission("generator:genDatasource:checkConnection")
    @Operation(summary = "测试数据源连接")
    @OperationLog("测试数据源连接")
    @PostMapping("checkConnection/{id}")
    public ResponseResult<Boolean> checkConnection(@PathVariable Long id) {
        return ResponseResult.success(genDatasourceService.checkConnection(id));
    }

    @SaCheckPermission("generator:genDatasource:save")
    @Operation(summary = "新增或修改数据源")
    @OperationLog("新增或修改数据源")
    @PostMapping
    public ResponseResult<Boolean> saveOrUpdate(@Validated @RequestBody GenDatasourceRequest genDatasource) {
        return ResponseResult.success(genDatasourceService.saveOrUpdate(genDatasource));
    }

    @SaCheckPermission("generator:genDatasource:remove")
    @Operation(summary = "删除数据源")
    @OperationLog("删除数据源")
    @DeleteMapping("/{ids}")
    public ResponseResult<Boolean> remove(@PathVariable List<Long> ids) {
        return ResponseResult.success(genDatasourceService.removeByIds(ids));
    }

    @SaCheckPermission("generator:genDatasource:status")
    @Operation(summary = "更新数据源状态")
    @OperationLog("更新数据源状态")
    @PutMapping("status")
    public ResponseResult<Boolean> updateStatus(@Validated @RequestBody BaseUpdateStatus request) {
        return ResponseResult.success(genDatasourceService.updateStatus(request));
    }

}
