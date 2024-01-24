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
@Schema(description = "登录日志查询参数")
public class LoginLogQuery extends BasePageQuery {

    @Schema(description = "用户Id")
    private Long userId;

    @Schema(description = "用户账号")
    private String username;

    @Schema(description = "登录IP地址")
    private String ip;

}
