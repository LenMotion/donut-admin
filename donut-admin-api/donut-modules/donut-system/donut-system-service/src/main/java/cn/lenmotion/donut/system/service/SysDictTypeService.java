package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.core.service.DonutService;
import cn.lenmotion.donut.system.entity.po.SysDictType;
import cn.lenmotion.donut.system.entity.query.DictTypeQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author lenmotion
 */
public interface SysDictTypeService extends DonutService<SysDictType> {

    /**
     * 根据条件分页查询字典类型
     *
     * @param query 查询信息
     * @return 字典类型集合信息
     */
    IPage<SysDictType> selectDictTypePage(DictTypeQuery query);

}
