package cn.lenmotion.donut.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.lenmotion.donut.core.annotation.DataScope;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.entity.LoginInfo;
import cn.lenmotion.donut.core.enums.BaseStatusEnum;
import cn.lenmotion.donut.core.enums.DataScopeTypeEnum;
import cn.lenmotion.donut.core.utils.AssertUtils;
import cn.lenmotion.donut.core.service.impl.DonutServiceImpl;
import cn.lenmotion.donut.system.entity.converter.RoleConverter;
import cn.lenmotion.donut.system.entity.po.SysRole;
import cn.lenmotion.donut.system.entity.po.SysRoleMenu;
import cn.lenmotion.donut.system.entity.query.RoleQuery;
import cn.lenmotion.donut.system.entity.request.SysRoleRequest;
import cn.lenmotion.donut.system.mapper.SysRoleMapper;
import cn.lenmotion.donut.system.service.SysRoleMenuService;
import cn.lenmotion.donut.system.service.SysRoleService;
import cn.lenmotion.donut.system.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends DonutServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysUserRoleService userRoleService;
    private final SysRoleMenuService roleMenuService;

    @Override
    @DataScope(type = DataScopeTypeEnum.ROLE, roleField = "id", tenantAdminIgnore = true)
    public IPage<SysRole> selectRolePage(RoleQuery query) {
        // 将query转成lambdaQuery的查询条件
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery(SysRole.class)
                .eq(StrUtil.isNotBlank(query.getStatus()), SysRole::getStatus, query.getStatus())
                .like(StrUtil.isNotBlank(query.getRoleKey()), SysRole::getRoleKey, query.getRoleKey())
                .like(StrUtil.isNotBlank(query.getRoleName()), SysRole::getRoleName, query.getRoleName())
                .orderByAsc(SysRole::getRoleSort);
        return getBaseMapper().selectPage(query.toPage(), queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(SysRoleRequest request) {
        SysRole entity = RoleConverter.INSTANCE.requestToPo(request);
        this.checkRoleParams(entity);
        boolean result = super.saveOrUpdate(entity);
        if (result) {
            LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysRoleMenu::getRoleId, entity.getId());
            roleMenuService.remove(queryWrapper);
            // 保存角色和菜单关系
            roleMenuService.saveRoleMenu(entity.getId(), request.getMenuIds(), false);
            roleMenuService.saveRoleMenu(entity.getId(), request.getHalfMenuIds(), true);
        }
        return result;
    }

    @Override
    @DataScope(type = DataScopeTypeEnum.ROLE, roleField = "id", tenantAdminIgnore = true)
    public List<SysRole> selectRolesByLoginInfo(LoginInfo loginInfo) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getStatus, BaseStatusEnum.ENABLED.getCode());
        return list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<?> list) {
        for (Object id : list) {
            AssertUtils.isFalse(BaseConstants.SUPER_ID.equals(id), "超级角色不允许操作");
            // 删除角色前需要校验是否被用户关联
            AssertUtils.isTrue(userRoleService.countByRoleId(Long.valueOf(id.toString())) == 0, "角色已关联用户，不允许删除");
        }
        boolean result = super.removeByIds(list);
        if (result) {
            roleMenuService.clearRoleMenu(list);
        }
        return result;
    }

    /**
     * 校验角色参数
     *
     * @param role
     */
    public void checkRoleParams(SysRole role) {
        AssertUtils.isFalse(BaseConstants.SUPER_ID.equals(role.getId()), "超级角色不允许操作");
        // 判断值是否唯一
        super.validColumnUnique(role, SysRole::getRoleKey, "角色key");
        super.validColumnUnique(role, SysRole::getRoleName, "角色名称");
        if (Objects.isNull(role.getId())) {
            role.setStatus(BaseStatusEnum.DISABLE.getCode());
        }
    }

}
