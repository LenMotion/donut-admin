package cn.lenmotion.donut.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.lenmotion.donut.core.annotation.DataScope;
import cn.lenmotion.donut.core.enums.DataScopeTypeEnum;
import cn.lenmotion.donut.core.exception.BusinessException;
import cn.lenmotion.donut.core.utils.AopUtils;
import cn.lenmotion.donut.system.entity.po.SysRole;
import cn.lenmotion.donut.system.entity.po.SysUser;
import cn.lenmotion.donut.system.entity.po.SysUserRole;
import cn.lenmotion.donut.system.entity.query.RoleUserQuery;
import cn.lenmotion.donut.system.entity.request.RoleBindUserRequest;
import cn.lenmotion.donut.system.entity.request.RoleUnbindUserRequest;
import cn.lenmotion.donut.system.entity.vo.RoleUserVO;
import cn.lenmotion.donut.system.mapper.SysUserMapper;
import cn.lenmotion.donut.system.mapper.SysUserRoleMapper;
import cn.lenmotion.donut.system.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * @author lenmotion
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    private final SysUserMapper userMapper;

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

    @Override
    public IPage<RoleUserVO> selectUserList(RoleUserQuery query) {
        var page = getBaseMapper().selectUserList(query.toPage(), query);

        if (CollUtil.isNotEmpty(page.getRecords())) {
            List<Long> userIds = page.getRecords().stream().map(RoleUserVO::getUserId).toList();
            // 查询有权限的用户
            var hasUserIds = AopUtils.getAopProxy(this).hasUserPermission(userIds);
            // 设置信息
            for (RoleUserVO record : page.getRecords()) {
                record.setHasPermission(hasUserIds.contains(record.getUserId()));
            }
        }

        return page;
    }

    @Override
    @DataScope(type = DataScopeTypeEnum.DEPT, field = "dept_id", alias = "u")
    public IPage<RoleUserVO> unbindUserList(RoleUserQuery query) {
        return getBaseMapper().unbindUserList(query.toPage(), query);
    }

    @DataScope(type = DataScopeTypeEnum.DEPT, field = "dept_id")
    public List<Long> hasUserPermission(List<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return new ArrayList<>();
        }

        return userMapper.selectBatchIds(userIds).stream().map(SysUser::getId).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean bindUser(RoleBindUserRequest request) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getRoleId, request.getRoleId());
        queryWrapper.in(SysUserRole::getUserId, request.getUserIds());
        var hasUserIds = this.list(queryWrapper).stream().map(SysUserRole::getUserId).toList();

        for (Long userId : request.getUserIds()) {
            if (hasUserIds.contains(userId)) {
                log.info("用户：{}， 已有角色，不做处理", userId);
                continue;
            }

            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(request.getRoleId());
            this.save(userRole);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataScope(type = DataScopeTypeEnum.DEPT, field = "dept_id")
    public Boolean unbind(SysUserRole userRole) {
        SysUser sysUser = userMapper.selectById(userRole.getUserId());
        if (sysUser == null) {
            throw new BusinessException("无法操作该用户");
        }
        return super.removeById(userRole);
    }

}
