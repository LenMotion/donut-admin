package cn.lenmotion.donut.monitor.service;

import cn.hutool.system.SystemUtil;
import cn.hutool.system.oshi.OshiUtil;
import cn.lenmotion.donut.monitor.entity.*;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Service;
import oshi.software.os.OSFileStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class MonitorService {

    private final RedissonConnectionFactory connectionFactory;

    public ServerVO getServerInfo() {
        //获取JVM信息
        var jvmInfo = SystemUtil.getJvmInfo();
        //获取操作系统信息
        var osInfo = SystemUtil.getOsInfo();
        //获取主机信息
        var hostInfo = SystemUtil.getHostInfo();
        //获取Java运行时信息
        var javaRuntimeInfo = SystemUtil.getJavaRuntimeInfo();
        //获取运行时信息
        var runtimeInfo = SystemUtil.getRuntimeInfo();
        //获取用户信息
        var userInfo = SystemUtil.getUserInfo();

        //获取文件存储信息
        List<MonitorFileStoreVO> fileStoreList = getMonitorFileStore();

        //构建JVM信息
        var monitorJvmInfoVo = MonitorJvmInfoVo.builder()
                .jdkVersion(jvmInfo.getVersion())
                .vendor(jvmInfo.getVendor())
                .jdkName(jvmInfo.getName())
                .jdkHome(javaRuntimeInfo.getHomeDir())
                .maxMemory(runtimeInfo.getMaxMemory())
                .useMemory(runtimeInfo.getFreeMemory())
                .projectHome(userInfo.getCurrentDir())
                .build();

        //获取CPU信息
        var cpuInfo = OshiUtil.getCpuInfo();
        //获取操作系统
        var os = OshiUtil.getOs();

        //构建系统信息
        var monitorSystemInfoVO = MonitorSystemInfoVO.builder()
                .cpuNum(cpuInfo.getCpuNum())
                .cpuModel(cpuInfo.getCpuModel().split("\n")[0])
                .toTal(cpuInfo.getToTal())
                .free(cpuInfo.getFree())
                .osArch(osInfo.getArch())
                .manufacturer(os.getManufacturer())
                .version(os.getVersionInfo().getVersion())
                .os(osInfo.getName())
                .systemBootTime(os.getSystemBootTime())
                .ip(hostInfo.getAddress())
                .build();

        //构建服务器信息
        ServerVO serverVO = new ServerVO();
        serverVO.setSystemInfo(monitorSystemInfoVO);
        serverVO.setJvmInfo(monitorJvmInfoVo);
        serverVO.setFileStoreList(fileStoreList);
        return serverVO;
    }

    private static List<MonitorFileStoreVO> getMonitorFileStore() {
        //获取文件系统中的文件存储
        List<OSFileStore> fileStores = OshiUtil.getOs().getFileSystem().getFileStores();

        //创建一个MonitorFileStoreVO的列表，长度为文件存储的数量
        List<MonitorFileStoreVO> fileStoreList = new ArrayList<>(fileStores.size());

        //遍历文件存储，创建一个MonitorFileStoreVO对象，并添加到列表中
        for (OSFileStore fileStore : fileStores) {
            MonitorFileStoreVO storeVO = new MonitorFileStoreVO();

            storeVO.setName(fileStore.getName());
            storeVO.setMount(fileStore.getMount());
            storeVO.setFsType(fileStore.getType());
            storeVO.setFreeSpace(fileStore.getFreeSpace());
            storeVO.setUsableSpace(fileStore.getTotalSpace() - fileStore.getFreeSpace());
            storeVO.setTotalSpace(fileStore.getTotalSpace());

            fileStoreList.add(storeVO);
        }
        return fileStoreList;
    }


    public RedisVO getRedisInfo() {
        RedisConnection connection = connectionFactory.getConnection();
        Properties commandStats = connection.commands().info("commandstats");

        List<JSONObject> pieList = new ArrayList<>();
        if (commandStats != null) {
            commandStats.stringPropertyNames().forEach(key -> {
                JSONObject data = new JSONObject();
                String property = commandStats.getProperty(key);
                data.put("name", StringUtils.removeStart(key, "cmdstat_"));
                data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
                pieList.add(data);
            });
        }

        RedisVO infoVo = new RedisVO();
        infoVo.setProperties(connection.commands().info());
        infoVo.setDbSize(connection.commands().dbSize());
        infoVo.setCmdStats(pieList);

        return infoVo;
    }
}
