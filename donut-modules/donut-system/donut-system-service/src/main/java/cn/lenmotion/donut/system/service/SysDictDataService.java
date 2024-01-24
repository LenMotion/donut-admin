package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.core.service.DonutService;
import cn.lenmotion.donut.system.entity.po.SysDictData;
import cn.lenmotion.donut.system.entity.query.DictDataQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author lenmotion
 */
public interface SysDictDataService extends DonutService<SysDictData> {

    /**
     * 根据value获取label
     * @param dictKey
     * @param dictValue
     * @return
     */
    String getLabelByValue(String dictKey, String dictValue);

    /**
     * 根据label获取value
     * @param dictKey
     * @param dictLabel
     * @return
     */
    String getValueByLabel(String dictKey, String dictLabel);

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictDataQuery 分页信息
     * @return 字典数据集合信息
     */
    IPage<SysDictData> selectDictDataList(DictDataQuery dictDataQuery);

    /**
     * 刷新缓存
     * @param dictKey
     */
    void refreshCache(String dictKey);

}
