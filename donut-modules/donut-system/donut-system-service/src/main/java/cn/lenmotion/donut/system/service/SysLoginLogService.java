package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.system.entity.po.SysLoginLog;
import cn.lenmotion.donut.system.entity.query.LoginLogQuery;
import cn.lenmotion.donut.system.entity.vo.export.LoginLogExportVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author lenmotion
 */
public interface SysLoginLogService extends IService<SysLoginLog> {

    /**
     * 分页查询
     * @param query
     * @return
     */
    IPage<SysLoginLog> selectPage(LoginLogQuery query);

    /**
     * 分页查询
     * @param query
     * @return
     */
    List<LoginLogExportVO> selectListByQuery(LoginLogQuery query);

}
