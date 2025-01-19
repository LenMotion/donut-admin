package cn.lenmotion.donut.system.remote;

import org.dromara.x.file.storage.core.FileInfo;

import java.util.List;
import java.util.Map;

/**
 * @author lenmotion
 */
public interface SysFileStorageRemoteService {

    /**
     * 根据ids查询对应的文件信息
     * @param ids
     * @return
     */
    Map<String, FileInfo> getMapByIds(List<String> ids);

    /**
     * 单个 id 查询
     * @param id
     * @return
     */
    FileInfo getById(String id);

}
