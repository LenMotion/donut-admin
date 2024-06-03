package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.core.entity.LoginInfo;
import cn.lenmotion.donut.core.service.DonutService;
import cn.lenmotion.donut.system.entity.po.SysRole;
import cn.lenmotion.donut.system.entity.query.RoleQuery;
import cn.lenmotion.donut.system.entity.request.SysRoleRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author lenmotion
 */
public interface SysRoleService extends DonutService<SysRole> {

    /**
     * 查询角色里表
     *
     * @param roleQuery
     * @return
     */
    IPage<SysRole> selectRolePage(RoleQuery roleQuery);

    /**
     * 新增角色
     *
     * @param request
     * @return
     */
    boolean saveOrUpdate(SysRoleRequest request);

    /**
     * 查询用户的角色
     * @param loginInfo
     * @return
     */
    List<SysRole> selectRolesByLoginInfo(LoginInfo loginInfo);

}
