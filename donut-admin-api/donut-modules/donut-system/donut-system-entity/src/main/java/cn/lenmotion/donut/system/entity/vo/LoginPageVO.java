package cn.lenmotion.donut.system.entity.vo;

import cn.lenmotion.donut.core.constants.BaseConstants;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;
import org.dromara.core.trans.vo.TransPojo;

/**
 * @author lenmotion
 */
@Data
public class LoginPageVO implements TransPojo {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "系统名称")
    private String name;

    @Schema(description = "系统标题")
    private String title;

    @Schema(description = "系统logo")
    @Trans(type = TransType.AUTO_TRANS, key = BaseConstants.STORAGE_NAMESPACE, ref = "logoUrl")
    private String logo;

    @Schema(description = "系统logo地址")
    private String logoUrl;

    @Schema(description = "系统描述")
    private String description;

}
