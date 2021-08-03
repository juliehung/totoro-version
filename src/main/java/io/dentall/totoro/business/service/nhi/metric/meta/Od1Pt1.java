package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

import java.util.List;

import static java.util.stream.Collectors.groupingBy;

/**
 * @ OD-1@@PT-1@
 */
public class Od1Pt1 extends SingleSourceCalculator {

    public Od1Pt1(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<OdDto> odDtoList = collector.retrieveSource(sourceName());

        return (long) odDtoList.stream()
            .map(OdDto::getPatientId)
            .collect(groupingBy(id -> id))
            .keySet().size();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Od1Pt1;
    }
}
