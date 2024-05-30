package cn.lenmotion.donut.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjUtil;
import cn.lenmotion.donut.core.service.impl.DonutServiceImpl;
import cn.lenmotion.donut.system.entity.po.SysExportLog;
import cn.lenmotion.donut.system.entity.query.SysExportLogQuery;
import cn.lenmotion.donut.system.mapper.SysExportLogMapper;
import cn.lenmotion.donut.system.service.SysExportLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class SysExportLogServiceImpl extends DonutServiceImpl<SysExportLogMapper, SysExportLog> implements SysExportLogService {

    @Override
    public IPage<SysExportLog> selectPageList(SysExportLogQuery query) {
        var queryWrapper = Wrappers.<SysExportLog>lambdaQuery();
        queryWrapper
                .eq(SysExportLog::getUserId, StpUtil.getLoginIdAsLong())
                .eq(ObjUtil.isNotEmpty(query.getName()), SysExportLog::getName, query.getName())
                .eq(ObjUtil.isNotEmpty(query.getErrorMsg()), SysExportLog::getErrorMsg, query.getErrorMsg())
                .orderByDesc(SysExportLog::getCreateTime);
        return this.page(query.toPage(), queryWrapper);
    }

}
