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
import java.util.stream.Collectors;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class SysFileStorageRemoteServiceImpl implements SysFileStorageRemoteService {

    private final SysFileStorageService fileStorageService;

    @Override
    public Map<String, FileInfo> getMapByUrlList(List<String> urlList) {
        if (CollUtil.isEmpty(urlList)) {
            return Map.of();
        }

        LambdaQueryWrapper<SysFileStorage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysFileStorage::getUrl, urlList);

        var list = FileStorageConverter.INSTANCE.toFileInfoList(fileStorageService.list(queryWrapper));
        return list.stream().collect(Collectors.toMap(FileInfo::getUrl, e -> e, (k1, k2) -> k1));
    }

}
