package cn.lenmotion.donut.generator.entity.enums;

import cn.lenmotion.donut.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lenmotion
 */
@AllArgsConstructor
@Getter
public enum DatasourceTypeEnum implements BaseEnum<String> {
    MYSQL("mysql",  "mysql", "jdbc:mysql://{}:{}/{}?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&" +
                    "useSSL=true&serverTimezone=GMT%2B8&useInformationSchema=true", "select 1"),
    POSTGRESQL("postgresql",  "postgresql", "jdbc:postgresql://{}:{}/{}", "select 'x'"),
    ;

    /**
     * 编码
     */
    private final String code;

    /**
     * 备注
     */
    private final String remark;

    /**
     * url
     */
    private final String url;

    /**
     * 校验sql
     */
    private final String checkSql;

}
