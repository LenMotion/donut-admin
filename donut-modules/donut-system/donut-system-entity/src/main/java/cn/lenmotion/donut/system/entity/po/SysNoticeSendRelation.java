package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.entity.BasePo;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author lenmotion
 * 通知公告发送关联表
 */
@Schema(description = "通知公告发送关联表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_notice_send_relation")
public class SysNoticeSendRelation extends BasePo {
    /**
     * 通知id
     */
    @TableField(value = "notice_id")
    @Schema(description = "通知id")
    private Long noticeId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 部门id
     */
    @TableField(value = "dept_id")
    @Schema(description = "部门id")
    private Long deptId;

    /**
     * 部门层级
     */
    @TableField(value = "dept_ancestors")
    @Schema(description = "部门层级")
    private String deptAncestors;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private Date createTime;
}