package cn.lenmotion.donut.framework.excel;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.context.DataScopeContext;
import cn.lenmotion.donut.core.exception.BusinessException;
import cn.lenmotion.donut.core.utils.AssertUtils;
import cn.lenmotion.donut.system.entity.enums.ExportStatusEnum;
import cn.lenmotion.donut.system.entity.po.SysExportLog;
import cn.lenmotion.donut.system.remote.SysExportLogRemoteService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.fhs.core.trans.vo.VO;
import com.fhs.trans.service.impl.TransService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.core.task.TaskExecutor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author lenmotion
 */
@Slf4j
public class ExcelExportClient {

    private final ExcelExportBuilder builder;
    private final FileStorageService fileStorageService;
    private final SysExportLogRemoteService exportLogRemoteService;
    private final TaskExecutor taskExecutor;
    private final ByteArrayOutputStream out;

    private ExcelExportClient(ExcelExportBuilder builder) {
        this.builder = builder;
        this.fileStorageService = SpringUtil.getBean(FileStorageService.class);
        this.exportLogRemoteService = SpringUtil.getBean(SysExportLogRemoteService.class);
        this.taskExecutor = SpringUtil.getBean(TaskExecutor.class);
        this.out = new ByteArrayOutputStream();
    }

    public static ExcelExportBuilder builder() {
        return new ExcelExportBuilder();
    }

    private void doAsyncExport() {
        taskExecutor.execute(this::doExport);
    }

    private String doExport() {
        AssertUtils.isFalse(CollUtil.isEmpty(builder.headList) && builder.clazz == null, "请设置表头或实体类");
        AssertUtils.notBlank(builder.fileName, "请设置文件名");

        // 保存日志
        log.info("开始执行保存日志...");
        exportLogRemoteService.save(builder.exportLog);
        try {
            ExcelWriterBuilder writerBuilder;
            // 判断是自定义头还是实体类
            if (builder.clazz != null) {
                writerBuilder = EasyExcel.write(out, builder.clazz);
            } else {
                writerBuilder = EasyExcel.write(out).head(builder.headList);
            }
            // 设置默认的样式
            writerBuilder.registerWriteHandler(cellStyleStrategy());
            // 设置自定义写入handler
            if (CollUtil.isNotEmpty(builder.writeHandlers)) {
                log.info("设置自定义写入handler");
                builder.writeHandlers.forEach(writerBuilder::registerWriteHandler);
            }
            // 执行写入程序
            log.info("开始执行写入程序...");
            writerBuilder.excelType(ExcelTypeEnum.XLSX).sheet(builder.fileName).doWrite(builder.data);
            // 上传文件到缓存信息
            return this.uploadStream(out, builder.exportLog);
        } catch (Exception e) {
            log.error("导出失败", e);
            builder.exportLog.setStatus(ExportStatusEnum.FAILED.getCode());
            builder.exportLog.setErrorMsg(e.getMessage());
            throw new BusinessException("导出失败");
        } finally {
            builder.exportLog.setExecTime(builder.timer.interval());
            exportLogRemoteService.endExport(builder.exportLog);
            IoUtil.close(out);
        }
    }


    /**
     * 设置内容和header的策略
     */
    private static HorizontalCellStyleStrategy cellStyleStrategy() {
        // 头的策略
        var headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        var headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 10);
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);

        // 内容的策略
        var contentWriteCellStyle = new WriteCellStyle();
        // 背景绿色
        var contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    /**
     * 上传流至文件服务器
     */
    public String uploadStream(ByteArrayOutputStream out, SysExportLog exportLog) {
        ByteArrayInputStream in = null;
        try {
            log.info("开始上传文件...");
            // 生成文件地址，加入随机数，避免重复
            var upFileName = exportLog.getName() + "_" + IdUtil.getSnowflakeNextId() + ExcelTypeEnum.XLSX.getValue();
            // 设置请求头
            Map<String, String> metadata = new HashMap<>();
            var contentDispositionValue = StrUtil.format("attachment; filename={}", URLUtil.encode(upFileName));
            metadata.put("Content-disposition", contentDispositionValue);
            // 上传文件信息
            in = IoUtil.toStream(out);
            var fileInfo = fileStorageService.of(in, upFileName, BaseConstants.EXCEL_CONTEXT_TYPE)
                    .setPath(builder.storagePath)
                    .setMetadata(metadata)
                    .setObjectType("excel")
                    .upload();
            exportLog.setStatus(ExportStatusEnum.FINISHED.getCode());
            exportLog.setFileInfoId(fileInfo.getId());
            exportLog.setUrl(fileInfo.getUrl());
            // 生成访问链接
            return fileStorageService.generatePresignedUrl(fileInfo, DateUtil.offsetMinute(new Date(), 30));
        } finally {
            log.info("上传文件完成，关闭流");
            IoUtil.close(in);
        }
    }

    @Slf4j
    public static class ExcelExportBuilder {

        private final TimeInterval timer;
        private final SysExportLog exportLog;
        private String fileName;
        private List<?> data;
        private Class<?> clazz;
        private List<WriteHandler> writeHandlers = null;
        private List<List<String>> headList = null;
        private String storagePath = "excel/";

        public ExcelExportBuilder() {
            this.timer = DateUtil.timer();
            this.exportLog = new SysExportLog();
            this.exportLog.setStatus(ExportStatusEnum.RUNNING.getCode());

            try {
                this.exportLog.setUserId(StpUtil.getLoginIdAsLong());
            } catch (Exception e) {
                log.warn("获取不到登录用户信息！");
            }
        }

        public ExcelExportBuilder fileName(String fileName) {
            this.fileName = fileName;
            this.exportLog.setName(fileName);
            return this;
        }

        public ExcelExportBuilder exportType(String exportType) {
            this.exportLog.setExportType(exportType);
            return this;
        }

        public ExcelExportBuilder setClass(Class<?> clazz) {
            this.clazz = clazz;
            return this;
        }

        public ExcelExportBuilder customHead(List<List<String>> headList) {
            this.headList = headList;
            return this;
        }

        public <T> ExcelExportBuilder setData(List<T> data) {
            this.data = data;
            return this;
        }

        public <T extends VO> ExcelExportBuilder setTransData(List<T> data) {
            if (CollUtil.isNotEmpty(data)) {
                DataScopeContext.clear();
                // 进行数据转换
                SpringUtil.getBean(TransService.class).transBatch(data);
            }
            this.data = data;
            return this;
        }

        public <T extends VO> ExcelExportBuilder queryTransData(Supplier<List<T>> queryFunc) {
            this.setTransData(queryFunc.get());
            return this;
        }

        public <T> ExcelExportBuilder queryData(Supplier<List<T>> queryFunc) {
            this.setData(queryFunc.get());
            return this;
        }

        public ExcelExportBuilder registerWriteHandler(WriteHandler handler) {
            if (this.writeHandlers == null) {
                this.writeHandlers = new ArrayList<>();
            }
            this.writeHandlers.add(handler);
            return this;
        }

        /**
         * 忽略日志记录
         */
        public ExcelExportBuilder ignoreLog() {
            this.exportLog.setId(0L);
            return this;
        }

        public ExcelExportBuilder storagePath(String storagePath) {
            this.storagePath = storagePath;
            return this;
        }

        public String doExport() {
            return new ExcelExportClient(this).doExport();
        }

        public void doAsyncExport() {
            new ExcelExportClient(this).doAsyncExport();
        }

    }

}
