package cn.lenmotion.donut.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.lenmotion.donut.auth.entity.LoginBody;
import cn.lenmotion.donut.auth.service.LoginService;
import cn.lenmotion.donut.core.entity.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lenmotion
 */
@Tag(name = "登陆授权")
@RestController
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @Operation(summary = "根据用户名和密码登陆")
    @PostMapping("/login")
    public ResponseResult<SaTokenInfo> login(@RequestBody LoginBody loginBody,
                                             HttpServletRequest request) {
        // 授权登录
        loginService.login(loginBody, request);
        return ResponseResult.success(StpUtil.getTokenInfo());
    }

    @GetMapping("logout")
    @Operation(summary = "退出登陆")
    public ResponseResult<Boolean> logout() {
        loginService.logout();
        return ResponseResult.success();
    }

}
