package cn.lenmotion.donut.framework.config;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.lenmotion.donut.core.annotation.DataScope;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.context.DataScopeContext;
import cn.lenmotion.donut.core.context.TenantContext;
import cn.lenmotion.donut.core.entity.LoginInfo;
import cn.lenmotion.donut.core.enums.DataScopeEnum;
import cn.lenmotion.donut.core.enums.DataScopeTypeEnum;
import cn.lenmotion.donut.core.utils.AssertUtils;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author LenMotion
 */
@Configuration
@RequiredArgsConstructor
public class MybatisPlusConfig {

    private final ProjectProperties projectProperties;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 数据权限插件
        interceptor.addInnerInterceptor(dataPermissionInterceptor());
        // 分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        // 乐观锁插件
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());
        // 阻断插件
        interceptor.addInnerInterceptor(blockAttackInnerInterceptor());
        // 租户插件
        interceptor.addInnerInterceptor(tenantLineInnerInterceptor(projectProperties));

        return interceptor;
    }

    /**
     * 分页插件，自动识别数据库类型 https://baomidou.com/guide/interceptor-pagination.html
     */
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 设置数据库类型为mysql
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInnerInterceptor.setMaxLimit(1000L);
        return paginationInnerInterceptor;
    }

    /**
     * 乐观锁插件 https://baomidou.com/guide/interceptor-optimistic-locker.html
     */
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * 如果是对全表的删除或更新操作，就会终止该操作 https://baomidou.com/guide/interceptor-block-attack.html
     */
    public BlockAttackInnerInterceptor blockAttackInnerInterceptor() {
        return new BlockAttackInnerInterceptor();
    }

    /**
     * 数据权限 的插件
     */
    public DataPermissionInterceptor dataPermissionInterceptor() {
        return new DataPermissionInterceptor(new DonutDataPermissionHandler());
    }

    /**
     * 租户插件
     */
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(ProjectProperties projectProperties) {
        return new TenantLineInnerInterceptor(new DonutTenantHandler(projectProperties));
    }

    @Slf4j
    @Component
    public static class DonutMetaObjectHandler implements MetaObjectHandler {

        private static final String CREATE_TIME = "createTime";
        private static final String UPDATE_TIME = "updateTime";
        private static final String CREATE_BY = "createBy";
        private static final String UPDATE_BY = "updateBy";

        @Override
        public void insertFill(MetaObject metaObject) {
            try {
                Object createTimeValue = metaObject.getValue(CREATE_TIME);
                if (createTimeValue == null) {
                    this.strictInsertFill(metaObject, CREATE_TIME, LocalDateTime.class, LocalDateTime.now());
                }
            } catch (Exception ignored) {
            }
            try {
                Object createByValue = metaObject.getValue(CREATE_BY);
                if (createByValue == null && StpUtil.isLogin()) {
                    this.strictInsertFill(metaObject, CREATE_BY, String.class, StpUtil.getLoginIdAsString());
                }
            } catch (Exception ignored) {
            }
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            try {
                Object updateTimeValue = metaObject.getValue(UPDATE_TIME);
                if (updateTimeValue == null) {
                    this.strictUpdateFill(metaObject, UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
                }
            } catch (Exception ignored) {
            }
            try {
                Object updateByValue = metaObject.getValue(UPDATE_BY);
                if (updateByValue == null && StpUtil.isLogin()) {
                    this.strictUpdateFill(metaObject, UPDATE_BY, String.class, StpUtil.getLoginIdAsString());
                }
            } catch (Exception ignored) {
            }
        }
    }

    @Slf4j
    public static class DonutDataPermissionHandler implements DataPermissionHandler {

        private static final Map<DataScopeTypeEnum, String> DATA_SCOPE_SQL_MAP = new HashMap<>();

        static {
            DATA_SCOPE_SQL_MAP.put(DataScopeTypeEnum.ROLE, " {} in ( select role_id from sys_user_role where user_id = {} )");
            DATA_SCOPE_SQL_MAP.put(DataScopeTypeEnum.MENU, " {} in ( select menu_id from sys_role_menu rm inner join sys_user_role ur on ur.role_id = rm.role_id where ur.user_id = {} )");
            DATA_SCOPE_SQL_MAP.put(DataScopeTypeEnum.DEPT, " {} in ( select id from sys_dept where {} and deleted = 0 )");
        }

        @Override
        public Expression getSqlSegment(Expression where, String mappedStatementId) {
            DataScope dataScope = DataScopeContext.getDataScope();
            if (dataScope == null) {
                return where;
            }

            Long userId = StpUtil.getLoginIdAsLong();
            var loginInfo = (LoginInfo) StpUtil.getSession().get(BaseConstants.SESSION_LOGIN_INFO);
            if (BaseConstants.SUPER_ID.equals(userId) || StrUtil.equalsIgnoreCase(loginInfo.getUsername(), BaseConstants.SUPER_USER)) {
                return where;
            }

            Expression expression = null;
            try {
                expression = CCJSqlParserUtil.parseCondExpression("1=2");
                var sql = switch (dataScope.type()) {
                    case ROLE -> {
                        String column = this.getColumn(dataScope.roleAlias(), dataScope.roleField());
                        yield StrUtil.format(DATA_SCOPE_SQL_MAP.get(dataScope.type()), column, StpUtil.getLoginIdAsLong());
                    }
                    case MENU -> {
                        String column = this.getColumn(dataScope.menuAlias(), dataScope.menuField());
                        yield StrUtil.format(DATA_SCOPE_SQL_MAP.get(dataScope.type()), column, StpUtil.getLoginIdAsLong());
                    }
                    case DEPT -> {
                        // 如果为空则表示没有权限，不允许获取数据
                        if (CollUtil.isEmpty(loginInfo.getRoleDataScopes()) || CollUtil.isEmpty(loginInfo.getDeptAncestors())) {
                            yield "1=2";
                        }
                        // 生成列
                        String column = this.getColumn(dataScope.deptAlias(), dataScope.deptField());
                        // 生成需要查询的部门sql
                        Set<String> sqlSet = new HashSet<>();
                        for (DataScopeEnum dataScopeEnum : loginInfo.getRoleDataScopes()) {
                            sqlSet.addAll(this.getDeptInByDataScope(userId, dataScopeEnum, loginInfo.getDeptAncestors()));
                        }
                        // 构建动态查询sql
                        yield StrUtil.format(DATA_SCOPE_SQL_MAP.get(dataScope.type()), column, StrUtil.join(" OR ", sqlSet));
                    }
                };
                expression = CCJSqlParserUtil.parseCondExpression(sql);
                log.info("DataScopeHandler expression: {}", expression);
            } catch (Exception e) {
                log.error("DataScopeHandler error", e);
            }
            return new AndExpression(where, expression);
        }

        /**
         * 根据权限类型，获取部门的sql
         *
         * @param userId
         * @param dataScopeEnum
         * @param deptAncestors
         * @return
         */
        private Set<String> getDeptInByDataScope(Long userId, DataScopeEnum dataScopeEnum, Set<String> deptAncestors) {
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
        private String getColumn(String alias, String field) {
            AssertUtils.notBlank(field, "field不能为空");
            return StrUtil.isBlank(alias) ? field : alias + "." + field;
        }
    }

    /**
     * 租户处理器
     */
    @AllArgsConstructor
    public static class DonutTenantHandler implements TenantLineHandler {

        private final ProjectProperties projectProperties;

        @Override
        public Expression getTenantId() {
            return Optional.ofNullable(TenantContext.getTenant()).map(LongValue::new).orElse(null);
        }

        @Override
        public boolean ignoreTable(String tableName) {
            return projectProperties.getTenantIgnoreTables().contains(tableName);
        }

    }

}
