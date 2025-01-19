package cn.lenmotion.donut.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.lenmotion.donut.core.annotation.DataScope;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.constants.ConfigConstants;
import cn.lenmotion.donut.core.context.TenantContext;
import cn.lenmotion.donut.core.enums.BaseStatusEnum;
import cn.lenmotion.donut.core.enums.DataScopeTypeEnum;
import cn.lenmotion.donut.core.exception.BusinessException;
import cn.lenmotion.donut.core.service.impl.DonutServiceImpl;
import cn.lenmotion.donut.core.utils.AssertUtils;
import cn.lenmotion.donut.core.utils.EnumUtils;
import cn.lenmotion.donut.system.entity.converter.MenuConverter;
import cn.lenmotion.donut.system.entity.enums.MenuTypeEnum;
import cn.lenmotion.donut.system.entity.po.SysMenu;
import cn.lenmotion.donut.system.entity.po.SysRole;
import cn.lenmotion.donut.system.entity.po.SysRoleMenu;
import cn.lenmotion.donut.system.entity.vo.RouteMetaVO;
import cn.lenmotion.donut.system.entity.vo.RouteVO;
import cn.lenmotion.donut.system.mapper.SysMenuMapper;
import cn.lenmotion.donut.system.mapper.SysRoleMapper;
import cn.lenmotion.donut.system.mapper.SysRoleMenuMapper;
import cn.lenmotion.donut.system.service.SysConfigService;
import cn.lenmotion.donut.system.service.SysMenuService;
import cn.lenmotion.donut.system.service.SysRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.trans.service.impl.TransService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.lenmotion.donut.core.constants.BaseConstants.LIMIT_1;

/**
 * @author lenmotion
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends DonutServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysRoleMenuService roleMenuService;
    private final SysConfigService configService;
    private final TransService transService;

    private final SysRoleMapper roleMapper;
    private final SysRoleMenuMapper roleMenuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysMenu saveOrUpdateMenu(SysMenu entity) {
        this.checkMenu(entity);
        var insert = entity.getId() == null;
        if (entity.getId() == null) {
            entity.setStatus(BaseStatusEnum.DISABLE.getCode());
        }
        super.saveOrUpdate(entity);
        // 如果不是超级租户在调整菜单，那么需要把菜单权限赋给超级租户的 SUPER 角色
        if (insert && !BaseConstants.SUPER_ID.equals(TenantContext.getTenant())) {
            LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysRole::getRoleKey, "SUPER")
                    .orderByAsc(SysRole::getCreateTime)
                    .last(LIMIT_1);
            SysRole sysRole = roleMapper.selectOne(queryWrapper);
            if (sysRole == null) {
                throw new BusinessException("当前租户不存在 SUPER 角色，无法新建菜单");
            }
            // 保存新的权限
            var roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(sysRole.getId());
            roleMenu.setMenuId(entity.getId());
            roleMenuMapper.insert(roleMenu);
        }
        return entity;
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        for (Object id : list) {
            long count = roleMenuService.countByMenuId(id.toString());
            AssertUtils.isTrue(count == 0, "该菜单下存在角色，无法删除");

            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysMenu::getParentId, id);
            AssertUtils.isTrue(this.count(queryWrapper) == 0, "该菜单下存在子菜单，无法删除");
        }

        return super.removeByIds(list);
    }

    /**
     * 参数校验
     *
     * @param menu
     */
    public void checkMenu(SysMenu menu) {
        MenuTypeEnum menuType = EnumUtils.getByCode(MenuTypeEnum.class, menu.getMenuType());
        AssertUtils.notNull(menuType, "菜单类型不能为空");
        AssertUtils.notEquals(menu.getParentId(), menu.getId(), "不能选择自己的作为上级菜单");

        if (menu.getParentId() != null && menu.getParentId() > 0) {
            AssertUtils.notNull(this.getById(menu.getParentId()), "父菜单不存在");
        }

        if (!MenuTypeEnum.BUTTON.equals(menuType) && !menu.getFrame()) {
            AssertUtils.notBlank(menu.getName(), "菜单组件名称不能为空");

            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysMenu::getName, menu.getName())
                    .ne(Objects.nonNull(menu.getId()), SysMenu::getId, menu.getId())
                    .last(LIMIT_1);
            AssertUtils.isNull(this.getOne(queryWrapper), "菜单组件名称已存在");
        }

        // 参数判断
        switch (menuType) {
            case CATALOG -> {
                AssertUtils.notBlank(menu.getPath(), "菜单路由地址不能为空");
                menu.setComponent(menuType.getComponent());
                AssertUtils.isTrue(StrUtil.startWith(menu.getPath(), "/"), "目录路由地址必须以/开头");
            }
            case MENU -> {
                AssertUtils.notBlank(menu.getPath(), "菜单路由地址不能为空");
                AssertUtils.notBlank(menu.getComponent(), "菜单组件路径不能为空");
                AssertUtils.isFalse(StrUtil.startWith(menu.getPath(), "/"), "菜单路由地址不能以/开头");
            }
            case BUTTON -> {
                AssertUtils.notBlank(menu.getPerms(), "菜单权限标识不能为空");
                menu.setPath(StringUtils.EMPTY);
                menu.setComponent(menuType.getComponent());
            }
        }
        // 如果是外链
        if (Boolean.TRUE.equals(menu.getFrame())) {
            AssertUtils.isTrue(HttpUtil.isHttp(menu.getPath()) || HttpUtil.isHttps(menu.getPath()), "外链地址必须以http://或https://开头");
        }
    }

    @Override
    @DataScope(type = DataScopeTypeEnum.MENU, menuField = "id")
    public List<Tree<Long>> getMenuTree() {
        // 根据传入的SysMenu对象，查询数据库中的菜单信息
        List<SysMenu> menus = this.list();
        // 转换
        transService.transBatch(menus);
        // 创建一个TreeNode<Long>类型的列表，用于存储查询出来的菜单信息
        List<TreeNode<Long>> nodeList = new ArrayList<>();
        // 遍历查询出来的菜单信息，将菜单信息封装成TreeNode<Long>类型，并存储到nodeList中
        for (SysMenu menu : menus) {
            TreeNode<Long> treeNode = new TreeNode<>(menu.getId(), menu.getParentId(), menu.getName(), menu.getOrderNo());
            Map<String, Object> beanMap = BeanUtil.beanToMap(menu);
            // 将菜单信息中的transMap属性存储到beanMap中
            beanMap.put("transMap", menu.getTransMap());
            treeNode.setExtra(beanMap);
            nodeList.add(treeNode);
        }
        // 根据传入的rootId，从nodeList中查找对应的菜单信息，并将其封装成TreeNode<Long>类型，返回
        return TreeUtil.build(nodeList, 0L);
    }

    @Override
    public List<SysMenu> getMenuListByUserId(Long userId) {
        // 如果是超级管理员，则查询所有菜单，否则根据用户ID查询菜单
        if (BaseConstants.SUPER_ID.equals(userId)) {
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysMenu::getMenuType, CollUtil.newArrayList(MenuTypeEnum.MENU.getCode(), MenuTypeEnum.CATALOG.getCode()))
                    .eq(SysMenu::getStatus, BaseStatusEnum.ENABLED.getCode())
                    .orderByAsc(SysMenu::getParentId, SysMenu::getOrderNo);
            return list(queryWrapper);
        } else {
            return roleMenuService.getMenuListByUserId(userId);
        }
    }

    @Override
    public List<Tree<Long>> getRouteListByUserId(Long userId) {
        if (userId == null) {
            return CollUtil.newArrayList();
        }
        Collection<SysMenu> menuList = this.getMenuListByUserId(userId);
        // 获取系统配置的默认菜单
        var defaultMenuStr = configService.getConfigByKey(ConfigConstants.DEFAULT_MENU);
        if (StrUtil.isNotBlank(defaultMenuStr)) {
            // 已经有的菜单
            var menuMap = menuList.stream().collect(Collectors.toMap(SysMenu::getId, Function.identity()));
            // 获取系统配置的菜单
            for (String id : defaultMenuStr.split(",")) {
                this.getMenuAndParentMenu(Long.parseLong(id), menuMap);
            }
            menuList = menuMap.values();
        }
        // 构建树
        List<TreeNode<Long>> treeNodeList = new ArrayList<>(menuList.size());

        for (SysMenu sysMenu : menuList) {
            // 将sysMenu转换为RouteVO
            RouteVO routeVO = MenuConverter.INSTANCE.toRouteVO(sysMenu);
            // 将sysMenu转换为RouteMetaVO
            RouteMetaVO routeMetaVO = MenuConverter.INSTANCE.toRouteMetaVO(sysMenu);
            if (sysMenu.getFrame()) {
                routeMetaVO.setFrameSrc(sysMenu.getPath());
            }
            // 将RouteMetaVO设置到RouteVO中
            routeVO.setMeta(routeMetaVO);

            // 创建TreeNode对象
            TreeNode<Long> treeNode = new TreeNode<>(sysMenu.getId(), sysMenu.getParentId(), sysMenu.getName(), sysMenu.getOrderNo());
            // 设置TreeNode的额外信息
            treeNode.setExtra(BeanUtil.beanToMap(routeVO));
            // 将TreeNode添加到list中
            treeNodeList.add(treeNode);
        }
        // 构建树
        List<Tree<Long>> treeList = TreeUtil.build(treeNodeList, BaseConstants.PARENT_ID);
        // 构建后如果是空树，那么new一个空的list
        return CollUtil.isEmpty(treeList) ? CollUtil.newArrayList() : treeList;
    }

    /**
     * 根据菜单id获取菜单信息
     *
     * @param id
     * @param hasMenuId
     */
    private void getMenuAndParentMenu(Long id, Map<Long, SysMenu> hasMenuId) {
        SysMenu menu = hasMenuId.get(id);
        if (menu == null) {
            // 没有的时候就去获取
            menu = this.getById(id);
            if (menu == null) {
                return;
            }
            // 再添加到map
            hasMenuId.put(id, menu);
        }
        // 如果有上级，继续获取上级的菜单
        if (menu.getParentId() != null && menu.getParentId() != 0) {
            this.getMenuAndParentMenu(menu.getParentId(), hasMenuId);
        }
    }

}
