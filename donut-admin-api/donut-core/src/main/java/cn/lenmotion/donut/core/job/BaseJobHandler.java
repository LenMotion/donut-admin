package cn.lenmotion.donut.core.job;

/**
 * @author LenMotion
 */
public interface BaseJobHandler {

    /**
     * 任务执行方法
     * @param params 任务参数
     */
    void handle(String params);

}
