package cn.lenmotion.donut.core.entity;

import cn.lenmotion.donut.core.enums.DataScopeEnum;
import lombok.Data;

import java.util.Set;

/**
 * 登录信息
 * @author lenmotion
 */
@Data
public class LoginInfo {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户id
     */
    private String username;

    /**
     * 所属租户id
     */
    private Long tenantId;

    /**
     * 用户的权限范围
     */
    private Set<DataScopeEnum> roleDataScopes;

    /**
     * 部门结构
     */
    private Set<String> deptAncestors;

}
