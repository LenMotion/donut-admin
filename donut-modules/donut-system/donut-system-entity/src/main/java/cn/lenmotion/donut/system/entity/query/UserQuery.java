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
@Schema(description = "用户查询对象")
public class UserQuery extends BasePageQuery {

    @Schema(description = "部门ID")
    private Long deptId;

    @Schema(description = "用户编号")
    private String userCode;

    @Schema(description = "用户账号")
    private String username;

}
