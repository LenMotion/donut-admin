package cn.lenmotion.donut.monitor.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @author lenmotion
 */
@Data
@Builder
@Schema(description = "系统信息")
public class MonitorSystemInfoVO {

    @Schema(description = "CPU核心数")
    private Integer cpuNum;

    @Schema(description = "CPU总的使用率")
    private double toTal;

    @Schema(description = "CPU当前空闲率")
    private double free;

    @Schema(description = "CPU型号")
    private String cpuModel;

    @Schema(description = "制造商名称")
    private String manufacturer;

    @Schema(description = "系统")
    private String os;

    @Schema(description = "系统架构")
    private String osArch;

    @Schema(description = "版本")
    private String version;

    @Schema(description = "系统启动时间")
    private long systemBootTime;

    @Schema(description = "IP地址")
    private String ip;

}
