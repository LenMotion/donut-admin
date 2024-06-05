package cn.lenmotion.donut.generator.service.impl;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.lenmotion.donut.core.entity.BasePageQuery;
import cn.lenmotion.donut.core.entity.BasePo;
import cn.lenmotion.donut.core.exception.BusinessException;
import cn.lenmotion.donut.core.utils.AssertUtils;
import cn.lenmotion.donut.core.utils.EnumUtils;
import cn.lenmotion.donut.generator.entity.enums.DatasourceTypeEnum;
import cn.lenmotion.donut.generator.entity.enums.MysqlTypeEnum;
import cn.lenmotion.donut.generator.entity.po.GenTable;
import cn.lenmotion.donut.generator.entity.po.GenTableColumn;
import cn.lenmotion.donut.generator.entity.query.ColumnsQuery;
import cn.lenmotion.donut.generator.mapper.GenTableColumnMapper;
import cn.lenmotion.donut.generator.service.GenDatasourceService;
import cn.lenmotion.donut.generator.service.GenTableColumnService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lenmotion
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GenTableColumnServiceImpl extends ServiceImpl<GenTableColumnMapper, GenTableColumn> implements GenTableColumnService {

    private final GenDatasourceService datasourceService;

    private static final List<String> NOT_SHOW_COLUMNS = List.of("update_time", "create_by", "update_by", "deleted");

    private static final List<String> NOT_EDIT_COLUMNS = List.of("create_time", "update_time", "create_by", "update_by", "deleted");

    @Override
    public List<GenTableColumn> tableColumns(ColumnsQuery query) {
        var datasource = datasourceService.getById(query.getDatasourceId());
        AssertUtils.notNull(datasource, "数据源不存在");
        var datasourceTypeEnum = EnumUtils.getByCode(DatasourceTypeEnum.class, datasource.getType());
        AssertUtils.notNull(datasourceTypeEnum, "数据源类型不存在");

        Connection conn = null;
        ResultSet rs = null;
        ResultSet primaryKeys = null;
        try {
            conn = datasourceService.getConnectionById(query.getDatasourceId());
            DatabaseMetaData metaData = conn.getMetaData();
            // 获取主键
            primaryKeys = metaData.getPrimaryKeys(conn.getCatalog(), conn.getSchema(), query.getTableName());
            List<String> pkList = new ArrayList<>();
            while (primaryKeys.next()) {
                pkList.add(primaryKeys.getString("COLUMN_NAME"));
            }
            // 查询表列信息
            rs = metaData.getColumns(conn.getCatalog(), conn.getSchema(), query.getTableName(), "%");
            List<GenTableColumn> columns = new ArrayList<>();
            AtomicInteger atomicInteger = new AtomicInteger(0);
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME").toUpperCase();
                GenTableColumn tableColumn = new GenTableColumn();
                tableColumn.setColumnName(columnName.toLowerCase());
                // java的字段名称
                tableColumn.setFieldName(StrUtil.toCamelCase(columnName.toLowerCase()));
                // 获取列的注释
                String remarks = rs.getString("REMARKS");
                tableColumn.setColumnRemark(ObjUtil.isEmpty(remarks) ? columnName : remarks);
                // 获取列的数据类型
                String typeName = rs.getString("TYPE_NAME").toUpperCase();
                tableColumn.setColumnType(ObjUtil.isEmpty(typeName) ? "NONE" : typeName);

                switch (datasourceTypeEnum) {
                    case MYSQL -> {
                        // 生成对应的java类型
                        if (MysqlTypeEnum.TINYINT.name().equals(typeName) && rs.getInt("COLUMN_SIZE") == 1) {
                            tableColumn.setJavaTypeClass(Boolean.class.getName());
                            tableColumn.setJavaType("Boolean");
                        } else {
                            MysqlTypeEnum typeEnum = MysqlTypeEnum.getByName(typeName);
                            tableColumn.setJavaType(typeEnum.getCode());
                            tableColumn.setJavaTypeClass(typeEnum.getRemark());
                        }
                    }
                    // todo 实现pgsql的字段映射
                    case POSTGRESQL -> {

                    }
                }
                // 是否是主键
                tableColumn.setIdField(pkList.contains(tableColumn.getColumnName()));
                tableColumn.setEditField(!NOT_EDIT_COLUMNS.contains(tableColumn.getColumnName()));
                tableColumn.setEditFieldType("Input");
                tableColumn.setSearchField(!tableColumn.getIdField() && !NOT_EDIT_COLUMNS.contains(tableColumn.getColumnName()));
                tableColumn.setSearchFieldType("Input");
                tableColumn.setTableField(!tableColumn.getIdField() && !NOT_SHOW_COLUMNS.contains(tableColumn.getColumnName()));
                tableColumn.setSortIndex(atomicInteger.incrementAndGet());

                columns.add(tableColumn);
            }
            return columns;
        } catch (SQLException e) {
            log.error("查询数据库表错误", e);
            throw new BusinessException("获取字段错误：表名 = " + query.getTableName());
        } finally {
            JdbcUtils.closeResultSet(primaryKeys);
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeConnection(conn);
        }
    }

    @Override
    public List<GenTableColumn> tableColumns(Long tableId) {
        LambdaQueryWrapper<GenTableColumn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GenTableColumn::getTableId, tableId)
                .orderByAsc(GenTableColumn::getSortIndex);
        return list(queryWrapper);
    }

    @Override
    public Boolean saveBatch(Long genTableId, List<GenTableColumn> list) {
        LambdaQueryWrapper<GenTableColumn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GenTableColumn::getTableId, genTableId);
        super.remove(queryWrapper);

        list.forEach(item -> {
            item.setTableId(genTableId);
            try {
                var javaType = ClassUtil.getClassName(Class.forName(item.getJavaTypeClass()), true);
                item.setJavaType(javaType);
            } catch (ClassNotFoundException e) {
                throw new BusinessException("获取Java类型错误：类名 = " + e.getMessage());
            }
            item.setUpperFieldName(StrUtil.upperFirst(item.getFieldName()));
        });

        return super.saveBatch(list);
    }

    @Override
    public List<GenTableColumn> genColumn(GenTable genTable) {
        List<String> ignoreColumns;
        // 排除父类的字段
        if (StrUtil.isNotBlank(genTable.getSuperClass())) {
            try {
                ignoreColumns = this.getFieldsByClass(Class.forName(genTable.getSuperClass()));
            } catch (ClassNotFoundException e) {
                throw new BusinessException("解析父类失败!");
            }
        } else {
            ignoreColumns = new ArrayList<>();
        }
        // 查询表的字段，然后过滤父字段
        LambdaQueryWrapper<GenTableColumn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GenTableColumn::getTableId, genTable.getId())
                .orderByAsc(GenTableColumn::getSortIndex);
        return this.list(queryWrapper).stream().filter(e -> !ignoreColumns.contains(e.getFieldName())).toList();
    }

    @Override
    public List<GenTableColumn> genSearchColumn(GenTable genTable) {
        return this.getGenColumns(BasePageQuery.class, genTable.getId(), GenTableColumn::getSearchField);
    }

    @Override
    public List<GenTableColumn> genEditColumn(GenTable genTable) {
        return this.getGenColumns(BasePo.class, genTable.getId(), GenTableColumn::getEditField);
    }

    private List<GenTableColumn> getGenColumns(Class<?> clz, Long tableId, SFunction<GenTableColumn, ?> func) {
        List<String> ignoreColumns = this.getFieldsByClass(clz);
        // 查询表的字段，然后过滤父字段
        LambdaQueryWrapper<GenTableColumn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GenTableColumn::getTableId, tableId).eq(func, 1)
                .orderByAsc(GenTableColumn::getSortIndex);
        return this.list(queryWrapper).stream().filter(e -> !ignoreColumns.contains(e.getFieldName())).toList();
    }

    private List<String> getFieldsByClass(Class<?> clz) {
        List<String> ignoreColumns = new ArrayList<>();
        Field[] supportedFields = ClassUtil.getDeclaredFields(clz);
        for (Field supportedField : supportedFields) {
            ignoreColumns.add(supportedField.getName());
        }
        Class<?> superclass = clz.getSuperclass();
        if (superclass != null) {
            ignoreColumns.addAll(this.getFieldsByClass(superclass));
        }
        return ignoreColumns;
    }

    @Override
    public List<GenTableColumn> genTableColumn(GenTable genTable) {
        LambdaQueryWrapper<GenTableColumn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GenTableColumn::getTableId, genTable.getId())
                .eq(GenTableColumn::getTableField, 1)
                .orderByAsc(GenTableColumn::getSortIndex);
        return this.list(queryWrapper);
    }

    @Override
    public List<GenTableColumn> genIdColumn(GenTable genTable) {
        LambdaQueryWrapper<GenTableColumn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GenTableColumn::getTableId, genTable.getId())
                .eq(GenTableColumn::getIdField, 1)
                .orderByAsc(GenTableColumn::getSortIndex);
        return this.list(queryWrapper);
    }

}
