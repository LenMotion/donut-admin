package cn.lenmotion.donut.monitor.entity.converter;

import cn.lenmotion.donut.monitor.entity.OnlineUserVO;
import cn.lenmotion.donut.system.entity.po.SysLoginLog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author LenMotion
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoginLogConverter {

    LoginLogConverter INSTANCE = Mappers.getMapper(LoginLogConverter.class);

    OnlineUserVO toOnlineUserVO(SysLoginLog loginLog);

}
