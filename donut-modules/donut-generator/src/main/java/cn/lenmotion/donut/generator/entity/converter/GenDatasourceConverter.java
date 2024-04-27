package cn.lenmotion.donut.generator.entity.converter;

import cn.lenmotion.donut.generator.entity.po.GenDatasource;
import cn.lenmotion.donut.generator.entity.request.GenDatasourceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author lenmotion
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenDatasourceConverter {

    GenDatasourceConverter INSTANCE = Mappers.getMapper(GenDatasourceConverter.class);

    GenDatasource toPo(GenDatasourceRequest request);

}
