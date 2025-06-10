package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.constants.DictKeyConstants;
import cn.lenmotion.donut.core.entity.BaseCreatePo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;

import java.time.LocalDateTime;

/**
 * @author lenmotion
 * 通知公告表
 */
@Schema(description = "通知公告表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_notice")
public class SysNotice extends BaseCreatePo {

    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 公告标题
     */
    @TableField(value = "notice_title")
    @Schema(description = "公告标题")
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    @TableField(value = "notice_type")
    @Schema(description = "公告类型（1通知 2公告）")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_NOTICE_TYPE)
    private String noticeType;

    /**
     * 公告发送类型（0全体人员 1本部门人员 2本部门及以下部门 3特定人员）
     */
    @TableField(value = "notice_send_type")
    @Schema(description = "公告发送类型（0全体人员 1本部门人员 2本部门及以下部门 3特定人员）")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_NOTICE_SEND_TYPE)
    private String noticeSendType;

    /**
     * 公告内容
     */
    @TableField(value = "notice_content")
    @Schema(description = "公告内容")
    private String noticeContent;

    /**
     * 公告状态（0正常 1禁用）
     */
    @TableField(value = "`status`")
    @Schema(description = "公告状态（0正常 1禁用）")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_STATUS)
    private String status;

    /**
     * 发布时间
     */
    @TableField(value = "publish_time")
    @Schema(description = "发布时间")
    private LocalDateTime publishTime;

    /**
     * 阅读量
     */
    @TableField(value = "read_num")
    @Schema(description = "阅读量")
    private Integer readNum;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @Schema(description = "备注")
    private String remark;
}