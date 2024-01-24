package cn.lenmotion.donut.system.mapper;

import cn.lenmotion.donut.system.entity.po.SysUserDept;
import cn.lenmotion.donut.system.entity.vo.UserDeptVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Set;

/**
 * @author LenMotion
 */
public interface SysUserDeptMapper extends BaseMapper<SysUserDept> {

    /**
     * 获取其他用户部门列表 （除开默认的部门）
     * @param userId
     * @return
     */
    List<UserDeptVO> getOtherUserDeptListByUserId(Long userId);

    /**
     * 获取其他用户部门列表 （除开默认的部门）
     * @param userId
     * @return
     */
    Set<String> getDeptAncestorsByUserId(Long userId);

}