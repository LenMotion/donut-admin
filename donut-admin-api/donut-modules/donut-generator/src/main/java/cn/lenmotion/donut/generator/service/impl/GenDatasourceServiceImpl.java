package cn.lenmotion.donut.generator.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.lenmotion.donut.core.enums.BaseStatusEnum;
import cn.lenmotion.donut.core.service.impl.DonutServiceImpl;
import cn.lenmotion.donut.core.utils.AssertUtils;
import cn.lenmotion.donut.core.utils.EnumUtils;
import cn.lenmotion.donut.generator.entity.converter.GenDatasourceConverter;
import cn.lenmotion.donut.generator.entity.enums.DatasourceTypeEnum;
import cn.lenmotion.donut.generator.entity.po.GenDatasource;
import cn.lenmotion.donut.generator.entity.query.GenDatasourceQuery;
import cn.lenmotion.donut.generator.entity.request.GenDatasourceRequest;
import cn.lenmotion.donut.generator.mapper.GenDatasourceMapper;
import cn.lenmotion.donut.generator.service.GenDatasourceService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class GenDatasourceServiceImpl extends DonutServiceImpl<GenDatasourceMapper, GenDatasource> implements GenDatasourceService {

    private final RSA rsa;

    @Override
    public IPage<GenDatasource> selectPageList(GenDatasourceQuery query) {
        var queryWrapper = Wrappers.<GenDatasource>lambdaQuery();
        queryWrapper
                .eq(ObjUtil.isNotEmpty(query.getType()), GenDatasource::getType, query.getType())
                .eq(ObjUtil.isNotEmpty(query.getName()), GenDatasource::getName, query.getName())
                .eq(ObjUtil.isNotEmpty(query.getHost()), GenDatasource::getHost, query.getHost())
                .orderByDesc(GenDatasource::getCreateTime);
        IPage<GenDatasource> page = this.page(query.toPage(), queryWrapper);
        if (CollUtil.isNotEmpty(page.getRecords())) {
            page.getRecords().forEach(e -> e.setPassword(null));
        }
        return page;
    }

    @Override
    public Boolean saveOrUpdate(GenDatasourceRequest request) {
        var genDatasource = GenDatasourceConverter.INSTANCE.toPo(request);
        return super.saveOrUpdate(genDatasource);
    }

    @Override
    public List<GenDatasource> selectEnableList() {
        var queryWrapper = Wrappers.<GenDatasource>lambdaQuery();
        queryWrapper.eq(GenDatasource::getStatus, BaseStatusEnum.ENABLED.getCode())
                .eq(GenDatasource::getCheckConnection, Boolean.TRUE)
                .orderByDesc(GenDatasource::getCreateTime);
        var list = super.list(queryWrapper);
        list.forEach(e -> e.setPassword(null));
        return list;
    }

    @Override
    public Boolean checkConnection(Long id) {
        GenDatasource updateDatasource = new GenDatasource();
        updateDatasource.setId(id);
        try (Connection ignored = this.getConnectionById(id)) {
            updateDatasource.setCheckConnection(true);
        } catch (Exception e) {
            log.error("数据源连接失败: ", e);
            updateDatasource.setCheckConnection(false);
        }
        super.updateById(updateDatasource);
        return updateDatasource.getCheckConnection();
    }

    @Override
    public Connection getConnectionById(Long id) throws SQLException {
        var datasource = this.getById(id);
        AssertUtils.notNull(datasource, "数据源不存在");
        var typeEnum = EnumUtils.getByCode(DatasourceTypeEnum.class, datasource.getType());
        AssertUtils.notNull(typeEnum, "数据源类型不存在");
        var url = StrUtil.format(typeEnum.getUrl(), datasource.getHost(), datasource.getPort(), datasource.getSchemaName());
        var password = rsa.decryptStr(datasource.getPassword(), KeyType.PrivateKey);
        return DriverManager.getConnection(url, datasource.getUsername(), password);
    }
}
