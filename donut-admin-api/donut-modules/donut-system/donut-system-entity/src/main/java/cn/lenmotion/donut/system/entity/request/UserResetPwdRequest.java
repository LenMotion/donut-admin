package cn.lenmotion.donut.system.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "重置密码请求")
public class UserResetPwdRequest {

    @Schema(description = "用户id")
    private List<Long> userIds;

}
