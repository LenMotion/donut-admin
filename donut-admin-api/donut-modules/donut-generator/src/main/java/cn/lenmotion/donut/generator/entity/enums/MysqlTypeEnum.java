package cn.lenmotion.donut.generator.entity.enums;

import cn.lenmotion.donut.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author lenmotion
 */
@Getter
@AllArgsConstructor
public enum MysqlTypeEnum implements BaseEnum<String> {
    VARCHAR("String", String.class.getName()),
    CHAR("String", String.class.getName()),
    TEXT("String", String.class.getName()),
    LONGTEXT("String", String.class.getName()),
    BLOB("String", String.class.getName()),
    INT("Integer", Integer.class.getName()),
    SMALLINT("Integer", Integer.class.getName()),
    MEDIUMINT("Integer", Integer.class.getName()),
    TINYINT("Integer", Integer.class.getName()),
    BIGINT("Long", Long.class.getName()),
    DECIMAL("BigDecimal", BigDecimal.class.getName()),
    FLOAT("Float", Float.class.getName()),
    DOUBLE("Double", Double.class.getName()),
    DATE("LocalDate", LocalDate.class.getName()),
    TIME("LocalTime", LocalTime.class.getName()),
    DATETIME("LocalDateTime", LocalDateTime.class.getName()),
    BOOLEAN("Boolean", Boolean.class.getName()),
    BIT("Boolean", Boolean.class.getName()),
    ;

    /**
     * 编码
     */
    private final String code;
    /**
     * 备注
     */
    private final String remark;

    public static MysqlTypeEnum getByName(String typeName) {
        for (MysqlTypeEnum value : MysqlTypeEnum.values()) {
            if (value.name().equals(typeName)) {
                return value;
            }
        }

        return MysqlTypeEnum.VARCHAR;
    }
}
