package cn.lenmotion.donut.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.lenmotion.donut.core.constants.ExportTypeConstants;
import cn.lenmotion.donut.framework.excel.ExcelClient;
import cn.lenmotion.donut.system.entity.converter.LogConverter;
import cn.lenmotion.donut.system.entity.po.SysLoginLog;
import cn.lenmotion.donut.system.entity.query.LoginLogQuery;
import cn.lenmotion.donut.system.entity.vo.export.LoginLogExportVO;
import cn.lenmotion.donut.system.mapper.SysLoginLogMapper;
import cn.lenmotion.donut.system.remote.SysExportLogRemoteService;
import cn.lenmotion.donut.system.service.SysLoginLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {

    private final SysExportLogRemoteService exportLogRemoteService;
    private final TaskExecutor taskExecutor;
    private final ExcelClient excelClient;

    @Override
    public IPage<SysLoginLog> selectPage(LoginLogQuery query) {
        return getBaseMapper().selectPage(query.toPage(), query);
    }

    @Override
    public void exportLog(LoginLogQuery query) {
        var userId = StpUtil.getLoginIdAsLong();
        taskExecutor.execute(() -> {
            var timer = DateUtil.timer();
            var exportLog = exportLogRemoteService.startExport(userId, "登录日志", ExportTypeConstants.LOGIN_LIST);
            var logList = getBaseMapper().selectListByQuery(query);
            var list = LogConverter.INSTANCE.toExportVO(logList);
            excelClient.exportTrans(list, LoginLogExportVO.class, exportLog, timer);
        });
    }

}
