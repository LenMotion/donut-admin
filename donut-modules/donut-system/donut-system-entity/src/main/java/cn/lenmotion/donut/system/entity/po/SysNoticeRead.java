package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.entity.BasePo;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author lenmotion
 * 通知公告阅读表
 */
@Schema(description = "通知公告阅读表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_notice_read")
public class SysNoticeRead extends BasePo {
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 通知id
     */
    @TableField(value = "notice_id")
    @Schema(description = "通知id")
    private Long noticeId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}