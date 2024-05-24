package cn.lenmotion.donut.system.entity.converter;

import cn.lenmotion.donut.system.entity.po.SysNotice;
import cn.lenmotion.donut.system.entity.request.SysNoticeRequest;
import cn.lenmotion.donut.system.entity.vo.NoticeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author lenmotion
 */
@Mapper
public interface NoticeConverter {

    NoticeConverter INSTANCE = Mappers.getMapper(NoticeConverter.class);

    SysNotice toPo(SysNoticeRequest request);

    NoticeVO toVO(SysNotice notice);

}
