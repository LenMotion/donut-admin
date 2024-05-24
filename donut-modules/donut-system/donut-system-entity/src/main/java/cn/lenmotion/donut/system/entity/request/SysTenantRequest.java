package cn.lenmotion.donut.system.entity.request;

import cn.lenmotion.donut.core.entity.BasePo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "租户表请求对象")
public class SysTenantRequest extends BasePo {

    @Schema(description = "租户名称")
    private String name;

    @Schema(description = "租户描述")
    private String description;

    @Schema(description = "所属集团")
    private String groupName;

    @Schema(description = "新增租户时，默认的权限")
    private Long[] menuIds;

    @Schema(description = "新增租户时，默认的权限，半选")
    private Long[] halfMenuIds;

}
