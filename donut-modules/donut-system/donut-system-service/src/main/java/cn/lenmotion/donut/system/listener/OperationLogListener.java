package cn.lenmotion.donut.system.listener;

import cn.lenmotion.donut.core.entity.OperationLogData;
import cn.lenmotion.donut.framework.event.OperationLogEvent;
import cn.lenmotion.donut.system.entity.covert.OperationLogConverter;
import cn.lenmotion.donut.system.entity.po.SysOperationLog;
import cn.lenmotion.donut.system.service.SysOperationLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @author lenmotion
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OperationLogListener implements ApplicationListener<OperationLogEvent> {

    private final SysOperationLogService operationLogService;
    private final TaskExecutor taskExecutor;

    @Override
    public void onApplicationEvent(OperationLogEvent event) {
        try {
            taskExecutor.execute(() -> {
                OperationLogData operationLog = event.getOperationLogData();
                if (operationLog == null) {
                    return;
                }
                // 转换对象
                SysOperationLog po = OperationLogConverter.INSTANCE.toPo(operationLog);
                operationLogService.save(po);
            });
        } catch (Exception e) {
            log.error("保存操作日志失败", e);
        }
    }

}
