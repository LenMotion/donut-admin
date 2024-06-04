package cn.lenmotion.donut.system.remote;

import org.dromara.x.file.storage.core.FileInfo;

import java.util.List;
import java.util.Map;

/**
 * @author lenmotion
 */
public interface SysFileStorageRemoteService {

    /**
     * 根据url查询对应的文件信息
     * @param urlList
     * @return
     */
    Map<String, FileInfo> getMapByUrlList(List<String> urlList);

}
