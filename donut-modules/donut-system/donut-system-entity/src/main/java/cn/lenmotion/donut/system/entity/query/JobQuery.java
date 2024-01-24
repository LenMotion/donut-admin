package cn.lenmotion.donut.system.entity.query;

import cn.lenmotion.donut.core.entity.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LenMotion
 */
@Data
@Schema(description = "任务查询")
@EqualsAndHashCode(callSuper = true)
public class JobQuery extends BasePageQuery {

    @Schema(description = "任务名称")
    private String name;

    @Schema(description = "任务执行器")
    private String taskClass;

}
