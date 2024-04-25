package cn.lenmotion.donut.framework.excel;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.exception.BusinessException;
import cn.lenmotion.donut.core.utils.AssertUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.fhs.common.spring.SpringContextUtil;
import com.fhs.core.trans.vo.VO;
import com.fhs.trans.service.impl.TransService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lenmotion
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ExcelClient {
    private final FileStorageService fileStorageService;
    private final TransService transService;

    /**
     * 导出需要easy-trans转换的数据
     */
    public <T extends VO> String exportTrans(List<T> list, Class<T> clz, String fileName) {
        if (CollUtil.isNotEmpty(list)) {
            transService.transBatch(list);
        }
        return this.export(list, clz, fileName);
    }

    /**
     * 导出excel
     */
    public <T> String export(List<T> list, Class<T> clz, String fileName) {
        var out = new ByteArrayOutputStream();
        try {
            // 写数据到文件中
            EasyExcel.write(out, clz)
                    .excelType(ExcelTypeEnum.XLSX)
                    .registerWriteHandler(cellStyleStrategy())
                    .sheet(fileName)
                    .doWrite(list);
            // 上传流至文件服务器
            return this.uploadStream(out, fileName);
        } catch (Exception e) {
            log.error("导出失败", e);
            throw new BusinessException("导出失败");
        } finally {
            IoUtil.close(out);
        }
    }

    /**
     * 自定义head导出
     */
    public <T> String export(List<List<String>> head, List<T> dataList, String fileName) {
        var out = new ByteArrayOutputStream();
        try {
            EasyExcel.write(out)
                    .head(head)
                    .excelType(ExcelTypeEnum.XLSX)
                    .registerWriteHandler(cellStyleStrategy())
                    .sheet(fileName)
                    .doWrite(dataList);
            // 上传流至文件服务器
            return this.uploadStream(out, fileName);
        } catch (Exception e) {
            log.error("导出失败", e);
            throw new BusinessException("导出失败");
        } finally {
            IoUtil.close(out);
        }
    }

    /**
     * 上传流至文件服务器
     */
    private String uploadStream(ByteArrayOutputStream out, String fileName) {
        ByteArrayInputStream in = null;
        try {
            // 生成文件地址，加入随机数，避免重复
            var upFileName = fileName + "_" + IdUtil.getSnowflakeNextId() + ExcelTypeEnum.XLSX.getValue();
            // 设置请求头
            Map<String, String> metadata = new HashMap<>();
            var contentDispositionValue = StrUtil.format("attachment; filename={}", URLUtil.encode(upFileName));
            metadata.put("Content-disposition", contentDispositionValue);
            // 上传文件信息
            in = IoUtil.toStream(out);
            var fileInfo = fileStorageService.of(in, upFileName, BaseConstants.EXCEL_CONTEXT_TYPE)
                    .setPath("excel/")
                    .setMetadata(metadata)
                    .setObjectType("excel")
                    .upload();
            // 生成访问链接
            return fileStorageService.generatePresignedUrl(fileInfo, DateUtil.offsetMinute(new Date(), 30));
        } catch (Exception e) {
            throw e;
        } finally {
            IoUtil.close(in);
        }
    }

    /**
     * 设置内容和header的策略
     */
    private static HorizontalCellStyleStrategy cellStyleStrategy() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 10);
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);

        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 背景绿色
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    /**
     * 导入文件
     *
     * @param file
     * @param clz
     * @param validate
     * @param <T>
     * @return
     * @throws BusinessException
     */
    public <T> List<T> importExcel(MultipartFile file, Class<T> clz, Boolean validate) throws BusinessException {
        try {
            List<T> list = EasyExcel.read(file.getInputStream()).head(clz).sheet().doReadSync();

            AssertUtils.notEmpty(list, "导入数据为空");

            TransService transService = SpringContextUtil.getBeanByClass(TransService.class);
            transService.unTransMore(list);

            if (Boolean.TRUE.equals(validate)) {
                Validator validator = SpringContextUtil.getBeanByClass(Validator.class);

                for (int i = 0; i < list.size(); i++) {
                    T importVO = list.get(i);
                    BindingResult result = new BeanPropertyBindingResult(importVO, "importVO" + i);
                    validator.validate(importVO, result);

                    if (result.hasErrors()) {
                        // 处理验证错误，例如构建错误消息或返回错误信息
                        throw new BusinessException(StrUtil.format("第{}行数据验证失败：{}", i + 1, result.getFieldError().getDefaultMessage()));
                    }
                }
            }

            return list;
        } catch (IOException e) {
            throw new BusinessException("文件读取失败");
        } catch (Exception e) {
            log.error("导入数据失败", e);
            throw new BusinessException(e.getMessage());
        }
    }
}
