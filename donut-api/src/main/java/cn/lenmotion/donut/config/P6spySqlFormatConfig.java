package cn.lenmotion.donut.config;

import cn.hutool.core.util.StrUtil;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * @author LenMotion
 */
public class P6spySqlFormatConfig implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return StrUtil.isNotBlank(sql) ? "耗时 " + elapsed + " ms | SQL 语句：" + sql.replaceAll("[\\s]+", StrUtil.SPACE) + ";" : "";
    }

}
