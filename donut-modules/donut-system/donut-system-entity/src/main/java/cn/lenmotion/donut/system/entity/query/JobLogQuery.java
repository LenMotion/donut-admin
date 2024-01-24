package cn.lenmotion.donut.system.entity.query;

import cn.lenmotion.donut.core.entity.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LenMotion
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class JobLogQuery extends BasePageQuery {

    @Schema(description = "任务id")
    private Long jobId;

    @Schema(description = "0自动执行 1手动执行")
    private String type;

    @Schema(description = "0执行中 1执行成功，2执行失败")
    private String status;

}
