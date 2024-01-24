package cn.lenmotion.donut.system.entity.covert;

import cn.lenmotion.donut.system.entity.po.SysRole;
import cn.lenmotion.donut.system.entity.request.SysRoleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author lenmotion
 */
@Mapper
public interface RoleConverter {

    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);

    SysRole requestToPo(SysRoleRequest request);

}
