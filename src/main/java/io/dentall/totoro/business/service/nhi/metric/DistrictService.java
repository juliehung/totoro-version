package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.dto.DistrictDto;
import io.dentall.totoro.business.service.nhi.metric.source.MetricSubject;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.domain.Holiday;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DistrictService {

    Optional<? extends DistrictDto> metric(LocalDate baseDate, MetricSubject metricSubject, List<? extends NhiMetricRawVM> source, Map<LocalDate, Optional<Holiday>> holidayMap);
}
