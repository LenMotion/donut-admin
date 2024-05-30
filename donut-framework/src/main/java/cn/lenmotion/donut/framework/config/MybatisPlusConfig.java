package cn.lenmotion.donut.framework.config;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.lenmotion.donut.core.annotation.DataScope;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.context.DataScopeContext;
import cn.lenmotion.donut.core.context.TenantContext;
import cn.lenmotion.donut.core.exception.BusinessException;
import cn.lenmotion.donut.core.utils.DataScopeUtils;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

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

        @Override
        public Expression getSqlSegment(Expression where, String mappedStatementId) {
            DataScope dataScope = DataScopeContext.getDataScope();
            if (dataScope == null) {
                return where;
            }

            Long userId = DataScopeContext.getUserId();
            var loginInfo = DataScopeContext.getLoginInfo();
            if (BaseConstants.SUPER_ID.equals(userId) || StrUtil.equalsIgnoreCase(loginInfo.getUsername(), BaseConstants.SUPER_USER)) {
                return where;
            }

            Expression expression;
            try {
                var sql = DataScopeUtils.getDataScopeSql(userId, loginInfo, dataScope);
                expression = CCJSqlParserUtil.parseCondExpression(sql);
                log.info("DataScopeHandler expression: {}", expression);
            } catch (Exception e) {
                log.error("DataScopeHandler error", e);
                try {
                    expression = CCJSqlParserUtil.parseCondExpression("1=2");
                } catch (JSQLParserException ex) {
                    throw new BusinessException("数据权限错误");
                }
            }
            return new AndExpression(where, expression);
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
