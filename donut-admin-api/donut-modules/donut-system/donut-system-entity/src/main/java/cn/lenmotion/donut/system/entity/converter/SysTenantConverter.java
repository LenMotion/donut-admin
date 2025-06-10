package cn.lenmotion.donut.system.entity.converter;

import cn.lenmotion.donut.system.entity.po.SysTenant;
import cn.lenmotion.donut.system.entity.request.SysTenantRequest;
import cn.lenmotion.donut.system.entity.vo.TenantBaseVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author lenmotion
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysTenantConverter {

    SysTenantConverter INSTANCE = Mappers.getMapper(SysTenantConverter.class);

    SysTenant toPo(SysTenantRequest request);

    TenantBaseVO toBaseVO(SysTenant sysTenant);

}
