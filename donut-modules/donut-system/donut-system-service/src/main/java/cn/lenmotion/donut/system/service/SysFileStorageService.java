package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.system.entity.po.SysFileStorage;
import cn.lenmotion.donut.system.entity.query.FileStorageQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author lenmotion
 */
public interface SysFileStorageService extends IService<SysFileStorage> {

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    IPage<SysFileStorage> selectPage(FileStorageQuery query);

}
