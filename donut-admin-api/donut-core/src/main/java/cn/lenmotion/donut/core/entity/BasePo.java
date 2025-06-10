package cn.lenmotion.donut.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.dromara.core.trans.vo.TransPojo;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LenMotion
 */
@Data
@Schema(description = "基础实体")
public class BasePo implements TransPojo, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(name = "id", description = "主键")
    private Long id;

}
