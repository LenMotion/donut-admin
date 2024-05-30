package cn.lenmotion.donut.core.utils;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.lenmotion.donut.core.annotation.DataScope;
import cn.lenmotion.donut.core.entity.LoginInfo;
import cn.lenmotion.donut.core.enums.DataScopeEnum;
import cn.lenmotion.donut.core.enums.DataScopeTypeEnum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author lenmotion
 */
public class DataScopeUtils {

    private static final Map<DataScopeTypeEnum, String> DATA_SCOPE_SQL_MAP = new HashMap<>();

    static {
        DATA_SCOPE_SQL_MAP.put(DataScopeTypeEnum.ROLE, " {} in ( select role_id from sys_user_role where user_id = {} )");
        DATA_SCOPE_SQL_MAP.put(DataScopeTypeEnum.MENU, " {} in ( select menu_id from sys_role_menu rm inner join sys_user_role ur on ur.role_id = rm.role_id where ur.user_id = {} )");
        DATA_SCOPE_SQL_MAP.put(DataScopeTypeEnum.DEPT, " {} in ( select id from sys_dept where {} and deleted = 0 )");
    }

    public static String getDataScopeSql(Long userId, LoginInfo loginInfo, DataScope dataScope) {
        return switch (dataScope.type()) {
            case ROLE -> {
                String column = DataScopeUtils.getColumn(dataScope.roleAlias(), dataScope.roleField());
                yield StrUtil.format(DATA_SCOPE_SQL_MAP.get(dataScope.type()), column, StpUtil.getLoginIdAsLong());
            }
            case MENU -> {
                String column = DataScopeUtils.getColumn(dataScope.menuAlias(), dataScope.menuField());
                yield StrUtil.format(DATA_SCOPE_SQL_MAP.get(dataScope.type()), column, StpUtil.getLoginIdAsLong());
            }
            case DEPT -> {
                // 如果为空则表示没有权限，不允许获取数据
                if (CollUtil.isEmpty(loginInfo.getRoleDataScopes()) || CollUtil.isEmpty(loginInfo.getDeptAncestors())) {
                    yield "1=2";
                }
                // 生成列
                String column = DataScopeUtils.getColumn(dataScope.deptAlias(), dataScope.deptField());
                // 生成需要查询的部门sql
                Set<String> sqlSet = new HashSet<>();
                for (DataScopeEnum dataScopeEnum : loginInfo.getRoleDataScopes()) {
                    sqlSet.addAll(DataScopeUtils.getDeptInByDataScope(userId, dataScopeEnum, loginInfo.getDeptAncestors()));
                }
                // 构建动态查询sql
                yield StrUtil.format(DATA_SCOPE_SQL_MAP.get(dataScope.type()), column, StrUtil.join(" OR ", sqlSet));
            }
        };
    }

    /**
     * 根据权限类型，获取部门的sql
     *
     * @param userId
     * @param dataScopeEnum
     * @param deptAncestors
     * @return
     */
    private static Set<String> getDeptInByDataScope(Long userId, DataScopeEnum dataScopeEnum, Set<String> deptAncestors) {
        Set<String> sqlSet = new HashSet<>(deptAncestors.size());
        for (String deptAncestor : deptAncestors) {
            var where = switch (dataScopeEnum) {
                case DEPT_AND_CHILDREN -> StrUtil.format(" ancestors like '{}%' ", deptAncestor);
                case DEPT -> StrUtil.format(" ancestors = '{}' ", deptAncestor);
                case SELF -> StrUtil.format(" create_by = {} ", userId);
            };
            sqlSet.add(where);
        }

        return sqlSet;
    }

    /**
     * 生成对应的column
     *
     * @param alias
     * @param field
     * @return
     */
    private static String getColumn(String alias, String field) {
        AssertUtils.notBlank(field, "field不能为空");
        return StrUtil.isBlank(alias) ? field : alias + "." + field;
    }

}
