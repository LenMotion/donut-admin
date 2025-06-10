package cn.lenmotion.donut.system.remote;

import cn.lenmotion.donut.system.entity.po.SysUser;

/**
 * @author lenmotion
 */
public interface SysUserRemoteService {

    /**
     * 根据用户名查询
     * @param username 用户名
     * @return 用户
     */
    SysUser getByUsername(String username);

    /**
     * 跟新用户
     *
     * @param user
     */
    void updateUser(SysUser user);

    /**
     * 根据id获取
     * @param userId
     * @return
     */
    SysUser getById(Long userId);

}
