package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingBy;

public class OdQuarterByPatientSource extends AbstractSource<OdDto, Map<Long, Map<String, List<OdDto>>>> {

    public OdQuarterByPatientSource(MetricConfig metricConfig) {
        super(new OdQuarterSource(metricConfig));
    }

    @Override
    public List<Map<Long, Map<String, List<OdDto>>>> doFilter(Stream<OdDto> source) {
        return singletonList(source.collect(groupingBy(OdDto::getPatientId, groupingBy(OdDto::getTreatmentProcedureTooth))));
    }

}
