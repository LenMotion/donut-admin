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
@Schema(description = "用户通知查询")
public class UserNoticeQuery extends BasePageQuery {

    @Schema(description = "通知类型")
    private String noticeType;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "是否已读")
    private Boolean read;

}
