package cn.lenmotion.donut.system.mapper;

import cn.lenmotion.donut.system.entity.po.SysNotice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author lenmotion
 */
@Mapper
public interface SysNoticeMapper extends BaseMapper<SysNotice> {

    int incReadNum(Long noticeId);

}