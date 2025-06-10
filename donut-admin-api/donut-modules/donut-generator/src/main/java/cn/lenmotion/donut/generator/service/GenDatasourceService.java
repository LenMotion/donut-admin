package cn.lenmotion.donut.generator.service;

import cn.lenmotion.donut.core.service.DonutService;
import cn.lenmotion.donut.generator.entity.po.GenDatasource;
import cn.lenmotion.donut.generator.entity.query.GenDatasourceQuery;
import cn.lenmotion.donut.generator.entity.request.GenDatasourceRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author lenmotion
 */
public interface GenDatasourceService extends DonutService<GenDatasource> {

    /**
     * 分页查询
     * @param query 查询条件
     * @return      分页数据
     */
    IPage<GenDatasource> selectPageList(GenDatasourceQuery query);

    /**
     * 新增或修改
     * @param request
     * @return
     */
    Boolean saveOrUpdate(GenDatasourceRequest request);

    /**
     * 获取启用的数据源列表
     * @return
     */
    List<GenDatasource> selectEnableList();

    /**
     * 校验链接
     * @param id
     * @return
     */
    Boolean checkConnection(Long id);

    /**
     * 根据id生成连接
     * @param id
     * @return
     */
    Connection getConnectionById(Long id) throws SQLException;
}
