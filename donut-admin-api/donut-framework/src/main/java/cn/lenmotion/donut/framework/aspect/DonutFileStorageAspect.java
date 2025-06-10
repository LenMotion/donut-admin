package cn.lenmotion.donut.framework.aspect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.lenmotion.donut.core.exception.BusinessException;
import cn.lenmotion.donut.framework.config.ProjectProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.UploadPretreatment;
import org.dromara.x.file.storage.core.aspect.FileStorageAspect;
import org.dromara.x.file.storage.core.aspect.UploadAspectChain;
import org.dromara.x.file.storage.core.platform.FileStorage;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Locale;

/**
 * @author lenmotion
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DonutFileStorageAspect implements FileStorageAspect {

    private final ProjectProperties projectProperties;

    /**
     * 上传，成功返回文件信息，失败返回 null
     */
    @Override
    public FileInfo uploadAround(UploadAspectChain chain, FileInfo fileInfo, UploadPretreatment pre, FileStorage fileStorage, FileRecorder fileRecorder) {
        log.info("上传文件 before -> {}", fileInfo);

        if (!CollUtil.contains(projectProperties.getFileExt(), fileInfo.getExt().toLowerCase(Locale.ROOT))) {
            throw new BusinessException("当前文件类型不允许上传");
        }

        String path = "";
        if (StrUtil.isNotBlank(fileInfo.getPath())) {
            path = fileInfo.getPath();
        }
        fileInfo.setPath(path + DateUtil.formatDate(new Date()) + "/");
        fileInfo = chain.next(fileInfo, pre, fileStorage, fileRecorder);
        log.info("上传文件 after -> {}", fileInfo);
        return fileInfo;
    }

}
