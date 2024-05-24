package cn.lenmotion.donut.system.service.impl;

import cn.lenmotion.donut.system.entity.converter.LogConverter;
import cn.lenmotion.donut.system.entity.po.SysLoginLog;
import cn.lenmotion.donut.system.entity.query.LoginLogQuery;
import cn.lenmotion.donut.system.entity.vo.export.LoginLogExportVO;
import cn.lenmotion.donut.system.mapper.SysLoginLogMapper;
import cn.lenmotion.donut.system.service.SysLoginLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lenmotion
 */
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {

    @Override
    public IPage<SysLoginLog> selectPage(LoginLogQuery query) {
        return getBaseMapper().selectPage(query.toPage(), query);
    }

    @Override
    public List<LoginLogExportVO> selectListByQuery(LoginLogQuery query) {
        var list = getBaseMapper().selectListByQuery(query);
        return LogConverter.INSTANCE.toExportVO(list);
    }

}
