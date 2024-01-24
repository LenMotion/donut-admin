package cn.lenmotion.donut.monitor.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @author lenmotion
 */
@Data
@Builder
@Schema(description = "jvm信息")
public class MonitorJvmInfoVo {

    @Schema(description = "jdk版本")
    private String jdkVersion;

    @Schema(description = "jdk供应商")
    private String vendor;

    @Schema(description = "jdk名称")
    private String jdkName;

    @Schema(description = "jdk安装路径")
    private String jdkHome;

    @Schema(description = "jvm最大内存")
    private long maxMemory;

    @Schema(description = "jvm可用内存")
    private long useMemory;

    @Schema(description = "项目根路径")
    private String projectHome;

}
