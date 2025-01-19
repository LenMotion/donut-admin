package cn.lenmotion.donut.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.core.enums.BaseStatusEnum;
import cn.lenmotion.donut.core.service.impl.DonutServiceImpl;
import cn.lenmotion.donut.system.entity.po.SysDictType;
import cn.lenmotion.donut.system.entity.query.DictTypeQuery;
import cn.lenmotion.donut.system.mapper.SysDictTypeMapper;
import cn.lenmotion.donut.system.service.SysDictDataService;
import cn.lenmotion.donut.system.service.SysDictTypeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.trans.service.impl.DictionaryTransService;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

/**
 * @author lenmotion
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends DonutServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    private final DictionaryTransService dictionaryTransService;
    private final SysDictDataService dictDataService;
    private final TaskExecutor taskExecutor;

    @PostConstruct
    public void init() {
        try {
            log.info("初始化字典缓存...");
            taskExecutor.execute(() -> {
                dictionaryTransService.makeUseRedis();
                // 遍历字典类型，刷新缓存
                LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SysDictType::getStatus, BaseStatusEnum.ENABLED.getCode());
                dictDataService.refreshCache(list(queryWrapper).stream().map(SysDictType::getDictKey).toArray(String[]::new));
            });
        } catch (Exception e) {
            log.info("初始化easy-trans失败", e);
        }
    }

    @PreDestroy
    public  void destroy() {
        log.info("开始清除easy-trans redis缓存...");
        list().forEach(dictType -> dictionaryTransService.removeDictGroupAndNoticeOtherService(dictType.getDictKey()));
    }

    @Override
    public IPage<SysDictType> selectDictTypePage(DictTypeQuery query) {
        // 将query转成lambdaQuery的查询条件
        LambdaQueryWrapper<SysDictType> queryWrapper = Wrappers.lambdaQuery(SysDictType.class)
                .eq(StrUtil.isNotBlank(query.getStatus()), SysDictType::getStatus, query.getStatus())
                .like(StrUtil.isNotBlank(query.getDictName()), SysDictType::getDictName, query.getDictName())
                .eq(StrUtil.isNotBlank(query.getDictKey()), SysDictType::getDictKey, query.getDictKey())
                .eq(StrUtil.isNotBlank(query.getDictType()), SysDictType::getDictType, query.getDictType())
                .orderByDesc(SysDictType::getId);
        return super.page(query.toPage(), queryWrapper);
    }

    @Override
    public boolean saveOrUpdate(SysDictType entity) {
        super.validColumnUnique(entity, SysDictType::getDictKey, "字典key");
        // 默认停用
        if (ObjectUtil.isNull(entity.getId())) {
            entity.setStatus(BaseStatusEnum.DISABLE.getCode());
        } else {
            entity.setStatus(null);
        }
        return super.saveOrUpdate(entity);
    }

    @Override
    public boolean updateStatus(BaseUpdateStatus request) {
        boolean result = super.updateStatus(request);
        // 更新成功后，刷新字典的缓存
        if (result) {
            var dictType = this.getById(request.getId());
            dictDataService.refreshCache(dictType.getDictKey());
        }
        return result;
    }
}
