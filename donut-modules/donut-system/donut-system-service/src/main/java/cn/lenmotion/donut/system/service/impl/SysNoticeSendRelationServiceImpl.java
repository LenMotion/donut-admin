package cn.lenmotion.donut.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.lenmotion.donut.system.entity.po.SysDept;
import cn.lenmotion.donut.system.entity.po.SysNoticeSendRelation;
import cn.lenmotion.donut.system.entity.query.UserNoticeQuery;
import cn.lenmotion.donut.system.entity.vo.UserNoticeVO;
import cn.lenmotion.donut.system.mapper.SysNoticeSendRelationMapper;
import cn.lenmotion.donut.system.service.SysNoticeSendRelationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lenmotion
 */
@Service
public class SysNoticeSendRelationServiceImpl extends ServiceImpl<SysNoticeSendRelationMapper, SysNoticeSendRelation> implements SysNoticeSendRelationService {

    @Override
    public void removeRelation(Long noticeId) {
        LambdaQueryWrapper<SysNoticeSendRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysNoticeSendRelation::getNoticeId, noticeId);
        this.remove(queryWrapper);
    }

    @Override
    public boolean saveRelationUser(Long noticeId, List<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return false;
        }

        return super.saveBatch(userIds.stream().map(userId -> {
            var relation = new SysNoticeSendRelation();
            relation.setNoticeId(noticeId);
            relation.setUserId(userId);
            return relation;
        }).toList());
    }

    @Override
    public boolean saveRelationDept(Long noticeId, List<Long> deptIds) {
        if (CollUtil.isEmpty(deptIds)) {
            return false;
        }

        return super.saveBatch(deptIds.stream().map(deptId -> {
            var relation = new SysNoticeSendRelation();
            relation.setNoticeId(noticeId);
            relation.setDeptId(deptId);
            return relation;
        }).toList());
    }

    @Override
    public boolean saveRelationDeptAncestors(Long noticeId, List<SysDept> deptList) {
        if (CollUtil.isEmpty(deptList)) {
            return false;
        }

        return super.saveBatch(deptList.stream().map(dept -> {
            var relation = new SysNoticeSendRelation();
            relation.setNoticeId(noticeId);
            relation.setDeptAncestors(dept.getAncestors());
            relation.setDeptId(dept.getId());
            return relation;
        }).toList());
    }

    @Override
    public List<Long> getDeptIdsByNoticeId(Long noticeId) {
        return this.getRelationByNoticeId(noticeId).stream().map(SysNoticeSendRelation::getDeptId).toList();
    }

    @Override
    public List<Long> getUserIdsByNoticeId(Long noticeId) {
        return this.getRelationByNoticeId(noticeId).stream().map(SysNoticeSendRelation::getUserId).toList();
    }

    @Override
    public IPage<Long> getSendUserByNoticeId(IPage<Long> page, Long noticeId) {
        return getBaseMapper().getSendUserByNoticeId(page, noticeId);
    }

    @Override
    public IPage<UserNoticeVO> getUserNotice(UserNoticeQuery query) {
        return getBaseMapper().getUserNotice(query.toPage(), query);
    }

    private List<SysNoticeSendRelation> getRelationByNoticeId(Long noticeId) {
        LambdaQueryWrapper<SysNoticeSendRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysNoticeSendRelation::getNoticeId, noticeId);
        return super.list(queryWrapper);
    }

}
