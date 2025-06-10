package cn.lenmotion.donut.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "通用查询参数")
public class BaseQuery {

    /**
     * 关键字
     */
    @Schema(description = "关键字")
    private String keyword;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String status;

    /**
     * 创建开始时间
     */
    @Schema(description = "创建开始时间")
    private String createStartTime;

    /**
     * 创建开始时间
     */
    @Schema(description = "创建开始时间")
    private String createEndTime;
}
