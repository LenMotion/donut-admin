package cn.lenmotion.donut.system.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjUtil;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.constants.ConfigConstants;
import cn.lenmotion.donut.core.context.TenantContext;
import cn.lenmotion.donut.core.enums.BaseStatusEnum;
import cn.lenmotion.donut.core.enums.DataScopeEnum;
import cn.lenmotion.donut.core.service.impl.DonutServiceImpl;
import cn.lenmotion.donut.core.utils.AssertUtils;
import cn.lenmotion.donut.system.entity.converter.SysTenantConverter;
import cn.lenmotion.donut.system.entity.po.*;
import cn.lenmotion.donut.system.entity.query.SysTenantQuery;
import cn.lenmotion.donut.system.entity.request.SysTenantRequest;
import cn.lenmotion.donut.system.entity.vo.RoleMenuIdVO;
import cn.lenmotion.donut.system.entity.vo.TenantBaseVO;
import cn.lenmotion.donut.system.mapper.SysTenantMapper;
import cn.lenmotion.donut.system.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lenmotion
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysTenantServiceImpl extends DonutServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {

    private final SysUserService userService;
    private final SysUserRoleService userRoleService;
    private final SysRoleService roleService;
    private final SysRoleMenuService roleMenuService;
    private final SysConfigService configService;
    private final SysDeptService deptService;
    private final SysPostService postService;
    private final SysUserDeptService userDeptService;

    @Override
    public IPage<SysTenant> selectPageList(SysTenantQuery query) {
        var queryWrapper = Wrappers.<SysTenant>lambdaQuery();
        queryWrapper.eq(ObjUtil.isNotEmpty(query.getName()), SysTenant::getName, query.getName())
                .eq(ObjUtil.isNotEmpty(query.getGroupName()), SysTenant::getGroupName, query.getGroupName())
                .orderByDesc(SysTenant::getCreateTime);
        return this.page(query.toPage(), queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdate(SysTenantRequest request) {
        AssertUtils.isFalse(BaseConstants.SUPER_ID.equals(request.getId()), "超级租户不可操作");
        var sysTenant = SysTenantConverter.INSTANCE.toPo(request);
        var currentTenant = TenantContext.getTenant();
        super.validColumnUnique(sysTenant, SysTenant::getName, "租户名称");

        boolean result = super.saveOrUpdate(sysTenant);
        log.info("saveOrUpdate result: {}", result);
        // 创建租户时，创建默认用户
        if (result && request.getId() == null) {
            var defaultPassword = configService.getConfigByKey(ConfigConstants.USER_DEFAULT_PASSWORD);
            var configList = configService.list();

            TenantContext.setTenant(sysTenant.getId());
            // 创建默认角色
            var role = this.saveRole(request);
            // 创建默认岗位
            var post = this.savePost(sysTenant);
            // 创建默认部门
            var dept = this.saveDept(sysTenant);
            // 创建默认用户
            var user = this.saveUser(sysTenant, dept, post, defaultPassword);
            // 创建默认用户角色
            this.saveUserRole(user, role);
            // 创建默认用户部门
            this.saveUserDept(user);
            // 初始化配置信息
            this.initConfig(configList);
            TenantContext.setTenant(currentTenant);

            sysTenant.setUserId(user.getId());
            sysTenant.setRoleId(role.getId());
            result = super.updateById(sysTenant);
        } else {
            var hasTenant = super.getById(sysTenant.getId());
            TenantContext.setTenant(sysTenant.getId());
            var menuIdVO = roleMenuService.getMenuIdListByRoleId(hasTenant.getRoleId());

            roleMenuService.clearRoleMenu(List.of(hasTenant.getRoleId()));
            roleMenuService.saveRoleMenu(hasTenant.getRoleId(), request.getMenuIds(), false);
            roleMenuService.saveRoleMenu(hasTenant.getRoleId(), request.getHalfMenuIds(), true);
            TenantContext.setTenant(currentTenant);
        }

        return result;
    }

    /**
     * 保存角色
     */
    private SysRole saveRole(SysTenantRequest request) {
        var role = new SysRole();
        role.setStatus(BaseStatusEnum.ENABLED.getCode());
        role.setDataScope(DataScopeEnum.DEPT_AND_CHILDREN.getCode());
        role.setRoleKey("SUPER");
        role.setRoleName("超级管理员");
        role.setRoleSort(1);
        roleService.save(role);

        roleMenuService.saveRoleMenu(role.getId(), request.getMenuIds(), false);
        roleMenuService.saveRoleMenu(role.getId(), request.getHalfMenuIds(), true);
        return role;
    }

    /**
     * 保存部门
     */
    private SysDept saveDept(SysTenant sysTenant) {
        var dept = new SysDept();
        dept.setStatus(BaseStatusEnum.ENABLED.getCode());
        dept.setId(IdUtil.getSnowflakeNextId());
        dept.setDeptName(sysTenant.getName());
        dept.setShortName(sysTenant.getName());
        dept.setDeptCode("001");
        dept.setParentId(0L);
        dept.setLevel(1);
        dept.setAncestors(dept.getId().toString());
        dept.setOrderNum(1);
        deptService.save(dept);

        return dept;
    }

    /**
     * 保存用户关联部门
     */
    private void saveUserDept(SysUser user) {
        SysUserDept userDept = new SysUserDept();
        userDept.setUserId(user.getId());
        userDept.setDeptId(user.getDeptId());
        userDept.setPostId(user.getPostId());
        userDept.setDefaultDept(true);
        userDeptService.save(userDept);
    }

    /**
     * 保存岗位
     */
    private SysPost savePost(SysTenant sysTenant) {
        var post = new SysPost();
        post.setStatus(BaseStatusEnum.ENABLED.getCode());
        post.setDeptId(0L);
        post.setPostName("超级管理员");
        post.setPostCode("SUPER");
        post.setOrderNo(1);
        postService.save(post);
        log.info("初始化租户【{}】岗位成功", sysTenant.getName());
        return post;
    }

    /**
     * 保存用户
     */
    private SysUser saveUser(SysTenant sysTenant, SysDept dept, SysPost post, String defaultPassword) {
        var user = new SysUser();
        user.setStatus(BaseStatusEnum.ENABLED.getCode());
        user.setBirthday(LocalDate.now());
        user.setSex("1");
        user.setUsername("admin");
        user.setDeptId(dept.getId());
        user.setPostId(post.getId());
        user.setUserCode("001");
        user.setNickName("超级管理员");
        user.setRealName(sysTenant.getName());
        user.setPassword(SaSecureUtil.sha256BySalt(defaultPassword, user.getUsername()));
        userService.save(user);
        log.info("初始化租户【{}】管理员成功", sysTenant.getName());
        return user;
    }

    /**
     * 关联用户角色
     */
    private void saveUserRole(SysUser user, SysRole role) {
        var userRole = new SysUserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        userRoleService.save(userRole);
    }

    /**
     * 初始化配置信息
     */
    private void initConfig(List<SysConfig> configList) {
        configList.forEach(e -> {
            e.setId(null);
            e.setTenantId(null);
        });
        configService.saveBatch(configList);
    }

    @Override
    public List<TenantBaseVO> baseInfoByIds(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return List.of();
        }
        LambdaQueryWrapper<SysTenant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysTenant::getId, ids)
                .eq(SysTenant::getStatus, BaseStatusEnum.ENABLED.getCode());
        return list(queryWrapper).stream().map(SysTenantConverter.INSTANCE::toBaseVO).collect(Collectors.toList());
    }

    @Override
    public RoleMenuIdVO listMenuIdByTenantId(Long tenantId) {
        var tenant = super.getById(tenantId);
        AssertUtils.notNull(tenant, "租户不存在");
        var currentTenant = TenantContext.getTenant();
        // 这里单独设置租户，避免查询数据失败
        TenantContext.setTenant(tenant.getId());
        RoleMenuIdVO result = roleMenuService.getMenuIdListByRoleId(tenant.getRoleId());
        TenantContext.setTenant(currentTenant);
        return result;
    }

}
