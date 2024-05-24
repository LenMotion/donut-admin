package cn.lenmotion.donut.system.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.core.enums.BaseStatusEnum;
import cn.lenmotion.donut.core.service.impl.DonutServiceImpl;
import cn.lenmotion.donut.system.entity.po.SysDictData;
import cn.lenmotion.donut.system.entity.query.DictDataQuery;
import cn.lenmotion.donut.system.mapper.SysDictDataMapper;
import cn.lenmotion.donut.system.service.SysDictDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.fhs.trans.service.impl.DictionaryTransService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class SysDictDataServiceImpl extends DonutServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    private final DictionaryTransService dictionaryTransService;

    @Override
    public String getLabelByValue(String dictKey, String dictValue) {
        return this.getByCondition(dictKey, dictValue, SysDictData::getDictValue, SysDictData::getDictLabel);
    }

    @Override
    public String getValueByLabel(String dictKey, String dictLabel) {
        return this.getByCondition(dictKey, dictLabel, SysDictData::getDictLabel, SysDictData::getDictValue);
    }

    /**
     * 根据条件获取
     */
    private String getByCondition(String dictKey,
                                  String condition,
                                  SFunction<SysDictData, String> conditionFun,
                                  SFunction<SysDictData, String> resultFunc) {
        if (!StrUtil.isAllNotBlank(dictKey, condition)) {
            return null;
        }
        LambdaQueryWrapper<SysDictData> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysDictData::getDictKey, dictKey)
                .eq(conditionFun, condition)
                .last(BaseConstants.LIMIT_1);
        return Optional.ofNullable(getOne(queryWrapper)).map(resultFunc).orElse(null);
    }

    @Override
    public IPage<SysDictData> selectDictDataList(DictDataQuery query) {
        // 将query转成lambdaQuery的查询条件
        LambdaQueryWrapper<SysDictData> queryWrapper = Wrappers.lambdaQuery(SysDictData.class)
                .eq(StrUtil.isNotBlank(query.getStatus()), SysDictData::getStatus, query.getStatus())
                .like(StrUtil.isNotBlank(query.getDictLabel()), SysDictData::getDictLabel, query.getDictLabel())
                .like(StrUtil.isNotBlank(query.getDictValue()), SysDictData::getDictValue, query.getDictValue())
                .eq(StrUtil.isNotBlank(query.getDictKey()), SysDictData::getDictKey, query.getDictKey())
                .orderByAsc(SysDictData::getDictSort);
        return super.page(query.toPage(), queryWrapper);
    }

    @Override
    public boolean saveOrUpdate(SysDictData entity) {
        super.validColumnUnique(entity, SysDictData::getDictLabel, "字典标签", SysDictData::getDictKey);
        super.validColumnUnique(entity, SysDictData::getDictValue, "字典Key", SysDictData::getDictKey);
        // 默认停用
        if (ObjectUtil.isNull(entity.getId())) {
            entity.setStatus(BaseStatusEnum.DISABLE.getCode());
        } else {
            entity.setStatus(null);
        }
        return super.saveOrUpdate(entity);
    }

    @Override
    public void refreshCache(String... dictKeys) {
        if (ArrayUtil.isEmpty(dictKeys)) {
            return;
        }
        List<SysDictData> dataList = getBaseMapper().getEnableByDictKey(List.of(dictKeys));

        Map<String, List<SysDictData>> listMap = dataList.stream().collect(Collectors.groupingBy(SysDictData::getDictKey));

        listMap.forEach((dictKey, list) -> {
            // 将查询结果封装成map
            Map<String, String> dictDataMap = list.stream()
                    .collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getDictLabel));
            // 刷新缓存
            dictionaryTransService.refreshCache(dictKey, dictDataMap);
        });
    }

    @Override
    public boolean updateStatus(BaseUpdateStatus request) {
        boolean result = super.updateStatus(request);
        // 更新成功后，刷新字典的缓存
        if (result) {
            var dictData = this.getById(request.getId());
            this.refreshCache(dictData.getDictKey());
        }
        return result;
    }

}
