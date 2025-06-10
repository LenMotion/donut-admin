package cn.lenmotion.donut.system.entity.query;

import cn.lenmotion.donut.core.entity.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "租户表查询")
public class SysTenantQuery extends BasePageQuery {

    @Schema(description = "租户名称")
    private String name;

    @Schema(description = "所属集团")
    private String groupName;

}
