package cn.lenmotion.donut.framework.excel;

import java.util.List;

/**
 * @author lenmotion
 */
@FunctionalInterface
public interface ExcelDataFunc<T> {

    List<T> apply();

}
