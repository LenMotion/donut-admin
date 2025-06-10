package cn.lenmotion.donut.system.entity.query;

import cn.lenmotion.donut.core.entity.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "角色关联用户查询")
public class RoleUserQuery extends BasePageQuery {

    @NotNull(message = "角色id不能为空")
    @Schema(description = "角色id")
    private Long roleId;

}
