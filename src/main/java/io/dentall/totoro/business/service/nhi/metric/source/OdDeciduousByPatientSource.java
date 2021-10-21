package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.groupingBy;

public abstract class OdDeciduousByPatientSource extends AbstractSource<Map<Long, Map<String, List<MetricTooth>>>> {

    protected static final List<String> teeth = unmodifiableList(asList(
        "51", "52", "53", "54", "55",
        "61", "62", "63", "64", "65",
        "71", "72", "73", "74", "75",
        "81", "82", "83", "84", "85"
    ));

    public OdDeciduousByPatientSource(Source<MetricTooth, ?> inputSource) {
        super(inputSource);
    }

    @Override
    public List<Map<Long, Map<String, List<MetricTooth>>>> doFilter(Stream<MetricTooth> source) {
        return singletonList(source
            .filter(dto -> teeth.contains(dto.getTooth()))
            .collect(groupingBy(MetricTooth::getPatientId, groupingBy(MetricTooth::getTooth))));
    }

}
