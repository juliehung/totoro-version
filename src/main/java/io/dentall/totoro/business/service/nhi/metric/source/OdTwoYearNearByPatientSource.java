package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;

import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingBy;

public class OdTwoYearNearByPatientSource extends AbstractSource<OdDto, Map<Long, Map<String, List<OdDto>>>> {

    public OdTwoYearNearByPatientSource(MetricConfig metricConfig) {
        super(new OdTwoYearNearSource(metricConfig));
    }

    @Override
    public List<Map<Long, Map<String, List<OdDto>>>> filter(List<OdDto> source) {
        return singletonList(source.stream().parallel()
            .collect(groupingBy(OdDto::getPatientId, groupingBy(OdDto::getTooth))));
    }

}
