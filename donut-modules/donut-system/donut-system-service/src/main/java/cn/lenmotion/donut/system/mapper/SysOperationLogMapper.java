package cn.lenmotion.donut.system.mapper;

import cn.lenmotion.donut.system.entity.po.SysOperationLog;
import cn.lenmotion.donut.system.entity.query.OperationLogQuery;
import cn.lenmotion.donut.system.entity.vo.OperationLogVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * @author lenmotion
 */
public interface SysOperationLogMapper extends BaseMapper<SysOperationLog> {

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    IPage<OperationLogVO> selectPage(IPage<OperationLogVO> page, @Param("query") OperationLogQuery query);

}