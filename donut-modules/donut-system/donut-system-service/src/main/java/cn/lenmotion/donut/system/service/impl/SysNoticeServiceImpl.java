package cn.lenmotion.donut.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.core.enums.BaseStatusEnum;
import cn.lenmotion.donut.core.service.impl.DonutServiceImpl;
import cn.lenmotion.donut.core.utils.AssertUtils;
import cn.lenmotion.donut.core.utils.EnumUtils;
import cn.lenmotion.donut.system.entity.covert.NoticeConverter;
import cn.lenmotion.donut.system.entity.enums.NoticeSendTypeEnum;
import cn.lenmotion.donut.system.entity.po.SysNotice;
import cn.lenmotion.donut.system.entity.query.NoticeQuery;
import cn.lenmotion.donut.system.entity.request.SysNoticeRequest;
import cn.lenmotion.donut.system.entity.vo.NoticeVO;
import cn.lenmotion.donut.system.mapper.SysDeptMapper;
import cn.lenmotion.donut.system.mapper.SysNoticeMapper;
import cn.lenmotion.donut.system.service.SysNoticeReadService;
import cn.lenmotion.donut.system.service.SysNoticeSendRelationService;
import cn.lenmotion.donut.system.service.SysNoticeService;
import cn.lenmotion.donut.system.websocket.NoticePublisher;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author lenmotion
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysNoticeServiceImpl extends DonutServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {

    private final SysNoticeSendRelationService sendRelationService;
    private final SysNoticeReadService noticeReadService;
    private final SysDeptMapper deptMapper;
    private final NoticePublisher noticePublisher;

    @Override
    public IPage<SysNotice> selectPage(NoticeQuery baseQuery) {
        LambdaQueryWrapper<SysNotice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(baseQuery.getNoticeTitle()), SysNotice::getNoticeTitle, baseQuery.getNoticeTitle())
                .eq(Objects.nonNull(baseQuery.getNoticeType()), SysNotice::getNoticeType, baseQuery.getNoticeType())
                .orderByDesc(SysNotice::getCreateTime);
        return this.page(baseQuery.toPage(), queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(SysNoticeRequest request) {
        var sendType = EnumUtils.getByCode(NoticeSendTypeEnum.class, request.getNoticeSendType());
        AssertUtils.notNull(sendType, "通知类型不能为空");

        var notice = NoticeConverter.INSTANCE.toPo(request);
        // 判断新增还是修改
        if (Objects.nonNull(notice.getId())) {
            var oldNotice = this.getById(notice.getId());
            AssertUtils.notNull(oldNotice, "通知不存在");
            AssertUtils.equals(oldNotice.getStatus(), BaseStatusEnum.DISABLE.getCode(), "通知已发布，无法修改");
            // 修改通知时，需要先删除关联关系
            sendRelationService.removeRelation(notice.getId());
        } else {
            notice.setStatus(BaseStatusEnum.DISABLE.getCode());
        }

        var saveResult = super.saveOrUpdate(notice);
        if (saveResult) {
            // 保存关联关系
            var result = switch (sendType) {
                case USER -> sendRelationService.saveRelationUser(notice.getId(), request.getUserIds());
                case DEPT -> sendRelationService.saveRelationDept(notice.getId(), request.getDeptIds());
                case DEPT_AND_CHILDREN -> {
                    if (CollUtil.isNotEmpty(request.getDeptIds())) {
                        var deptList = deptMapper.selectBatchIds(request.getDeptIds());
                        yield sendRelationService.saveRelationDeptAncestors(notice.getId(), deptList);
                    }
                    yield true;
                }
                case ALL -> true;
            };
            log.info("新增关联信息结果: {}", result);
        }

        return saveResult;
    }

    @Override
    public NoticeVO getDetailById(Long id) {
        var notice = super.getById(id);
        AssertUtils.notNull(notice, "通知不存在");
        // 转成vo
        var noticeVO = NoticeConverter.INSTANCE.toVO(notice);
        // 获取关联关系
        var sendType = EnumUtils.getByCode(NoticeSendTypeEnum.class, notice.getNoticeSendType());
        AssertUtils.notNull(sendType, "通知发送类型不能为空");
        // 根据发送类型设置关联关系
        switch (sendType) {
            case USER ->  noticeVO.setUserIds(sendRelationService.getUserIdsByNoticeId(id));
            case DEPT, DEPT_AND_CHILDREN -> noticeVO.setDeptIds(sendRelationService.getDeptIdsByNoticeId(id));
        }

        return noticeVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(BaseUpdateStatus request) {
        var result = super.updateStatus(request);
        // 更新成功，并且状态为正常，发送广播出去，发送notice通知
        if (result && BaseStatusEnum.ENABLED.getCode().equals(request.getStatus())) {
            // 更新发布时间
            SysNotice notice = new SysNotice();
            notice.setId(request.getId());
            notice.setPublishTime(LocalDateTime.now());
            notice.setReadNum(0);

            super.updateById(notice);
            // 清空阅读记录
            noticeReadService.cleanReadRecord(request.getId());
            // 发送通知
            noticePublisher.publish(this.getById(request.getId()));
        }

        return result;
    }
}
