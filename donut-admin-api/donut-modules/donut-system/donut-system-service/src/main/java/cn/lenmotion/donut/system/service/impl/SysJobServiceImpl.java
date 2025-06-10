package cn.lenmotion.donut.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.extra.spring.SpringUtil;
import cn.lenmotion.donut.core.enums.BaseStatusEnum;
import cn.lenmotion.donut.core.exception.BusinessException;
import cn.lenmotion.donut.core.job.BaseJobHandler;
import cn.lenmotion.donut.core.utils.AssertUtils;
import cn.lenmotion.donut.core.service.impl.DonutServiceImpl;
import cn.lenmotion.donut.system.entity.enums.JobStatusEnum;
import cn.lenmotion.donut.system.entity.enums.JobTypeEnum;
import cn.lenmotion.donut.system.entity.po.SysJob;
import cn.lenmotion.donut.system.entity.po.SysJobLog;
import cn.lenmotion.donut.system.entity.query.JobLogQuery;
import cn.lenmotion.donut.system.entity.query.JobQuery;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.system.mapper.SysJobLogMapper;
import cn.lenmotion.donut.system.mapper.SysJobMapper;
import cn.lenmotion.donut.system.service.SysJobService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author LenMotion
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysJobServiceImpl extends DonutServiceImpl<SysJobMapper, SysJob> implements SysJobService {

    private final SysJobLogMapper jobLogMapper;

    @PostConstruct
    public void init() {
        log.info("开始初始化任务，将所有任务添加到执行器里面");
        LambdaQueryWrapper<SysJob> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysJob::getStatus, BaseStatusEnum.ENABLED.getCode());

        var list = this.list(queryWrapper);
        log.info("当前总任务数: {}", list.size());
        list.forEach(this::addSchedule);
        CronUtil.setMatchSecond(true);
        CronUtil.start(true);
        log.info("任务初始化完成");
    }

    @PreDestroy
    public void destroy() {
        if (CronUtil.getScheduler().isStarted()) {
            CronUtil.stop();
            log.info("终止任务");
        }
    }

    @Override
    public IPage<SysJob> selectPageList(JobQuery baseQuery) {
        LambdaQueryWrapper<SysJob> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(baseQuery.getName()), SysJob::getName, baseQuery.getName())
                .like(StrUtil.isNotBlank(baseQuery.getTaskClass()), SysJob::getTaskClass, baseQuery.getTaskClass())
                .eq(StrUtil.isNotBlank(baseQuery.getStatus()), SysJob::getStatus, baseQuery.getStatus())
                .orderByDesc(SysJob::getId);
        return super.page(baseQuery.toPage(), queryWrapper);
    }

    @Override
    public void execNow(Long id) {
        var job = this.getById(id);
        AssertUtils.notNull(job, "任务不存在");
        // 手动执行
        this.exec(job, JobTypeEnum.MANUAL).execute();
    }

    @Override
    public boolean saveOrUpdate(SysJob entity) {
        try {
            Class.forName(entity.getTaskClass());
        } catch (ClassNotFoundException e) {
            throw new BusinessException("任务类不存在");
        }
        super.validColumnUnique(entity, SysJob::getName, "任务名称");
        super.validColumnUnique(entity, SysJob::getTaskClass, "任务类");
        if (Objects.isNull(entity.getId())) {
            entity.setStatus(BaseStatusEnum.DISABLE.getCode());
        }
        return super.saveOrUpdate(entity);
    }

    @Override
    public boolean updateStatus(BaseUpdateStatus request) {
        boolean result = super.updateStatus(request);

        if (result) {
            if (BaseStatusEnum.ENABLED.getCode().equals(request.getStatus())) {
                var job = this.getById(request.getId());
                // 添加定时任务
                this.addSchedule(job);
            } else {
                // 移除定时任务
                CronUtil.remove(String.valueOf(request.getId()));
            }
        }

        return result;
    }

    @Override
    public IPage<SysJobLog> selectLogPageList(JobLogQuery baseQuery) {
        LambdaQueryWrapper<SysJobLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjUtil.isNotNull(baseQuery.getJobId()), SysJobLog::getJobId, baseQuery.getJobId())
                .eq(StrUtil.isNotBlank(baseQuery.getStatus()), SysJobLog::getStatus, baseQuery.getStatus())
                .eq(StrUtil.isNotBlank(baseQuery.getType()), SysJobLog::getType, baseQuery.getType())
                .le(StrUtil.isNotBlank(baseQuery.getCreateEndTime()), SysJobLog::getCreateTime, baseQuery.getCreateEndTime())
                .ge(StrUtil.isNotBlank(baseQuery.getCreateStartTime()), SysJobLog::getCreateTime, baseQuery.getCreateStartTime())
                .orderByDesc(SysJobLog::getId);
        return jobLogMapper.selectPage(baseQuery.toPage(), queryWrapper);
    }

    /**
     * 添加一个任务到执行器里面
     *
     * @param job
     */
    public void addSchedule(SysJob job) {
        try {
            // 使用CronUtil的schedule方法，根据job的id，cron表达式，以及一个lambda表达式，来执行任务
            CronUtil.schedule(job.getId().toString(), job.getCron(), exec(job, JobTypeEnum.AUTO));
        } catch (Exception e) {
            log.error("添加任务失败", e);
        }
    }

    /**
     * 执行器的内容
     *
     * @param job
     * @return 返回一个任务
     */
    public Task exec(SysJob job, JobTypeEnum typeEnum) {
        return () -> {
            var timer = DateUtil.timer();
            // 任务日志
            var jobLog = new SysJobLog();
            jobLog.setJobId(job.getId());
            jobLog.setStatus(JobStatusEnum.RUNNING.getCode());
            jobLog.setType(typeEnum.getCode());
            jobLogMapper.insert(jobLog);

            try {
                // 使用SpringUtil的getBean方法，根据job的taskClass属性，获取任务处理器
                var task = (BaseJobHandler) SpringUtil.getBean(Class.forName(job.getTaskClass()));
                // 调用任务处理器的handle方法，传入job的params属性
                task.handle(job.getParams());
                jobLog.setStatus(JobStatusEnum.FINISHED.getCode());
            } catch (Exception e) {
                log.error("执行任务失败", e);
                jobLog.setErrorMsg(e.getMessage());
                jobLog.setStatus(JobStatusEnum.FAILED.getCode());
            } finally {
                jobLog.setTime(timer.interval());
                jobLog.setEndTime(LocalDateTime.now());
                jobLogMapper.updateById(jobLog);
            }
        };
    }

}
