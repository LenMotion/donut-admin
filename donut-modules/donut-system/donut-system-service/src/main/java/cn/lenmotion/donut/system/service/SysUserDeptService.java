package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.system.entity.po.SysUserDept;
import cn.lenmotion.donut.system.entity.vo.UserDeptVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * @author LenMotion
 */
public interface SysUserDeptService extends IService<SysUserDept> {

    /**
     * 保存用户的关联部门
     * @param userId
     * @param deptList
     */
    void insertUserDept(Long userId, List<SysUserDept> deptList);

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
