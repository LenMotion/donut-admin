package cn.lenmotion.donut.system.mapper;

import cn.lenmotion.donut.system.entity.po.SysDictData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lenmotion
 */
@Mapper
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    /**
     * 获取启用状态的数据字典信息
     * @param dictKey
     * @return
     */
    List<SysDictData> getEnableByDictKey(String dictKey);

}
