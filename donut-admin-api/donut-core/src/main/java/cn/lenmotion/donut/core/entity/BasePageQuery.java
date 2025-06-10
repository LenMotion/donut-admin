package cn.lenmotion.donut.core.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "通用分页查询参数")
@EqualsAndHashCode(callSuper = true)
public class BasePageQuery extends BaseQuery {

    @Schema(description = "当前页", defaultValue = "1")
    private Long pageNum = 1L;

    @Schema(description = "每页数量", defaultValue = "10")
    private Long pageSize = 10L;

    /**
     * 转成分页对象
     *
     * @param <T>
     * @return
     */
    public <T> IPage<T> toPage() {
        return new Page<>(pageNum, pageSize);
    }

}
