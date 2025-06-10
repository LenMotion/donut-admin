package cn.lenmotion.donut.system.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "角色请求实体")
public class SysRoleRequest {

    @Schema(name = "id", description = "主键")
    private Long id;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色权限字符串
     */
    @Schema(description = "角色Key")
    @NotBlank(message = "角色Key不能为空")
    private String roleKey;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer roleSort;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    @Schema(description = "数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    private String dataScope;

    /**
     * 角色状态（0正常 1停用）
     */
    @Schema(description = "角色状态（0正常 1停用）")
    private String status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 菜单组
     */
    @Schema(description = "选中菜单组")
    private Long[] menuIds;

    /**
     * 半选中的菜单组
     */
    @Schema(description = "半选中的菜单组")
    private Long[] halfMenuIds;

}
