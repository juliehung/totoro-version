package io.dentall.totoro.business.service.nhi.metric.mapper;

import io.dentall.totoro.business.service.nhi.metric.dto.ExcludeDto;
import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
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
        @Mapping(target = "surface", source = "treatmentProcedureSurface"),
        @Mapping(target = "category", source = "nhiCategory"),
        @Mapping(target = "specificCode", source = "treatmentProcedureSpecificCode")

    })
    OdDto mapToOdDto(NhiMetricRawVM vm);

    ExcludeDto mapToExcludeDto(NhiMetricRawVM vm);

    @Mappings({
        @Mapping(target = "treatmentProcedureCode", source = "code"),
        @Mapping(target = "nhiCategory", source = "category"),
        @Mapping(target = "treatmentProcedureSpecificCode", source = "specificCode")
    })
    ExcludeDto mapToExcludeDto(OdDto odDto);
}
