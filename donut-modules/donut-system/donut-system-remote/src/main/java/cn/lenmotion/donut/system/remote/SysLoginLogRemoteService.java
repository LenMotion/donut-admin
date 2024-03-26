package cn.lenmotion.donut.system.remote;

import cn.lenmotion.donut.system.entity.po.SysLoginLog;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询token对应的登录信息
     * @param tokenValueList
     * @return
     */
    Map<String, SysLoginLog> selectLoginLogByToken(List<String> tokenValueList);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysLoginLog getLoginLogById(Long id);

}
