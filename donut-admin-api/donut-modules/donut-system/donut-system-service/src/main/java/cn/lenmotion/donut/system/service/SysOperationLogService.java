package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.system.entity.po.SysOperationLog;
import cn.lenmotion.donut.system.entity.query.OperationLogQuery;
import cn.lenmotion.donut.system.entity.vo.OperationLogVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author lenmotion
 */
public interface SysOperationLogService extends IService<SysOperationLog> {

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    IPage<OperationLogVO> selectPage(OperationLogQuery query);

}
