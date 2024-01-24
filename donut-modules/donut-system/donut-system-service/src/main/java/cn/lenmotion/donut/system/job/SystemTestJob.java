package cn.lenmotion.donut.system.job;

import cn.lenmotion.donut.core.job.BaseJobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LenMotion
 */
@Slf4j
@Component
public class SystemTestJob implements BaseJobHandler {

    @Override
    public void handle(String params) {
        log.info("这是任务参数： {}", params);
        log.info("执行测试任务");
    }

}
