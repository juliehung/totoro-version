package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.groupingBy;

/**
 * @ OD-4@醫令數
 */
public class Od4TreatmentCount extends SingleSourceCalculator<Long> {

    private static final List<String> codes = unmodifiableList(asList("89001C", "89004C", "89008C", "89011C"));

    public Od4TreatmentCount(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<OdDto> odDtoList = collector.retrieveSource(sourceName());
        return (long) odDtoList.stream()
            .filter(dto -> codes.contains(dto.getCode()))
            .collect(groupingBy(dto -> dto.getDisposalId() + dto.getCode()))
            .size();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Od4ToothCount;
    }
}
