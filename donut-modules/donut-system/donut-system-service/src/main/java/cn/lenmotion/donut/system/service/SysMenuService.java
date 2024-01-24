package cn.lenmotion.donut.system.service;

import cn.hutool.core.lang.tree.Tree;
import cn.lenmotion.donut.system.entity.po.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author lenmotion
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取菜单树
     *
     * @return
     */
    List<Tree<Long>> getMenuTree();

    /**
     * 获取用户的菜单列表
     * @param userId
     * @return
     */
    List<SysMenu> getMenuListByUserId(Long userId);

    /**
     * 获取菜单
     * @param userId
     * @return
     */
    List<Tree<Long>> getRouteListByUserId(Long userId);

    /**
     * 新增或修改菜单
     * @param entity
     * @return
     */
    Long saveOrUpdateMenu(SysMenu entity);

}
