package cn.lenmotion.donut.framework.excel;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.lenmotion.donut.core.exception.BusinessException;
import cn.lenmotion.donut.core.utils.AssertUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.spring.SpringContextUtil;
import org.dromara.trans.service.impl.TransService;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * @author lenmotion
 */
@Slf4j
public class ExcelImportClient {

    private final ExcelImportBuilder builder;
    private final TransService transService;

    private ExcelImportClient(ExcelImportBuilder builder) {
        this.builder = builder;
        this.transService = SpringUtil.getBean(TransService.class);
    }

    public static ExcelImportBuilder builder() {
        return new ExcelImportBuilder();
    }

    private void doSyncImport() {
        AssertUtils.notNull(builder.listener, "导入监听类未配置");
        var readerBuilder = EasyExcel.read(builder.inputStream, builder.listener);
        Optional.ofNullable(builder.headRowNumber).ifPresent(readerBuilder::headRowNumber);

        readerBuilder.sheet().doReadSync();
    }

    private <T> List<T> doImport() {
        AssertUtils.notNull(builder.clazz, "导入类未配置");

        var readerBuilder = EasyExcel.read(builder.inputStream).head(builder.clazz);
        Optional.ofNullable(builder.headRowNumber).ifPresent(readerBuilder::headRowNumber);

        List<T> list = readerBuilder.sheet().doReadSync();
        // 自动转换，如果需要
        transService.unTransMore(list);

        if (builder.validate) {
            Validator validator = SpringContextUtil.getBeanByClass(Validator.class);

            for (int i = 0; i < list.size(); i++) {
                T importVO = list.get(i);
                var result = new BeanPropertyBindingResult(importVO, "importVO" + i);
                validator.validate(importVO, result);

                if (result.hasErrors()) {
                    // 处理验证错误，例如构建错误消息或返回错误信息
                    var msg = Optional.ofNullable(result.getFieldError()).map(FieldError::getDefaultMessage).orElse("未知错误");
                    throw new BusinessException(StrUtil.format("第{}行数据验证失败：{}", i + 1, msg));
                }
            }
        }

        return list;
    }

    @Slf4j
    public static class ExcelImportBuilder {

        private Class<?> clazz;
        private InputStream inputStream;
        private ReadListener<?> listener;
        private Integer headRowNumber;
        private boolean validate = true;

        public ExcelImportBuilder() {
        }

        public ExcelImportBuilder setClass(Class<?> clazz) {
            this.clazz = clazz;
            return this;
        }

        public ExcelImportBuilder inputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public ExcelImportBuilder listener(ReadListener<?> listener) {
            this.listener = listener;
            return this;
        }

        public ExcelImportBuilder headRowNumber(Integer headRowNumber) {
            this.headRowNumber = headRowNumber;
            return this;
        }

        public ExcelImportBuilder validate(boolean validate) {
            this.validate = validate;
            return this;
        }

        public <T> List<T> doImport() {
            return new ExcelImportClient(this).doImport();
        }

        public void doSyncImport() {
            new ExcelImportClient(this).doSyncImport();
        }

    }

}
