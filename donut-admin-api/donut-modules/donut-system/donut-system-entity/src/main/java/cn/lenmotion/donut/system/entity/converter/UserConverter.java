package cn.lenmotion.donut.system.entity.converter;

import cn.lenmotion.donut.system.entity.po.SysUser;
import cn.lenmotion.donut.system.entity.request.SysUserRequest;
import cn.lenmotion.donut.system.entity.request.UserProfileRequest;
import cn.lenmotion.donut.system.entity.vo.UserInfoVO;
import cn.lenmotion.donut.system.entity.vo.UserResponseVO;
import cn.lenmotion.donut.system.entity.vo.imported.UserImportVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author lenmotion
 */
@Mapper
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    @Mapping(target = "quickNav", ignore = true)
    UserInfoVO toInfoVO(SysUser user);

    SysUser toPo(SysUserRequest request);

    UserResponseVO toResponseVO(SysUser user);

    SysUserRequest toPo(UserImportVO importVO);

    List<SysUserRequest> toPo(List<UserImportVO> importVOList);

    @Mapping(target = "quickNav", ignore = true)
    SysUser toPo(UserProfileRequest request);

}
