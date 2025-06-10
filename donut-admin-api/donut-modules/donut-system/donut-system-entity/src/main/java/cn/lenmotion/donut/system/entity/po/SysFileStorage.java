package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.constants.BaseConstants;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;
import org.dromara.core.trans.vo.TransPojo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lenmotion
 */
@Data
@NoArgsConstructor
@TableName("sys_file_storage")
public class SysFileStorage implements TransPojo, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "租户ID")
    private Long tenantId;

    /** 文件id */
    @TableId(type = IdType.INPUT)
    @Trans(type = TransType.AUTO_TRANS, key = BaseConstants.STORAGE_NAMESPACE, alias = "preview")
    private String id;

    /** 文件访问地址 */
    private String url;

    /** 文件大小，单位字节 */
    private Long size;

    /** 文件名称 */
    private String filename;

    /** 原始文件名 */
    private String originalFilename;

    /** 基础存储路径 */
    private String basePath;

    /** 存储路径 */
    private String path;

    /** 文件扩展名 */
    private String ext;

    /** MIME类型 */
    private String contentType;

    /** 存储平台 */
    private String platform;

    /** 缩略图访问路径 */
    private String thUrl;

    /** 缩略图名称 */
    private String thFilename;

    /** 缩略图大小，单位字节 */
    private Long thSize;

    /** 缩略图MIME类型 */
    private String thContentType;

    /** 文件所属对象id */
    private String objectId;

    /** 文件所属对象类型，例如用户头像，评价图片 */
    private String objectType;

    /** 文件元数据 */
    private String metadata;

    /** 文件用户元数据 */
    private String userMetadata;

    /** 缩略图元数据 */
    private String thMetadata;

    /** 缩略图用户元数据 */
    private String thUserMetadata;

    /** 附加属性 */
    private String attr;

//    /** 文件ACL */
//    private String fileAcl;
//
//    /** 缩略图文件ACL */
//    private String thFileAcl;

    /** 创建时间 */
    private LocalDateTime createTime;

    private Integer deleted;

}
