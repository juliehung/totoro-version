package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.service.nhi.metric.util.OdDto;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.groupingBy;

public abstract class OdDeciduousSource extends AbstractSource<OdDto, Map<Long, List<OdDto>>> {

    protected static final List<String> teeth = unmodifiableList(asList(
        "51", "52", "53", "54", "55",
        "61", "62", "63", "64", "65",
        "71", "72", "73", "74", "75",
        "81", "82", "83", "84", "85"
    ));

    @Override
    public List<Map<Long, List<OdDto>>> doFilter(List<OdDto> source) {
        return singletonList(source.stream().parallel()
            .filter(dto -> teeth.contains(dto.getTooth()))
            .collect(groupingBy(OdDto::getPatientId)));
    }

}
