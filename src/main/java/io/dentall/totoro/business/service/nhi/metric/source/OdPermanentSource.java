package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.groupingBy;

public abstract class OdPermanentSource extends AbstractSource<Map<Long, Map<String, List<MetricTooth>>>> {

    protected static final List<String> teeth = unmodifiableList(asList(
        "11", "12", "13", "14", "15", "16", "17", "18", "19",
        "21", "22", "23", "24", "25", "26", "27", "28", "29",
        "31", "32", "33", "34", "35", "36", "37", "38", "39",
        "41", "42", "43", "44", "45", "46", "47", "48", "49",
        "99"
    ));

    public OdPermanentSource(Source<?, ?> inputSource) {
        super(inputSource);
    }

    @Override
    public List<Map<Long, Map<String, List<MetricTooth>>>> doFilter(Stream<MetricTooth> source) {
        return singletonList(source
            .filter(dto -> teeth.contains(dto.getTooth()))
            .collect(groupingBy(MetricTooth::getPatientId, groupingBy(MetricTooth::getTooth))));
    }

}
