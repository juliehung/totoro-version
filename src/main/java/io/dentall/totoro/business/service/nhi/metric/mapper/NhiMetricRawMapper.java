package io.dentall.totoro.business.service.nhi.metric.mapper;

import io.dentall.totoro.business.service.nhi.metric.util.OdDto;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NhiMetricRawMapper {

    NhiMetricRawMapper INSTANCE = Mappers.getMapper(NhiMetricRawMapper.class);

    @Mappings({
        @Mapping(target = "code", source = "treatmentProcedureCode"),
        @Mapping(target = "surface", source = "treatmentProcedureSurface")

    })
    OdDto mapToOdDto(NhiMetricRawVM vm);
}
