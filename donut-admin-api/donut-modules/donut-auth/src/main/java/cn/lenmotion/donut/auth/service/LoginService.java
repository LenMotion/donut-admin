package cn.lenmotion.donut.auth.service;

import cn.lenmotion.donut.auth.entity.LoginBody;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author lenmotion
 */
public interface LoginService {

    /**
     * 登陆
     * @param loginBody 登陆请求信息
     * @param request 请求
     */
    void login(LoginBody loginBody, HttpServletRequest request);

    /**
     * 退出登录
     */
    void logout();

}
