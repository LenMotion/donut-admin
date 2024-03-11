package cn.lenmotion.donut.system.entity.covert;

import cn.lenmotion.donut.system.entity.po.SysMenu;
import cn.lenmotion.donut.system.entity.vo.RouteMetaVO;
import cn.lenmotion.donut.system.entity.vo.RouteVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author lenmotion
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuConverter {

    MenuConverter INSTANCE = Mappers.getMapper(MenuConverter.class);

    RouteVO toRouteVO(SysMenu sysMenu);

    RouteMetaVO toRouteMetaVO(SysMenu sysMenu);

}
