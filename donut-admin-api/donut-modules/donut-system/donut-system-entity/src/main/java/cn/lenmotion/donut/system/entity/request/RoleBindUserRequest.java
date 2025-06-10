package cn.lenmotion.donut.system.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "角色绑定用户请求")
public class RoleBindUserRequest {

    @Schema(description = "角色id")
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @Schema(description = "用户id")
    @NotEmpty(message = "用户id不能为空")
    private List<Long> userIds;

}
