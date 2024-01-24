package cn.lenmotion.donut.core.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.entity.BasePo;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.core.exception.BusinessException;
import cn.lenmotion.donut.core.service.DonutService;
import cn.lenmotion.donut.core.utils.AssertUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author lenmotion
 */
public class DonutServiceImpl<M extends BaseMapper<T>, T extends BasePo> extends ServiceImpl<M, T> implements DonutService<T> {

    /**
     * 判断字段是否重复，需要过滤id
     *
     * @param t         对象信息
     * @param column    需要校验的列
     * @param title     提示信息
     * @param eqColumns 额外比较的字段
     */
    @SafeVarargs
    protected final void validColumnUnique(T t, SFunction<T, ?> column, String title, SFunction<T, ?>... eqColumns) {
        Object value = column.apply(t);
        // 获取id信息
        SFunction<T, Long> idFunction = T::getId;
        Long id = (idFunction).apply(t);
        // 查询是否重复
        LambdaQueryWrapper<T> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.setEntityClass(getEntityClass());
        // 设置比较条件
        queryWrapper.eq(column, value)
                .ne(Objects.nonNull(id), idFunction, id)
                .select(idFunction)
                .last(BaseConstants.LIMIT_1);
        // 额外的比较条件
        if (ArrayUtil.isNotEmpty(eqColumns)) {
            for (SFunction<T, ?> eqColumn : eqColumns) {
                queryWrapper.eq(eqColumn, eqColumn.apply(t));
            }
        }
        // 断言查询结果是否为空，不为空则抛出异常
        AssertUtils.isNull(getOne(queryWrapper), "新增 [" + value + "] 失败，" + title + "已存在");
    }

    @Override
    public boolean updateStatus(BaseUpdateStatus request) {
        AssertUtils.notNull(request.getId(), "id不能为空");
        AssertUtils.notBlank(request.getStatus(), "状态不能为空");

        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setEntityClass(getEntityClass());
        updateWrapper.set(BASE_STATUS_COLUMN, request.getStatus())
                .lambda()
                .eq(T::getId, request.getId());
        return update(updateWrapper);
    }

}
