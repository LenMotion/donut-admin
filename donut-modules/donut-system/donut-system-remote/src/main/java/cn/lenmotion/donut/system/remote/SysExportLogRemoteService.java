package cn.lenmotion.donut.system.remote;

import cn.lenmotion.donut.system.entity.po.SysExportLog;

/**
 * @author lenmotion
 */
public interface SysExportLogRemoteService {

    /**
     * 开始导出
     * @param userId
     * @param fileName
     * @param exportType
     * @return
     */
    SysExportLog startExport(Long userId, String fileName, String exportType);

    /**
     * 新增
     * @param exportLog
     */
    void save(SysExportLog exportLog);

    /**
     * 结束导出
     * @param exportLog
     */
    void endExport(SysExportLog exportLog);

}
