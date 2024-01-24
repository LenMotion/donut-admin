package cn.lenmotion.donut.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.lenmotion.donut.system.entity.po.SysUserDept;
import cn.lenmotion.donut.system.entity.vo.UserDeptVO;
import cn.lenmotion.donut.system.mapper.SysUserDeptMapper;
import cn.lenmotion.donut.system.service.SysUserDeptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author LenMotion
 */
@Service
public class SysUserDeptServiceImpl extends ServiceImpl<SysUserDeptMapper, SysUserDept> implements SysUserDeptService {

    @Override
    public void insertUserDept(Long userId, List<SysUserDept> deptList) {
        LambdaQueryWrapper<SysUserDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDept::getUserId, userId);
        this.remove(queryWrapper);

        if (CollUtil.isEmpty(deptList)) {
            return;
        }

        for (SysUserDept sysUserDept : deptList) {
            sysUserDept.setUserId(userId);
            this.save(sysUserDept);
        }
    }

    @Override
    public List<UserDeptVO> getOtherUserDeptListByUserId(Long userId) {
        return getBaseMapper().getOtherUserDeptListByUserId(userId);
    }

    @Override
    public Set<String> getDeptAncestorsByUserId(Long userId) {
        return getBaseMapper().getDeptAncestorsByUserId(userId);
    }

}
