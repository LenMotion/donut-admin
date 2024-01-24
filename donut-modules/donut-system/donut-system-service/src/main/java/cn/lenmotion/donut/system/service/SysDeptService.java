package cn.lenmotion.donut.system.service;

import cn.hutool.core.lang.tree.Tree;
import cn.lenmotion.donut.core.service.DonutService;
import cn.lenmotion.donut.system.entity.po.SysDept;
import cn.lenmotion.donut.system.entity.query.DeptQuery;

import java.util.List;

/**
  *
  * @author LenMotion
  */
public interface SysDeptService extends DonutService<SysDept> {

    /**
     * 查询dept列表
     * @param deptQuery
     * @return
     */
    List<SysDept> getDeptList(DeptQuery deptQuery);

    /**
     * 获取部门权限树
     * @param deptQuery
     * @return
     */
    List<Tree<Long>> getDeptTree(DeptQuery deptQuery);

    /**
     * 校验用户是否有某个部门的权限
     * @param deptId
     * @param userId
     */
    void checkEditPermission(Long deptId, Long userId);

}
