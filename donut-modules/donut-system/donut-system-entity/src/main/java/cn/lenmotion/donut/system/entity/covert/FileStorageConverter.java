package cn.lenmotion.donut.system.entity.covert;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.lenmotion.donut.system.entity.po.SysFileStorage;
import cn.lenmotion.donut.system.entity.vo.FileUploadVO;
import com.alibaba.fastjson.JSON;
import org.dromara.x.file.storage.core.FileInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * @author lenmotion
 */
@Mapper(imports = {JSON.class, Dict.class})
public interface FileStorageConverter {

    FileStorageConverter INSTANCE = Mappers.getMapper(FileStorageConverter.class);

    @Mappings({
            @Mapping(target = "attr", expression = "java(JSON.toJSONString(fileInfo.getAttr()))")
    })
    SysFileStorage toModel(FileInfo fileInfo);

    @Mappings({
            @Mapping(target = "attr", expression = "java(JSON.parseObject(storage.getAttr(), Dict.class))")
    })
    FileInfo toFileInfo(SysFileStorage storage);

    FileUploadVO toUploadVO(FileInfo fileInfo);

    /**
     * 将指定值转换成 json 字符串
     */
    static String valueToJson(Map<String, String> value) {
        return JSON.toJSONString(value);
    }

    static Map<String, String> jsonStrToMap(String str) {
        if (StrUtil.isBlank(str)) {
            return null;
        }
        return JSON.parseObject(str, Map.class);
    }

    static LocalDateTime dateToLocal(Date date) {
        return Optional.ofNullable(date).map(DateUtil::formatDateTime).map(DateUtil::parseLocalDateTime).orElse(null);
    }

    static Date localToDate(LocalDateTime dateTime) {
        return Optional.ofNullable(dateTime).map(DateUtil::formatLocalDateTime).map(DateUtil::parseDateTime).orElse(null);
    }

}
