package cn.lenmotion.donut.system.entity.request;

import cn.lenmotion.donut.core.entity.BasePo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "公告保存对象")
@EqualsAndHashCode(callSuper = true)
public class SysNoticeRequest extends BasePo {

    @Schema(description = "公告标题")
    @NotBlank(message = "公告标题不能为空")
    private String noticeTitle;

    @Schema(description = "公告类型（1通知 2公告）")
    @NotBlank(message = "公告类型不能为空")
    private String noticeType;

    @Schema(description = "公告发送类型（0全体人员 1本部门人员 2本部门及以下部门 3特定人员）")
    @NotBlank(message = "公告发送范围不能为空")
    private String noticeSendType;

    @Schema(description = "公告内容")
    private String noticeContent;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "用户ID集合")
    private List<Long> userIds;

    @Schema(description = "部门ID集合")
    private List<Long> deptIds;

}
