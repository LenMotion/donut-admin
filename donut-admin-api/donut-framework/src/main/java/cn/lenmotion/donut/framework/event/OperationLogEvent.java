package cn.lenmotion.donut.framework.event;

import cn.lenmotion.donut.core.entity.OperationLogData;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author lenmotion
 */
@Getter
public class OperationLogEvent extends ApplicationEvent {

    private final OperationLogData operationLogData;

    public OperationLogEvent(Object source, OperationLogData operationLogData) {
        super(source);
        this.operationLogData = operationLogData;
    }

}
