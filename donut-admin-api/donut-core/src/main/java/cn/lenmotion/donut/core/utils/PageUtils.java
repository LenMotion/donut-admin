package cn.lenmotion.donut.core.utils;

import cn.lenmotion.donut.core.entity.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 分页工具类
 *
 * @author lenmotion
 */
public class PageUtils {

    /**
     * 基础的设置页码，每页数量与总数的方法
     *
     * @param page
     * @param <R>
     * @param <T>
     * @return
     */
    public static <R, T> PageResult<R> getBasePage(IPage<T> page) {
        PageResult<R> pageResult = new PageResult<>();
        pageResult.setPageNum((int) page.getCurrent())
                .setPageSize((int) page.getSize())
                .setPages((int) page.getPages())
                .setTotal((int) page.getTotal());
        return pageResult;
    }

    /**
     * 设置默认的page
     *
     * @param page
     * @param <T>
     * @return
     */
    public static <T> PageResult<T> getPageResult(IPage<T> page) {
        PageResult<T> pageResult = getBasePage(page);
        pageResult.setItems(page.getRecords());
        return pageResult;
    }

    /**
     * 自定义的result结果集
     *
     * @param page
     * @param resultList
     * @param <M>
     * @param <T>
     * @return
     */
    public static <M, T> PageResult<M> getPageResult(IPage<T> page, List<M> resultList) {
        PageResult<M> pageResult = getBasePage(page);
        pageResult.setItems(resultList);
        return pageResult;
    }

}
