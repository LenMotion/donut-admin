package cn.lenmotion.donut.system.entity.vo;

import cn.lenmotion.donut.core.entity.BasePo;
import cn.lenmotion.donut.system.entity.po.SysDept;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;

/**
 * @author lenmotion
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "角色关联的用户")
public class RoleUserVO extends BasePo {

    private Long userId;

    private String username;

    private String nickName;

    @Trans(type = TransType.SIMPLE, fields = "deptName", target = SysDept.class)
    private Long deptId;

    private Boolean hasPermission;

}
