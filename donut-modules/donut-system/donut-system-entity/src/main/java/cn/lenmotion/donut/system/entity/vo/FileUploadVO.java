package cn.lenmotion.donut.system.entity.vo;

import cn.lenmotion.donut.core.constants.BaseConstants;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.VO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "文件上传VO")
public class FileUploadVO implements VO {

    @TableId
    @Trans(type = TransType.AUTO_TRANS, key = BaseConstants.STORAGE_NAMESPACE, ref = "url")
    private String uid;

    @Schema(description = "文件访问地址，用于后台保存")
    private String url;

    @Schema(description = "文件实际名称")
    private String name;

    @Schema(description = "文件扩展名")
    private String ext;

    @Schema(description = "文件大小")
    private Long size;

    @Schema(description = "文件类型")
    private String contentType;

}
