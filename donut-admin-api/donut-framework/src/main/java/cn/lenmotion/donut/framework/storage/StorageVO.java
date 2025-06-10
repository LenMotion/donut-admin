package cn.lenmotion.donut.framework.storage;

import lombok.Data;
import org.dromara.core.trans.vo.VO;

/**
 * @author lenmotion
 */
@Data
public class StorageVO implements VO {

    private String id;

    private String url;

}
