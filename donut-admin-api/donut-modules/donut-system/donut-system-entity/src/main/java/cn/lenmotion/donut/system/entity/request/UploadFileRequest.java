package cn.lenmotion.donut.system.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "上传文件请求")
public class UploadFileRequest {

    @Schema(description = "是否开放")
    private boolean open = false;

    @Schema(description = "文件")
    @NotNull(message = "文件不能为空")
    private MultipartFile file;

}
