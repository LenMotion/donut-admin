package cn.lenmotion.donut.generator.service;

import cn.lenmotion.donut.generator.entity.po.GenTable;
import cn.lenmotion.donut.generator.entity.query.GenTableQuery;
import cn.lenmotion.donut.generator.entity.request.GenTableRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author lenmotion
 */
public interface GenTableService extends IService<GenTable> {

    /**
     * 分页查询
     * @param query 查询条件
     * @return      分页数据
     */
    IPage<GenTable> selectPageList(GenTableQuery query);

    /**
     * 查询所有的表
     * @return
     */
    List<GenTable> tables(Long datasourceId);

    /**
     * 保存或更新表
     * @param request
     * @return
     */
    Boolean saveOrUpdate(GenTableRequest request);

    String genCode(String fileName, Long tableId);

}
