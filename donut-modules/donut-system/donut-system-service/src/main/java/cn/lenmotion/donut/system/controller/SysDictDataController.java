package cn.lenmotion.donut.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.lenmotion.donut.core.annotation.OperationLog;
import cn.lenmotion.donut.core.entity.PageResult;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.core.utils.PageUtils;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.system.service.SysDictDataService;
import cn.lenmotion.donut.system.entity.po.SysDictData;
import cn.lenmotion.donut.system.entity.query.DictDataQuery;
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
@Tag(name = "系统字典数据")
@RestController
@RequestMapping("/system/dict/data")
@RequiredArgsConstructor
public class SysDictDataController {

    private final SysDictDataService dictDataService;

    @SaCheckPermission("system:dict:list")
    @GetMapping("/list")
    @Operation(summary = "分页查询")
    public ResponseResult<PageResult<SysDictData>> list(DictDataQuery query) {
        IPage<SysDictData> list = dictDataService.selectDictDataList(query);
        return ResponseResult.success(PageUtils.getPageResult(list));
    }

    @SaCheckPermission("system:dict:save")
    @PostMapping
    @Operation(summary = "新增或修改字典数据")
    @OperationLog("新增或修改字典数据")
    public ResponseResult<Boolean> saveOrUpdate(@Validated @RequestBody SysDictData dict) {
        return ResponseResult.success(dictDataService.saveOrUpdate(dict));
    }

    @SaCheckPermission("system:dict:status")
    @Operation(summary = "更新字典数据状态")
    @OperationLog("更新字典数据状态")
    @PutMapping("status")
    public ResponseResult<Boolean> updateStatus(@Validated @RequestBody BaseUpdateStatus request) {
        return ResponseResult.success(dictDataService.updateStatus(request));
    }

    @SaCheckPermission("system:dict:remove")
    @DeleteMapping("/{idList}")
    @Operation(summary = "删除字典数据")
    @OperationLog("删除字典数据")
    public ResponseResult<Boolean> remove(@PathVariable List<Long> idList) {
        return ResponseResult.success(dictDataService.removeByIds(idList));
    }

}
