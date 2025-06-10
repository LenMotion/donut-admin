package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.system.entity.po.SysDept;
import cn.lenmotion.donut.system.entity.po.SysNoticeSendRelation;
import cn.lenmotion.donut.system.entity.query.UserNoticeQuery;
import cn.lenmotion.donut.system.entity.vo.UserNoticeVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author lenmotion
 */
public interface SysNoticeSendRelationService extends IService<SysNoticeSendRelation> {

    /**
     * 删除通知关联
     *
     * @param noticeId
     */
    void removeRelation(Long noticeId);

    /**
     * 保存通知关联的用户
     *
     * @param noticeId
     * @param userIds
     * @return
     */
    boolean saveRelationUser(Long noticeId, List<Long> userIds);

    /**
     * 保存通知关联的部门
     *
     * @param noticeId
     * @param deptIds
     * @return
     */
    boolean saveRelationDept(Long noticeId, List<Long> deptIds);

    /**
     * 保存通知关联的部门及所有下级部门
     *
     * @param noticeId
     * @param deptList
     * @return
     */
    boolean saveRelationDeptAncestors(Long noticeId, List<SysDept> deptList);

    /**
     * 获取通知已关联的用户id
     *
     * @param noticeId
     * @return
     */
    List<Long> getDeptIdsByNoticeId(Long noticeId);

    /**
     * 获取通知已关联的用户id
     *
     * @param noticeId
     * @return
     */
    List<Long> getUserIdsByNoticeId(Long noticeId);

    /**
     * 获取通知已关联的用户id
     *
     * @param page
     * @param noticeId
     * @return
     */
    IPage<Long> getSendUserByNoticeId(IPage<Long> page, Long noticeId);

    /**
     * 获取用户的通知信息
     *
     * @param query
     * @return
     */
    IPage<UserNoticeVO> getUserNotice(UserNoticeQuery query);

}
