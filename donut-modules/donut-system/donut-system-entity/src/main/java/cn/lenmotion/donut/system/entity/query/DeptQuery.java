package cn.lenmotion.donut.system.entity.query;

import cn.lenmotion.donut.core.entity.BaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "部门查询参数")
public class DeptQuery extends BaseQuery {

    @Schema(description = "部门名称")
    private String deptName;

    @Schema(description = "单位编号")
    private String deptCode;

}
