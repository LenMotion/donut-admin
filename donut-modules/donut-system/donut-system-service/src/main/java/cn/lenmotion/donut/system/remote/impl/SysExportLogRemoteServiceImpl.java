package cn.lenmotion.donut.system.remote.impl;

import cn.lenmotion.donut.system.entity.enums.ExportStatusEnum;
import cn.lenmotion.donut.system.entity.po.SysExportLog;
import cn.lenmotion.donut.system.remote.SysExportLogRemoteService;
import cn.lenmotion.donut.system.service.SysExportLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class SysExportLogRemoteServiceImpl implements SysExportLogRemoteService {

    private final SysExportLogService exportLogService;


    @Override
    public SysExportLog startExport(Long userId, String fileName) {
        var exportLog = new SysExportLog();
        exportLog.setName(fileName);
        exportLog.setStatus(ExportStatusEnum.RUNNING.getCode());
        exportLog.setUserId(userId);
        exportLogService.save(exportLog);
        return exportLog;
    }

    @Override
    public void endExport(SysExportLog exportLog) {
        exportLogService.updateById(exportLog);
    }

}
