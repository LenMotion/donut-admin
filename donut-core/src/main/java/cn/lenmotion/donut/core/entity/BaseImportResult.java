package cn.lenmotion.donut.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lenmotion
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "导入结果")
public class BaseImportResult {

    @Schema(description = "导入总数")
    private Integer total;

    @Schema(description = "成功数量")
    private Integer success;

    @Schema(description = "失败数量")
    private Integer failed;

    @Schema(description = "失败信息")
    private List<String> failedMessage;

}
