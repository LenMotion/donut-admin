package cn.lenmotion.donut.framework.storage;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.lenmotion.donut.core.constants.BaseConstants;
import com.fhs.core.trans.anno.AutoTrans;
import com.fhs.trans.service.AutoTransable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.platform.FileStorage;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author lenmotion
 */
@Slf4j
@Service
@AutoTrans(namespace = BaseConstants.STORAGE_NAMESPACE, fields = {"url"})
@RequiredArgsConstructor
public class StorageAutoTrans implements AutoTransable<StorageVO> {

    private final FileStorageService fileStorageService;
    private final SaTokenConfig saTokenConfig;

    @Override
    public List<StorageVO> selectByIds(List<?> ids) {
       return ids.stream().map(this::selectById).toList();
    }

    @Override
    public StorageVO selectById(Object primaryValue) {
        StorageVO storageVO = new StorageVO();
        try {
            FileInfo fileInfo = fileStorageService.getFileInfoByUrl(primaryValue.toString());
            if (Objects.isNull(fileInfo)) {
                return storageVO;
            }

            FileStorage fileStorage = fileStorageService.getFileStorage(fileInfo.getPlatform());
            if (Objects.isNull(fileStorage)) {
                return storageVO;
            }

            storageVO.setId(primaryValue.toString());
            if (fileStorage.isSupportPresignedUrl()) {
                storageVO.setUrl(fileStorageService.generatePresignedUrl(fileInfo, DateUtil.offsetMinute(new Date(), 30)));
            } else if (StpUtil.isLogin()) {
                var prefix = StrUtil.isBlank(saTokenConfig.getTokenPrefix()) ? "" : saTokenConfig.getTokenPrefix() + " ";
                var token = StrUtil.format("{}?{}={}{}", fileInfo.getUrl(), saTokenConfig.getTokenName(), prefix, StpUtil.getTokenValue());
                storageVO.setUrl(URLUtil.encodeBlank(token));
            } else {
                storageVO.setUrl(fileInfo.getUrl());
            }
            return storageVO;
        } catch (Exception e) {
            log.error("生成文件访问链接异常: {}", e.getMessage());
            return storageVO;
        }
    }

}
