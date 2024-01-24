package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.system.entity.po.SysNoticeRead;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author lenmotion
 */
public interface SysNoticeReadService extends IService<SysNoticeRead> {

    /**
     * 阅读
     * @param noticeId
     * @param userId
     * @return
     */
    boolean read(Long noticeId, Long userId);

    /**
     * 清空阅读记录
     * @param noticeId
     * @return
     */
    boolean cleanReadRecord(Long noticeId);

}
