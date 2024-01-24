package cn.lenmotion.donut.system.mapper;

import cn.lenmotion.donut.system.entity.po.SysLoginLog;
import cn.lenmotion.donut.system.entity.query.LoginLogQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lenmotion
 */
@Mapper
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {

    /**
     * 分页查询
     * @param query
     * @return
     */
    IPage<SysLoginLog> selectPage(IPage<SysLoginLog> page, @Param("query") LoginLogQuery query);

    /**
     * 查询
     * @param query
     * @return
     */
    List<SysLoginLog> selectListByQuery(@Param("query") LoginLogQuery query);

}
