package cn.lenmotion.donut.framework.redis;

/**
 * @author lenmotion
 */
public interface LockTask<E> {

    /**
     * 执行的任务
     * @return
     */
    E run();

}
