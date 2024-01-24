package cn.lenmotion.donut.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

/**
 * @author LenMotion
 */
@Data
@Schema(description = "角色菜单ID")
public class RoleMenuIdVO {

    @Schema(description = "全选菜单ID")
    private Set<Long> menuIds;

    @Schema(description = "半选菜单ID")
    private Set<Long> halfMenuIds;

}
