package cn.lenmotion.donut.system.service.impl;

import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.constants.RedisConstants;
import cn.lenmotion.donut.system.entity.po.SysNoticeRead;
import cn.lenmotion.donut.system.mapper.SysNoticeMapper;
import cn.lenmotion.donut.system.mapper.SysNoticeReadMapper;
import cn.lenmotion.donut.system.service.SysNoticeReadService;
import com.baomidou.lock.annotation.Lock4j;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class SysNoticeReadServiceImpl extends ServiceImpl<SysNoticeReadMapper, SysNoticeRead> implements SysNoticeReadService {

    private final SysNoticeMapper noticeMapper;

    @Override
    @Lock4j(name = RedisConstants.USER_READ_NOTICE_LOCK_KEY, keys = {"#noticeId", "#userId"})
    public boolean read(Long noticeId, Long userId) {
        // 查询是否已经读取
        LambdaQueryWrapper<SysNoticeRead> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysNoticeRead::getNoticeId, noticeId)
                .eq(SysNoticeRead::getUserId, userId)
                .last(BaseConstants.LIMIT_1);

        SysNoticeRead sysNoticeRead = this.getOne(queryWrapper);

        if (sysNoticeRead == null) {
            // 如果没有，则新建一个
            sysNoticeRead = new SysNoticeRead();
            sysNoticeRead.setNoticeId(noticeId);
            sysNoticeRead.setUserId(userId);
            this.save(sysNoticeRead);

            // 更新通知的阅读数
            noticeMapper.incReadNum(noticeId);
        }

        return true;
    }

    @Override
    public boolean cleanReadRecord(Long noticeId) {
        LambdaQueryWrapper<SysNoticeRead> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysNoticeRead::getNoticeId, noticeId);
        return this.remove(queryWrapper);
    }
}
