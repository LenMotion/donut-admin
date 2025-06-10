package cn.lenmotion.donut.system.entity.converter;

import cn.lenmotion.donut.core.entity.OperationLogData;
import cn.lenmotion.donut.system.entity.po.SysOperationLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author lenmotion
 */
@Mapper
public interface OperationLogConverter {

    OperationLogConverter INSTANCE = Mappers.getMapper(OperationLogConverter.class);

    SysOperationLog toPo(OperationLogData operationLogData);

}
