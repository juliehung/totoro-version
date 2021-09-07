package io.dentall.totoro.service.mapper;

import io.dentall.totoro.business.vm.nhi.NhiMetricReportBodyVM;
import io.dentall.totoro.domain.NhiMetricReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NhiMetricReportMapper {
    NhiMetricReportMapper INSTANCE = Mappers.getMapper(NhiMetricReportMapper.class);

    @Mappings({
        @Mapping(
            target = "yearMonth",
            expression = "java( io.dentall.totoro.service.util.DateTimeUtil.transformLocalDateToFormatedStringYyyymm( nhiMetricReportBodyVM.getBegin() ) )"
        ),
        @Mapping(target = "status", constant = "LOCK"),
        @Mapping(target = "comment.nhiMetricReportTypes", source ="nhiMetricReportTypes"),
        @Mapping(target = "comment.doctorIds", source ="doctorIds")
    })
    NhiMetricReport convertBodyToDomain(NhiMetricReportBodyVM nhiMetricReportBodyVM);

}
