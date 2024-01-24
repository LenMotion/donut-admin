package cn.lenmotion.donut.system.mapper;

import cn.lenmotion.donut.system.entity.po.SysNoticeSendRelation;
import cn.lenmotion.donut.system.entity.query.UserNoticeQuery;
import cn.lenmotion.donut.system.entity.vo.UserNoticeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * @author lenmotion
 */
public interface SysNoticeSendRelationMapper extends BaseMapper<SysNoticeSendRelation> {

    /**
     * 获取通知已关联的用户id
     * @param page
     * @param noticeId
     * @return
     */
    IPage<Long> getSendUserByNoticeId(IPage<Long> page, @Param("noticeId") Long noticeId);

    /**
     * 获取用户的通知信息
     *
     * @param query
     * @return
     */
    IPage<UserNoticeVO> getUserNotice(IPage<UserNoticeVO> page, @Param("query") UserNoticeQuery query);

}