package cn.lenmotion.donut.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.lenmotion.donut.core.annotation.DataScope;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.constants.ConfigConstants;
import cn.lenmotion.donut.core.enums.BaseStatusEnum;
import cn.lenmotion.donut.core.enums.DataScopeTypeEnum;
import cn.lenmotion.donut.core.utils.AopUtils;
import cn.lenmotion.donut.core.utils.AssertUtils;
import cn.lenmotion.donut.core.utils.EnumUtils;
import cn.lenmotion.donut.core.service.impl.DonutServiceImpl;
import cn.lenmotion.donut.system.entity.po.SysDept;
import cn.lenmotion.donut.system.entity.query.DeptQuery;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.system.mapper.SysDeptMapper;
import cn.lenmotion.donut.system.service.SysConfigService;
import cn.lenmotion.donut.system.service.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.trans.service.impl.TransService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author LenMotion
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends DonutServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysConfigService configService;
    private final TransService transService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveOrUpdateRequest(SysDept entity) {
        this.checkDept(entity);
        if (Objects.isNull(entity.getId())) {
            String ancestors = "";
            if (!BaseConstants.PARENT_ID.equals(entity.getParentId())) {
                SysDept info = super.getById(entity.getParentId());
                // 获取父节点的ancestors
                ancestors += info.getAncestors() + ",";
                // 检查父节点是否符合规则
                this.checkParentDept(info, entity, null);
            } else {
                entity.setLevel(1);
            }

            entity.setAncestors(ancestors);
            boolean result = super.save(entity);
            if (result) {
                // 保存成功，更新ancestors
                entity.setAncestors(ancestors + entity.getId());
                super.updateById(entity);
            }
        } else {
            AopUtils.getAopProxy(this).checkEditPermission(entity.getId(), StpUtil.getLoginIdAsLong());
            // 获取旧的数据
            var oldDept = super.getById(entity.getId());
            // 判断父节点是否发生变化
            if (ObjUtil.notEqual(oldDept.getParentId(), entity.getParentId())) {
                // 获取新的父节点信息
                SysDept info = super.getById(entity.getParentId());
                // 检查新的父节点是否符合要求
                this.checkParentDept(info, entity, oldDept);
                // 更新新的层级关系
                entity.setAncestors(info.getAncestors() + "," + entity.getId());
                // 更新新的层级关系
                getBaseMapper().updateDeptLevel(oldDept.getAncestors(), oldDept.getLevel() - entity.getLevel());
                getBaseMapper().updateDeptAncestors(oldDept.getAncestors(), entity.getAncestors());
            } else {
                entity.setAncestors(null);
                entity.setLevel(null);
            }
            super.updateById(entity);
        }
        return entity.getId();
    }

    /**
     * 判断父节点是否符合要求
     *
     * @param parentDept
     * @param entity
     */
    private void checkParentDept(SysDept parentDept, SysDept entity, SysDept oldDept) {
        AssertUtils.notNull(parentDept, "上级部门不存在");
        // 如果父节点不为正常状态,则不允许新增子节点
        if (entity.getId() == null) {
            AssertUtils.isTrue(BaseStatusEnum.ENABLED.getCode().equals(parentDept.getStatus()),
                    "该上级部门停用，不允许新增下级部门");
        }
        // 判断层级是否超过限制
        entity.setLevel(parentDept.getLevel() + 1);
        var maxDeptLevel = configService.getConfigIntValue(ConfigConstants.MAX_DEPT_LEVEL);
        AssertUtils.isFalse(maxDeptLevel > 0 && entity.getLevel() > maxDeptLevel,
                String.format("部门最大层级不能超过 %s 层", maxDeptLevel));
        // 如果是更新，并且层级发生了变化，那么也需要判断层级是否超过限制
        if (!Objects.isNull(oldDept) && entity.getLevel() > oldDept.getLevel() && maxDeptLevel > 0) {
            // 改变了多少层级
            int changeLevel = entity.getLevel() - oldDept.getLevel();
            // 现在子层级最大的层级数量
            var maxChildLevel = getBaseMapper().maxChildLevel(oldDept.getAncestors());
            // 相加，判断是否超过层级
            AssertUtils.isFalse(Objects.nonNull(maxChildLevel) && maxChildLevel + changeLevel > maxDeptLevel,
                    String.format("部门最大层级不能超过 %s 层", maxDeptLevel));
        }
    }

    /**
     * 校验部门信息
     *
     * @param dept
     */
    private void checkDept(SysDept dept) {
        // 查询部门名称和编号不重复
        super.validColumnUnique(dept, SysDept::getDeptName, "部门名称");
        super.validColumnUnique(dept, SysDept::getDeptCode, "部门编号");
        // 断言传入的父id是否和自己的id一直，相同则抛出异常
        AssertUtils.notEquals(dept.getId(), dept.getParentId(), "新增部门'" + dept.getDeptName() + "'失败，上级角色不能选择自己");
        AopUtils.getAopProxy(this).checkEditPermission(dept.getId(), StpUtil.getLoginIdAsLong());
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        var userId = StpUtil.getLoginIdAsLong();
        for (Object object : list) {
            AssertUtils.notNull(object, "id不能为空");
            AopUtils.getAopProxy(this).checkEditPermission(Long.parseLong(object.toString()), userId);

            LambdaQueryWrapper<SysDept> queryWrapper = Wrappers.lambdaQuery(SysDept.class);
            queryWrapper.eq(SysDept::getParentId, object);
            AssertUtils.isTrue(this.count(queryWrapper) == 0, "存在下级部门，不允许删除");
        }
        return super.removeByIds(list);
    }

    @Override
    @DataScope(type = DataScopeTypeEnum.DEPT, field = "id")
    public void checkEditPermission(Long deptId, Long userId) {
        if (deptId == null) {
            return;
        }
        // 超级管理员不做限制，任意更改
        if (BaseConstants.SUPER_ID.equals(userId)) {
            return;
        }
        // 查询当前用户是否有权限编辑该部门
        var hasDeptPerm = super.getById(deptId);
        AssertUtils.notNull(hasDeptPerm, "没有该部门的权限！无法操作该部门的任何信息！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(BaseUpdateStatus request) {
        AopUtils.getAopProxy(this).checkEditPermission(request.getId(), StpUtil.getLoginIdAsLong());
        boolean result = super.updateStatus(request);
        if (result) {
            var dept = super.getById(request.getId());
            // 更新子部门状态
            BaseStatusEnum status = EnumUtils.getByCode(BaseStatusEnum.class, request.getStatus());
            AssertUtils.notNull(status, "状态值不正确");
            switch (status) {
                case ENABLED -> {
                    LambdaUpdateWrapper<SysDept> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.apply("find_in_set(id, '" + dept.getAncestors() + "')")
                            .set(SysDept::getStatus, BaseStatusEnum.ENABLED.getCode())
                            .eq(SysDept::getStatus, BaseStatusEnum.DISABLE.getCode());
                    super.update(updateWrapper);
                }
                case DISABLE -> {
                    LambdaQueryWrapper<SysDept> queryWrapper = Wrappers.lambdaQuery(SysDept.class);
                    queryWrapper.apply("find_in_set('" + dept.getId() + "', ancestors)")
                            .eq(SysDept::getStatus, BaseStatusEnum.ENABLED.getCode())
                            .ne(SysDept::getId, dept.getId())
                            .select(SysDept::getId)
                            .last(BaseConstants.LIMIT_1);
                    // 断言查询结果是否为空，不为空则抛出异常
                    AssertUtils.isNull(getOne(queryWrapper), "部门'" + dept.getDeptName() + "'拥有未停用的子部门，无法停用");
                }
            }
        }
        return result;
    }

    @Override
    @DataScope(type = DataScopeTypeEnum.DEPT, field = "id")
    public List<SysDept> getDeptList(DeptQuery query) {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(query.getStatus()), SysDept::getStatus, query.getStatus())
                .like(StrUtil.isNotBlank(query.getDeptCode()), SysDept::getDeptCode, query.getDeptCode())
                .like(StrUtil.isNotBlank(query.getDeptName()), SysDept::getDeptName, query.getDeptName());

        return this.list(queryWrapper);
    }

    @Override
    public List<Tree<Long>> getDeptTree(DeptQuery query) {
        // 查询拥有的部门权限
        List<SysDept> deptList = AopUtils.getAopProxy(this).getDeptList(query);
        // 创建一个空的Set集合
        Set<Long> deptIdSet = new HashSet<>();
        // 如果传入的deptList不为空，需要获取这些部门对应的上级，需要将这些上级设置为不可更改的上级信息
        if (CollUtil.isNotEmpty(deptList)) {
            // 获取deptList中每一个元素的ancestors属性，并将其转换为List
            List<String> ancestorsList = deptList.stream()
                    .map(SysDept::getAncestors)
                    .toList();
            // 将ancestorsList中的每一个元素拆分为字符串，并将其转换为Long类型，然后收集到deptIdSet中
            deptIdSet = ancestorsList.stream()
                    .flatMap(str -> Stream.of(str.split(",")))
                    .map(Long::valueOf)
                    .collect(Collectors.toSet());
            // 获取deptList中每一个元素的Id属性，并转换为Set
            Set<Long> hasIdSet = deptList.stream().map(SysDept::getId).collect(Collectors.toSet());
            // 从deptIdSet中移除hasIdSet中的元素
            deptIdSet.removeAll(hasIdSet);
            // 如果deptIdSet不为空，表示有上级部门，需要将这些上级部门查询出来并添加到deptList中
            if (CollUtil.isNotEmpty(deptIdSet)) {
                // 将deptIdSet中的元素查询的部门添加到deptList中
                deptList.addAll(super.listByIds(deptIdSet));
            }
        }
        // 转换数据字典
        transService.transBatch(deptList);

        List<TreeNode<Long>> treeNodeList = new ArrayList<>(deptList.size());
        for (SysDept sysDept : deptList) {
            TreeNode<Long> treeNode = new TreeNode<>(sysDept.getId(), sysDept.getParentId(), sysDept.getDeptName(), sysDept.getOrderNum());

            Map<String, Object> beanMap = BeanUtil.beanToMap(sysDept);
            // 将对象中的transMap属性存储到beanMap中
            beanMap.put("transMap", sysDept.getTransMap());
            // 判断是否是上级的标识，用于前端标识是否可以修改操作
            beanMap.put("parentDeptTag", deptIdSet.contains(sysDept.getId()));
            treeNode.setExtra(beanMap);
            treeNodeList.add(treeNode);
        }

        return CollUtil.isEmpty(treeNodeList) ? new ArrayList<>() : TreeUtil.build(treeNodeList, BaseConstants.PARENT_ID);
    }
}
