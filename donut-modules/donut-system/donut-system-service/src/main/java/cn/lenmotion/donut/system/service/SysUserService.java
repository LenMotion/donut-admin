package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.core.entity.BaseImportResult;
import cn.lenmotion.donut.core.service.DonutService;
import cn.lenmotion.donut.system.entity.po.SysUser;
import cn.lenmotion.donut.system.entity.query.UserQuery;
import cn.lenmotion.donut.system.entity.request.SysUserRequest;
import cn.lenmotion.donut.system.entity.request.UserAvatarRequest;
import cn.lenmotion.donut.system.entity.request.UserProfileRequest;
import cn.lenmotion.donut.system.entity.request.UserPwdRequest;
import cn.lenmotion.donut.system.entity.vo.UserResponseVO;
import cn.lenmotion.donut.system.entity.vo.export.UserExportVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author lenmotion
 */
public interface SysUserService extends DonutService<SysUser> {

    /**
     * 查询用户列表
     *
     * @param userQuery
     * @return
     */
    IPage<UserResponseVO> selectUserPage(UserQuery userQuery);

    /**
     * 查询导出的用户信息
     *
     * @param userQuery
     * @return
     */
    List<UserExportVO> selectUserExportList(UserQuery userQuery);

    /**
     * 查询导出的用户信息
     *
     * @param userQuery
     */
    void exportUserList(UserQuery userQuery);

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return 用户
     */
    SysUser getByUsername(String username);

    /**
     * 创建用户
     *
     * @param request
     * @return
     */
    Boolean saveOrUpdate(SysUserRequest request);

    /**
     * 获取用户的详细信息
     *
     * @param userId
     * @return
     */
    UserResponseVO getUserDetail(Long userId);

    /**
     * 导入用户
     *
     * @param userList
     * @return
     */
    BaseImportResult importUser(List<SysUserRequest> userList);

    /**
     * 用户更新个人信息
     *
     * @param request
     * @return
     */
    Boolean updateProfile(UserProfileRequest request);

    /**
     * 用户更新个人信息
     *
     * @param request
     * @return
     */
    Boolean updateAvatar(UserAvatarRequest request);

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    Boolean updatePassword(UserPwdRequest request);

}
