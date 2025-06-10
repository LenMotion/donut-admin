package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.core.service.DonutService;
import cn.lenmotion.donut.system.entity.po.SysNotice;
import cn.lenmotion.donut.system.entity.query.NoticeQuery;
import cn.lenmotion.donut.system.entity.request.SysNoticeRequest;
import cn.lenmotion.donut.system.entity.vo.NoticeVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author lenmotion
 */
public interface SysNoticeService extends DonutService<SysNotice> {

    /**
     * 分页查询
     * @param baseQuery
     * @return
     */
    IPage<SysNotice> selectPage(NoticeQuery baseQuery);

    /**
     * 新增或者修改
     * @param request
     * @return
     */
    boolean saveOrUpdate(SysNoticeRequest request);

    /**
     * 根据详情获取
     * @param id
     * @return
     */
    NoticeVO getDetailById(Long id);

}
