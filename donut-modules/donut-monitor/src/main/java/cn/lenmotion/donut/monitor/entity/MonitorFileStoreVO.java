package cn.lenmotion.donut.monitor.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "监控文件存储VO")
public class MonitorFileStoreVO {

    @Schema(description = "名称")
    private String name;

    @Schema(description = "挂载点")
    private String mount;

    @Schema(description = "文件系统类型")
    private String fsType;

    @Schema(description = "总空间")
    private long totalSpace;

    @Schema(description = "可用空间")
    private long usableSpace;

    @Schema(description = "可用空间")
    private long freeSpace;

}
