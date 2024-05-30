package cn.lenmotion.donut.framework.aspect;

import cn.dev33.satoken.stp.StpUtil;
import cn.lenmotion.donut.core.annotation.DataScope;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.context.DataScopeContext;
import cn.lenmotion.donut.core.entity.LoginInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author lenmotion
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DataScopeAspect {

    @Before(value = "@annotation(dataScope)")
    public void doBefore(JoinPoint joinPoint, DataScope dataScope) {
        Long userId = StpUtil.getLoginIdAsLong();
        var loginInfo = (LoginInfo) StpUtil.getSession().get(BaseConstants.SESSION_LOGIN_INFO);
        DataScopeContext.setDataScope(dataScope, userId, loginInfo);
    }

    @After(value = "@annotation(dataScope)")
    public void doAfter(JoinPoint joinPoint, DataScope dataScope) {
        DataScopeContext.clear();
    }

}
