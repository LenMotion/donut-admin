package cn.lenmotion.donut.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.lenmotion.donut.core.annotation.OperationLog;
import cn.lenmotion.donut.core.entity.PageResult;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.core.utils.PageUtils;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.system.service.SysDictTypeService;
import cn.lenmotion.donut.system.entity.po.SysDictType;
import cn.lenmotion.donut.system.entity.query.DictTypeQuery;
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
@Tag(name = "数据字典类型管理")
@RestController
@RequestMapping("/system/dictType")
@RequiredArgsConstructor
public class SysDictTypeController {

    private final SysDictTypeService dictTypeService;

    @SaCheckPermission("system:dict:list")
    @Operation(summary = "字典类型列表")
    @GetMapping("/list")
    public ResponseResult<PageResult<SysDictType>> list(DictTypeQuery query) {
        IPage<SysDictType> list = dictTypeService.selectDictTypePage(query);
        return ResponseResult.success(PageUtils.getPageResult(list));
    }

    @SaCheckPermission("system:dict:save")
    @Operation(summary = "新增或修改字典类型")
    @OperationLog("新增或修改字典类型")
    @PostMapping
    public ResponseResult<Boolean> saveOrUpdate(@Validated @RequestBody SysDictType dict) {
        return ResponseResult.success(dictTypeService.saveOrUpdate(dict));
    }

    @SaCheckPermission("system:dict:status")
    @Operation(summary = "更新字典类型状态")
    @OperationLog("更新字典类型状态")
    @PutMapping("status")
    public ResponseResult<Boolean> updateStatus(@Validated @RequestBody BaseUpdateStatus request) {
        return ResponseResult.success(dictTypeService.updateStatus(request));
    }

    @SaCheckPermission("system:dict:remove")
    @Operation(summary = "删除字典类型")
    @OperationLog("删除字典类型")
    @DeleteMapping("/{idList}")
    public ResponseResult<Boolean> remove(@PathVariable List<Long> idList) {
        return ResponseResult.success(dictTypeService.removeByIds(idList));
    }

}
