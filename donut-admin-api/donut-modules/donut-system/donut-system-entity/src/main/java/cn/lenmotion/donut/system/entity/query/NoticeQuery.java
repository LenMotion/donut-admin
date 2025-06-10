package cn.lenmotion.donut.system.entity.query;

import cn.lenmotion.donut.core.entity.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "公告查询")
@Data
public class NoticeQuery extends BasePageQuery {

    @Schema(description = "标题")
    private String noticeTitle;

    @Schema(description = "类型")
    private Integer noticeType;

}