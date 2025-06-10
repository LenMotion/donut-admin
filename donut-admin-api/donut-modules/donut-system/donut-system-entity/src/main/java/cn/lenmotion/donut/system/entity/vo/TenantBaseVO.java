package cn.lenmotion.donut.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "租户基础信息")
public class TenantBaseVO {

    @Schema(description = "租户ID")
    private Long id;

    @Schema(description = "租户名称")
    private String name;

}
