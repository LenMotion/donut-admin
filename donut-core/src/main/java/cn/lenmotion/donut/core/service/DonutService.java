package cn.lenmotion.donut.core.service;

import cn.lenmotion.donut.core.entity.BasePo;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 本类提供一些基础的公有方法，供所有Service继承使用。
 * @author lenmotion
 */
public interface DonutService<T extends BasePo> extends IService<T> {

    String BASE_STATUS_COLUMN = "`status`";

    /**
     * 更新状态
     * @param request
     * @return
     */
    boolean updateStatus(BaseUpdateStatus request);

}
