package cn.lenmotion.donut.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.lenmotion.donut.system.entity.po.SysRole;
import cn.lenmotion.donut.system.entity.po.SysUserRole;
import cn.lenmotion.donut.system.mapper.SysUserRoleMapper;
import cn.lenmotion.donut.system.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService{

    @Override
    public long countByRoleId(Long roleId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getRoleId, roleId);
        return this.count(queryWrapper);
    }

    @Override
    public List<SysRole> getRolesByUserId(Long userId) {
        return getBaseMapper().getRoleIdsByUserId(userId);
    }

    @Override
    public Set<String> getRoleKeysByUserId(Long userId) {
        return getBaseMapper().getRoleIdsByUserId(userId).stream().map(SysRole::getRoleKey).collect(Collectors.toSet());
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        return getBaseMapper().getRoleIdsByUserId(userId).stream().map(SysRole::getId).toList();
    }

    @Override
    public void insertUserRole(Long userId, List<Long> roleIdList) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId, userId);
        this.remove(queryWrapper);

        if (CollUtil.isEmpty(roleIdList)) {
            return;
        }

        for (Long roleId : roleIdList) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            this.save(userRole);
        }
    }
}
