package cn.lenmotion.donut.system.entity.query;

import cn.lenmotion.donut.core.entity.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "操作日志查询")
public class OperationLogQuery extends BasePageQuery {

    @Schema(description = "操作人员")
    private Long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "模块标题")
    private String title;

    @Schema(description = "请求方式")
    private String requestMethod;

    @Schema(description = "请求URL")
    private String url;

    @Schema(description = "ip地址")
    private String ip;

}
