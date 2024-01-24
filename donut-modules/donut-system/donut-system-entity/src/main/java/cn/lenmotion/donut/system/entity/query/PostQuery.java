package cn.lenmotion.donut.system.entity.query;

import cn.lenmotion.donut.core.entity.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LenMotion
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "岗位查询")
public class PostQuery extends BasePageQuery {

    @Schema(description = "岗位类型 0全局岗位 1部门岗位")
    private String postType;

    @Schema(description = "部门id")
    private Long deptId;

    @Schema(description = "岗位编码")
    private String postCode;

    @Schema(description = "岗位名称")
    private String postName;

}
