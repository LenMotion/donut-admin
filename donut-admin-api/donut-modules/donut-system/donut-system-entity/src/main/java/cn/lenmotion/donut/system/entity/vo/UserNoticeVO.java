package cn.lenmotion.donut.system.entity.vo;

import cn.lenmotion.donut.core.entity.BasePo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author LenMotion
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户公告")
@Data
public class UserNoticeVO extends BasePo {

    @Schema(description = "公告标题")
    private String noticeTitle;

    @Schema(description = "公告内容")
    private String noticeContent;

    @Schema(description = "发布时间")
    private LocalDateTime publishTime;

    @Schema(description = "是否已读 0-未读 1-已读")
    private Boolean isRead;

    @Schema(description = "阅读时间")
    private LocalDateTime readTime;

}
