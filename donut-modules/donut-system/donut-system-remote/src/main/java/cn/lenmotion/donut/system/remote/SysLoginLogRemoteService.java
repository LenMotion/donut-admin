package cn.lenmotion.donut.system.remote;

import cn.lenmotion.donut.system.entity.po.SysLoginLog;

/**
 * @author lenmotion
 */
public interface SysLoginLogRemoteService {

    /**
     * 保存登陆日志
     * @param loginLog
     * @return
     */
    boolean saveLoginLog(SysLoginLog loginLog);

}
