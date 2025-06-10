package cn.lenmotion.donut.system.service.impl;

import cn.lenmotion.donut.system.entity.query.OperationLogQuery;
import cn.lenmotion.donut.system.entity.vo.OperationLogVO;
import cn.lenmotion.donut.system.mapper.SysOperationLogMapper;
import cn.lenmotion.donut.system.service.SysOperationLogService;
import cn.lenmotion.donut.system.entity.po.SysOperationLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
/**
 * 
 * @author lenmotion
 */
@Service
public class SysOperationLogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLog> implements SysOperationLogService {

    @Override
    public IPage<OperationLogVO> selectPage(OperationLogQuery query) {
        return getBaseMapper().selectPage(query.toPage(), query);
    }

}
