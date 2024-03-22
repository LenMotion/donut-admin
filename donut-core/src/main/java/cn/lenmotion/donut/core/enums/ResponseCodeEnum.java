package cn.lenmotion.donut.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lenmotion
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseEnum<Integer> {
    SUCCESS(200, "SUCCESS"),
    FAILED(500, "系统错误，请联系管理员"),
    UN_LOGIN(1401, "登陆已失效，请重新授权"),
    USER_DISABLED(1402, "用户已禁用，请联系管理员"),
    UN_AUTH(1403, "无访问权限，请联系管理员"),
    USERNAME_OR_PWD_ERROR(1404, "用户名或密码错误"),
    ELSEWHERE_LOGIN(1405, "账号已在其他地方登录"),
    BAD_ARGUMENT_VALUE(1601, "请求参数错误"),
    METHOD_NOT_SUPPORT(1602, "请求方式错误"),
    HEARTBEAT(1000, "online"),
    ;

    /**
     * 编码
     */
    private final Integer code;

    /**
     * 备注
     */
    private final String remark;

}
