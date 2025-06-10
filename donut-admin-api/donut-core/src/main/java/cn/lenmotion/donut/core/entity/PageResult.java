package cn.lenmotion.donut.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author lenmotion
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@Schema(description = "分页结果")
public class PageResult<T> implements Serializable {

    /**
     * 当前页
     */
    @Schema(description = "当前页")
    private Integer pageNum;

    /**
     * 每页数量
     */
    @Schema(description = "每页数量")
    private Integer pageSize;

    /**
     * 总数
     */
    @Schema(description = "总数")
    private Integer total;

    /**
     * 总页数
     */
    @Schema(description = "总页数")
    private Integer pages;

    /**
     * 返回结果
     */
    @Schema(description = "返回结果")
    private List<T> items;

    /**
     * 分页扩展响应信息，有时候分页的时候还需要返回其他的信息，所以有了这个字段
     */
    @Schema(description = "扩展信息")
    private Map<String, Object> extension;

}
