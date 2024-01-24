package cn.lenmotion.donut.monitor.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "服务器信息")
public class ServerVO {

    @Schema(description = "文件存储信息")
    private List<MonitorFileStoreVO> fileStoreList;

    @Schema(description = "JVM信息")
    private MonitorJvmInfoVo jvmInfo;

    @Schema(description = "系统信息")
    private MonitorSystemInfoVO systemInfo;

}
