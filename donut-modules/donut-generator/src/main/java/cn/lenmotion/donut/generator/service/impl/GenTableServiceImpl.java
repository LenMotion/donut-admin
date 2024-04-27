package cn.lenmotion.donut.generator.service.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateUtil;
import cn.lenmotion.donut.core.exception.BusinessException;
import cn.lenmotion.donut.core.utils.AssertUtils;
import cn.lenmotion.donut.core.utils.EnumUtils;
import cn.lenmotion.donut.generator.entity.converter.GenTableConverter;
import cn.lenmotion.donut.generator.entity.enums.DatasourceTypeEnum;
import cn.lenmotion.donut.generator.entity.po.GenTable;
import cn.lenmotion.donut.generator.entity.po.GenTableColumn;
import cn.lenmotion.donut.generator.entity.query.GenTableQuery;
import cn.lenmotion.donut.generator.entity.request.GenTableRequest;
import cn.lenmotion.donut.generator.mapper.GenTableMapper;
import cn.lenmotion.donut.generator.service.GenDatasourceService;
import cn.lenmotion.donut.generator.service.GenTableColumnService;
import cn.lenmotion.donut.generator.service.GenTableService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lenmotion
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GenTableServiceImpl extends ServiceImpl<GenTableMapper, GenTable> implements GenTableService {

    private final GenTableColumnService genTableColumnService;
    private final GenDatasourceService genDatasourceService;
    private final RSA rsa;

    @Override
    public IPage<GenTable> selectPageList(GenTableQuery query) {
        var queryWrapper = Wrappers.<GenTable>lambdaQuery();
        queryWrapper
                .eq(ObjUtil.isNotEmpty(query.getTableName()), GenTable::getTableName, query.getTableName())
                .eq(ObjUtil.isNotEmpty(query.getFeatureName()), GenTable::getFeatureName, query.getFeatureName())
                .orderByDesc(GenTable::getCreateTime);
        return this.page(query.toPage(), queryWrapper);
    }

    @Override
    public List<GenTable> tables(Long datasourceId) {
        if (datasourceId == null) {
            return new ArrayList<>();
        }

        Connection conn = null;
        ResultSet rs = null;
        try {
            var datasource = genDatasourceService.getById(datasourceId);
            AssertUtils.notNull(datasource, "数据源不存在");
            var typeEnum = EnumUtils.getByCode(DatasourceTypeEnum.class, datasource.getType());
            AssertUtils.notNull(typeEnum, "数据源类型不存在");
            var url = StrUtil.format(typeEnum.getUrl(), datasource.getHost(), datasource.getPort(), datasource.getSchemaName());

            var password = rsa.decryptStr(datasource.getPassword(), KeyType.PrivateKey);
            conn = DriverManager.getConnection(url, datasource.getUsername(), password);
            DatabaseMetaData metaData = conn.getMetaData();
            rs = metaData.getTables(conn.getCatalog(), conn.getSchema(), "%", new String[]{"TABLE"});
            List<GenTable> tables = new ArrayList<>();
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                GenTable genTable = new GenTable();
                genTable.setTableName(tableName);
                String remarks = rs.getString("REMARKS");
                genTable.setFeatureName(ObjUtil.isEmpty(remarks) ? tableName : remarks);
                // 生成类名
                genTable.setClassName(StrUtil.upperFirst(StrUtil.toCamelCase(tableName)));
                tables.add(genTable);
            }
            return tables;
        } catch (SQLException e) {
            log.error("查询数据库表错误", e);
            throw new BusinessException("查询数据库表错误");
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeConnection(conn);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdate(GenTableRequest request) {
        GenTable genTable = GenTableConverter.INSTANCE.toTablePo(request);

        var result = super.saveOrUpdate(genTable);
        if (result) {
            genTableColumnService.saveBatch(genTable.getId(), GenTableConverter.INSTANCE.toColumnPoList(request.getColumns()));
        }
        return result;
    }

    @Override
    public String genCode(String fileName, Long tableId) {
        var genTable = this.getById(tableId);

        var templateConfig = new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH);
        var engine = TemplateUtil.createEngine(templateConfig);
        var template = engine.getTemplate(fileName);

        Dict dict = Dict.create();
        dict.setFields(
                genTable::getTableName,
                genTable::getFeatureName,
                genTable::getAuthor,
                genTable::getClassName,
                genTable::getPackageName,
                genTable::getSuperClass,
                genTable::getModuleName,
                genTable::getStatusApi,
                genTable::getMenuId
        );
        dict.set("lowClassName", StrUtil.lowerFirst(genTable.getClassName()));
        dict.set("genMenuId", IdUtil.getSnowflakeNextId());
        dict.set("saveMenuId", IdUtil.getSnowflakeNextId());
        dict.set("removeMenuId", IdUtil.getSnowflakeNextId());
        dict.set("statusMenuId", IdUtil.getSnowflakeNextId());
        List<GenTableColumn> columns = genTableColumnService.genColumn(genTable);
        dict.set("columns", columns);
        dict.set("columnClasses", columns.stream().map(GenTableColumn::getJavaTypeClass).filter(e -> !e.startsWith("java.lang")).toList());
        List<GenTableColumn> searchColumns = genTableColumnService.genSearchColumn(genTable);
        dict.set("searchColumns", searchColumns);
        dict.set("searchColumnClasses", searchColumns.stream().map(GenTableColumn::getJavaTypeClass).filter(e -> !e.startsWith("java.lang")).toList());
        List<GenTableColumn> editColumns = genTableColumnService.genEditColumn(genTable);
        dict.set("editColumns", editColumns);
        dict.set("editColumnClasses", editColumns.stream().map(GenTableColumn::getJavaTypeClass).filter(e -> !e.startsWith("java.lang")).toList());
        List<GenTableColumn> tableColumns = genTableColumnService.genTableColumn(genTable);
        dict.set("tableColumns", tableColumns);
        dict.set("tableColumnClasses", tableColumns.stream().map(GenTableColumn::getJavaTypeClass).filter(e -> !e.startsWith("java.lang")).toList());

        if (StrUtil.isNotBlank(genTable.getSuperClass())) {
            try {
                dict.set("superClassName", ClassUtil.getClassName(Class.forName(genTable.getSuperClass()), true));
            } catch (ClassNotFoundException e) {
                dict.remove("superClass");
            }
        }

        return template.render(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        LambdaQueryWrapper<GenTableColumn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GenTableColumn::getTableId, id);
        genTableColumnService.remove(queryWrapper);
        return super.removeById(id);
    }
}
