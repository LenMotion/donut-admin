package cn.lenmotion.donut.system.mapper;

import cn.lenmotion.donut.system.entity.po.SysUser;
import cn.lenmotion.donut.system.entity.query.UserQuery;
import cn.lenmotion.donut.system.entity.vo.UserResponseVO;
import cn.lenmotion.donut.system.entity.vo.export.UserExportVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LenMotion
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询用户列表
     *
     * @param userQuery
     * @return
     */
    IPage<UserResponseVO> selectUserPage(IPage<UserResponseVO> page, @Param("query") UserQuery userQuery);

    /**
     * 查询导出的用户信息
     *
     * @param userQuery
     * @return
     */
    List<UserExportVO> selectUserExportList(@Param("query") UserQuery userQuery);

}
