package cn.lenmotion.donut.system.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.system.entity.converter.FileStorageConverter;
import cn.lenmotion.donut.system.entity.po.SysFileStorage;
import cn.lenmotion.donut.system.entity.query.FileStorageQuery;
import cn.lenmotion.donut.system.mapper.SysFileStorageMapper;
import cn.lenmotion.donut.system.service.SysFileStorageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.dromara.x.file.storage.core.upload.FilePartInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lenmotion
 */
@Service
public class SysFileStorageServiceImpl extends ServiceImpl<SysFileStorageMapper, SysFileStorage> implements SysFileStorageService, FileRecorder {

    @Override
    public IPage<SysFileStorage> selectPage(FileStorageQuery query) {
        var queryWrapper = Wrappers.<SysFileStorage>lambdaQuery();
        queryWrapper.like(StrUtil.isNotBlank(query.getOriginalFilename()), SysFileStorage::getOriginalFilename, query.getOriginalFilename())
                .orderByDesc(SysFileStorage::getCreateTime);
        return this.page(query.toPage(), queryWrapper);
    }

    @Override
    public boolean save(FileInfo fileInfo) {
        fileInfo.setId(IdUtil.getSnowflakeNextIdStr());
        var fileStorage = FileStorageConverter.INSTANCE.toModel(fileInfo);
        return this.save(fileStorage);
    }

    @Override
    public void update(FileInfo fileInfo) {
        var fileStorage = FileStorageConverter.INSTANCE.toModel(fileInfo);
        this.updateById(fileStorage);
    }

    @Override
    public FileInfo getByUrl(String url) {
        LambdaQueryWrapper<SysFileStorage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysFileStorage::getUrl, url)
                .last(BaseConstants.LIMIT_1);
        return FileStorageConverter.INSTANCE.toFileInfo(this.getOne(queryWrapper));
    }

    @Override
    public boolean delete(String url) {
        LambdaQueryWrapper<SysFileStorage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysFileStorage::getUrl, url);
        return remove(queryWrapper);
    }

    @Override
    public void saveFilePart(FilePartInfo filePartInfo) {
        log.warn("file part storage is not implemented");
    }

    @Override
    public void deleteFilePartByUploadId(String s) {
        log.warn("delete file part storage is not implemented");
    }
}
