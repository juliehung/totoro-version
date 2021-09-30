package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingBy;

public class OdMonthSelectedByPatientSource extends AbstractSource<Map<Long, Map<String, List<MetricTooth>>>> {

    public OdMonthSelectedByPatientSource(MetricConfig metricConfig) {
        super(new OdMonthSelectedSource(metricConfig));
    }

    @Override
    public List<Map<Long, Map<String, List<MetricTooth>>>> doFilter(Stream<MetricTooth> source) {
        return singletonList(source.collect(groupingBy(MetricTooth::getPatientId, groupingBy(MetricTooth::getTooth))));
    }

}
