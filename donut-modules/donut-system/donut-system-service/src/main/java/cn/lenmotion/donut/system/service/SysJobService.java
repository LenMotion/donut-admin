package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.core.service.DonutService;
import cn.lenmotion.donut.system.entity.po.SysJob;
import cn.lenmotion.donut.system.entity.po.SysJobLog;
import cn.lenmotion.donut.system.entity.query.JobLogQuery;
import cn.lenmotion.donut.system.entity.query.JobQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author LenMotion
 */
public interface SysJobService extends DonutService<SysJob> {

    /**
     * 查询任务
     * @param baseQuery
     * @return
     */
    IPage<SysJob> selectPageList(JobQuery baseQuery);

    /**
     * 立即执行任务
     * @param id
     */
    void execNow(Long id);

    /**
     * 分页查询日志
     * @param baseQuery
     * @return
     */
    IPage<SysJobLog> selectLogPageList(JobLogQuery baseQuery);

}
