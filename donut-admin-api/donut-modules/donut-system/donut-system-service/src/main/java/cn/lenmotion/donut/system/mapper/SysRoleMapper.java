package cn.lenmotion.donut.system.mapper;

import cn.lenmotion.donut.system.entity.po.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lenmotion
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询用户的角色
     * @param userId
     * @return
     */
    List<SysRole> selectRolesByUserId(Long userId);

}