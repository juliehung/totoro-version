package io.dentall.totoro.business.service.nhi.metric.mapper;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTreatment;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NhiMetricRawMapper {

    NhiMetricRawMapper INSTANCE = Mappers.getMapper(NhiMetricRawMapper.class);

    MetricTooth mapToOdDto(NhiMetricRawVM vm);

    MetricTreatment mapToMetricTreatment(NhiMetricRawVM vm);

}
