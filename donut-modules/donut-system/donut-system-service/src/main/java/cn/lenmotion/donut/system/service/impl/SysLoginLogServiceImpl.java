package cn.lenmotion.donut.system.service.impl;

import cn.lenmotion.donut.core.constants.ExportTypeConstants;
import cn.lenmotion.donut.framework.excel.ExcelExportClient;
import cn.lenmotion.donut.system.entity.po.SysLoginLog;
import cn.lenmotion.donut.system.entity.query.LoginLogQuery;
import cn.lenmotion.donut.system.entity.vo.export.LoginLogExportVO;
import cn.lenmotion.donut.system.mapper.SysLoginLogMapper;
import cn.lenmotion.donut.system.service.SysLoginLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {

    @Override
    public IPage<SysLoginLog> selectPage(LoginLogQuery query) {
        return getBaseMapper().selectPage(query.toPage(), query);
    }

    @Override
    public void exportLog(LoginLogQuery query) {
        ExcelExportClient.builder()
                .setClass(LoginLogExportVO.class)
                .queryData(() -> getBaseMapper().selectListByQuery(query))
                .exportType(ExportTypeConstants.LOGIN_LIST)
                .fileName("登录日志")
                .doAsyncExport();
    }

}
