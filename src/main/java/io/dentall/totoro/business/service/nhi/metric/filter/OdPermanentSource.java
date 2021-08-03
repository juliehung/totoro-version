package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.groupingBy;

public abstract class OdPermanentSource extends AbstractSource<OdDto, Map<Long, Map<String, List<OdDto>>>> {

    protected static final List<String> teeth = unmodifiableList(asList(
        "11", "12", "13", "14", "15", "16", "17", "18", "19",
        "21", "22", "23", "24", "25", "26", "27", "28", "29",
        "31", "32", "33", "34", "35", "36", "37", "38", "39",
        "41", "42", "43", "44", "45", "46", "47", "48", "49",
        "99"
    ));

    @Override
    public List<Map<Long, Map<String, List<OdDto>>>> doFilter(List<OdDto> source) {
        return singletonList(source.stream().parallel()
            .filter(dto -> teeth.contains(dto.getTooth()))
            .collect(groupingBy(OdDto::getPatientId, groupingBy(OdDto::getTooth))));
    }

}
