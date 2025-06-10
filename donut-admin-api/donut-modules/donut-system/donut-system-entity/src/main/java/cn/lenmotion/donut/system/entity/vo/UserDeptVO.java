package cn.lenmotion.donut.system.entity.vo;

import cn.lenmotion.donut.core.entity.BasePo;
import cn.lenmotion.donut.system.entity.po.SysPost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author LenMotion
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDeptVO extends BasePo {

    @Schema(description = "部门ID")
    private Long deptId;

    @Schema(description = "部门名称")
    private String deptName;

    @Schema(description = "岗位ID")
    private Long postId;

    @Schema(description = "部门名称")
    private String postName;

    @Schema(description = "岗位选项")
    private List<SysPost> postOptions;

}
