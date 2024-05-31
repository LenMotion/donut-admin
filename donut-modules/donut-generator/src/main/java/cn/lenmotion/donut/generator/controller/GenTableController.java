package cn.lenmotion.donut.generator.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.lenmotion.donut.core.annotation.OperationLog;
import cn.lenmotion.donut.core.entity.PageResult;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.core.utils.PageUtils;
import cn.lenmotion.donut.generator.entity.po.GenTable;
import cn.lenmotion.donut.generator.entity.po.GenTableColumn;
import cn.lenmotion.donut.generator.entity.query.ColumnsQuery;
import cn.lenmotion.donut.generator.entity.query.GenTableQuery;
import cn.lenmotion.donut.generator.entity.request.GenTableRequest;
import cn.lenmotion.donut.generator.service.GenTableColumnService;
import cn.lenmotion.donut.generator.service.GenTableService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lenmotion
 */
@Tag(name = "生成管理")
@RestController
@RequestMapping("/gen/code")
@RequiredArgsConstructor
public class GenTableController {

    private final GenTableService genTableService;
    private final GenTableColumnService genTableColumnService;

    @SaCheckPermission("gen:code:list")
    @GetMapping("list")
    @Operation(summary = "查询生成列表")
    public ResponseResult<PageResult<GenTable>> list(GenTableQuery query) {
        IPage<GenTable> list = genTableService.selectPageList(query);
        return ResponseResult.success(PageUtils.getPageResult(list));
    }

    @SaCheckPermission("gen:code:list")
    @GetMapping("/preview")
    @Operation(summary = "生成代码")
    public ResponseResult<String> genCode(@RequestParam Long tableId,
                                          @RequestParam String fileName) {
        return ResponseResult.success("success", genTableService.genCode(fileName, tableId));
    }

    @SaCheckPermission("gen:code:remove")
    @DeleteMapping("/{id}")
    @Operation(summary = "删除生成数据")
    @OperationLog("删除生成数据")
    public ResponseResult<Boolean> remove(@PathVariable Long id) {
        return ResponseResult.success(genTableService.removeById(id));
    }

    @SaCheckPermission("gen:code:save")
    @Operation(summary = "查询数据库列表")
    @GetMapping("tableList")
    public ResponseResult<List<GenTable>> tableList(@RequestParam(required = false) Long datasourceId) {
        return ResponseResult.success(genTableService.tables(datasourceId));
    }

    @SaCheckPermission("gen:code:save")
    @Operation(summary = "查询表字段列表")
    @GetMapping("tableColumnsByTable/{tableId}")
    public ResponseResult<List<GenTableColumn>> tableColumnsByTable(@PathVariable Long tableId) {
        return ResponseResult.success(genTableColumnService.tableColumns(tableId));
    }

    @SaCheckPermission("gen:code:save")
    @Operation(summary = "查询表字段列表")
    @GetMapping("tableColumns")
    public ResponseResult<List<GenTableColumn>> tableColumns(ColumnsQuery query) {
        return ResponseResult.success(genTableColumnService.tableColumns(query));
    }

    @Operation(summary = "保存或更新表信息")
    @OperationLog(value = "保存或更新表信息")
    @SaCheckPermission("gen:code:save")
    @PostMapping
    public ResponseResult<Boolean> saveOrUpdate(@RequestBody GenTableRequest request) {
        return ResponseResult.success(genTableService.saveOrUpdate(request));
    }

}
