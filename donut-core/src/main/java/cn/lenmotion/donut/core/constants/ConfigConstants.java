package cn.lenmotion.donut.core.constants;

/**
 * @author LenMotion
 */
public interface ConfigConstants {

    /**
     * 是否开启验证码校验
     */
    String CAPTCHA_ON_OFF = "LOGIN_CAPTCHA_SWITCH";

    /**
     * 最大部门等级
     */
    String MAX_DEPT_LEVEL = "MAX_DEPT_LEVEL";

    /**
     * 用户默认密码
     */
    String USER_DEFAULT_PASSWORD = "USER_DEFAULT_PASSWORD";

    /**
     * 获取系统配置的默认菜单
     */
    String DEFAULT_MENU = "DEFAULT_MENU";

    /**
     * 账号密码连续输入错误次数
     */
    String ACCOUNT_LOCK_COUNT = "ACCOUNT_LOCK_COUNT";

    /**
     * 账号锁定时长（分钟）
     */
    String ACCOUNT_LOCK_TIME = "ACCOUNT_LOCK_TIME";

}
