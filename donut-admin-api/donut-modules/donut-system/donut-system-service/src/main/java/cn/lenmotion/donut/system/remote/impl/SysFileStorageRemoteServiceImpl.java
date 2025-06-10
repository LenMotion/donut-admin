package cn.lenmotion.donut.system.remote.impl;

import cn.hutool.core.collection.CollUtil;
import cn.lenmotion.donut.system.entity.converter.FileStorageConverter;
import cn.lenmotion.donut.system.entity.po.SysFileStorage;
import cn.lenmotion.donut.system.remote.SysFileStorageRemoteService;
import cn.lenmotion.donut.system.service.SysFileStorageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class SysFileStorageRemoteServiceImpl implements SysFileStorageRemoteService {

    private final SysFileStorageService fileStorageService;

    @Override
    public Map<String, FileInfo> getMapByIds(List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Map.of();
        }

        var list = FileStorageConverter.INSTANCE.toFileInfoList(fileStorageService.listByIds(ids));
        return list.stream().collect(Collectors.toMap(FileInfo::getId, Function.identity(), (k1, k2) -> k1));
    }

    @Override
    public FileInfo getById(String id) {
        return FileStorageConverter.INSTANCE.toFileInfo(fileStorageService.getById(id));
    }

}
