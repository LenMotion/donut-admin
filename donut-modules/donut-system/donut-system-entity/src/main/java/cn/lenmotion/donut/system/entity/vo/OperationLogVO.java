package cn.lenmotion.donut.system.entity.vo;

import cn.lenmotion.donut.core.constants.DictKeyConstants;
import cn.lenmotion.donut.core.entity.BasePo;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author lenmotion
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "操作日志")
public class OperationLogVO extends BasePo {

    @Schema(description = "操作人员")
    private Long userId;

    @Schema(description = "登录名")
    private String username;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "模块标题")
    private String title;

    @Schema(description = "方法名称")
    private String method;

    @Schema(description = "请求方式")
    private String requestMethod;

    @Schema(description = "请求URL")
    private String url;

    @Schema(description = "ip地址")
    private String ip;

    @Schema(description = "请求参数")
    private String params;

    @Schema(description = "返回参数")
    private String jsonResult;

    @Schema(description = "操作状态（0正常 1异常）")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_OPERATION_STATUS)
    private String status;

    @Schema(description = "错误消息")
    private String errorMsg;

    @Schema(description = "操作时间")
    private LocalDateTime createTime;

}
