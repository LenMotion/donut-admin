package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.core.service.DonutService;
import cn.lenmotion.donut.system.entity.po.SysExportLog;
import cn.lenmotion.donut.system.entity.query.SysExportLogQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author lenmotion
 */
public interface SysExportLogService extends DonutService<SysExportLog> {

    /**
     * 分页查询
     * @param query 查询条件
     * @return      分页数据
     */
    IPage<SysExportLog> selectPageList(SysExportLogQuery query);

}
