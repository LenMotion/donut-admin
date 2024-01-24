package cn.lenmotion.donut.monitor.entity;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Properties;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "Redis信息")
public class RedisVO {

    @Schema(description = "Redis配置")
    private Properties properties;

    @Schema(description = "Redis数据库大小")
    private Long dbSize;

    @Schema(description = "Redis命令统计")
    private List<JSONObject> cmdStats;

}
