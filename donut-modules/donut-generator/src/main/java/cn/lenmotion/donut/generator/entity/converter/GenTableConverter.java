package cn.lenmotion.donut.generator.entity.converter;

import cn.lenmotion.donut.generator.entity.po.GenTable;
import cn.lenmotion.donut.generator.entity.po.GenTableColumn;
import cn.lenmotion.donut.generator.entity.request.GenTableColumnRequest;
import cn.lenmotion.donut.generator.entity.request.GenTableRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author lenmotion
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenTableConverter {

    GenTableConverter INSTANCE = Mappers.getMapper(GenTableConverter.class);

    GenTable toTablePo(GenTableRequest request);

    List<GenTableColumn> toColumnPoList(List<GenTableColumnRequest> list);

}
