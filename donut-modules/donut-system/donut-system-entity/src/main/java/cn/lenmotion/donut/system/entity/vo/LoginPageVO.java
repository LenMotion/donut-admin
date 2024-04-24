package cn.lenmotion.donut.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lenmotion
 */
@Data
public class LoginPageVO {

    @Schema(description = "系统名称")
    private String name;

    @Schema(description = "系统标题")
    private String title;

    @Schema(description = "系统logo")
    private String logo;

    @Schema(description = "系统描述")
    private String description;

}
