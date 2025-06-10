package cn.lenmotion.donut.core.enums;

/**
 * @author lenmotion
 */
public interface BaseEnum<T> {

    /**
     * 编码
     * @return
     */
    T getCode();

    /**
     * 备注
     * @return
     */
    String getRemark();

}
