package cn.lenmotion.donut.generator.service;

import cn.lenmotion.donut.generator.entity.po.GenTable;
import cn.lenmotion.donut.generator.entity.po.GenTableColumn;
import cn.lenmotion.donut.generator.entity.query.ColumnsQuery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author lenmotion
 */
public interface GenTableColumnService extends IService<GenTableColumn> {

    /**
     * 查询表的字段
     * @param query
     * @return
     */
    List<GenTableColumn> tableColumns(ColumnsQuery query);

    /**
     * 查询表的字段
     * @param tableId
     * @return
     */
    List<GenTableColumn> tableColumns(Long tableId);

    /**
     * 批量新增，并且删除原有的
     * @param genTableId
     * @param list
     * @return
     */
    Boolean saveBatch(Long genTableId, List<GenTableColumn> list);

    /**
     * 需要生成的字段
     * @param genTable
     * @return
     */
    List<GenTableColumn> genColumn(GenTable genTable);

    /**
     * 生成查询字段
     * @param genTable
     * @return
     */
    List<GenTableColumn> genSearchColumn(GenTable genTable);

    /**
     * 生成编辑字段
     * @param genTable
     * @return
     */
    List<GenTableColumn> genEditColumn(GenTable genTable);

    /**
     * 生成表格字段
     * @param genTable
     * @return
     */
    List<GenTableColumn> genTableColumn(GenTable genTable);

    /**
     * 获取id字段
     * @param genTable
     * @return
     */
    List<GenTableColumn> genIdColumn(GenTable genTable);

}
