package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

/**
 * @ OD-5@齒數
 */
public class Od5ToothCount extends SingleSourceCalculator<Long> {

    private static final List<String> codes = unmodifiableList(asList("89002C", "89005C", "89009C"));

    public Od5ToothCount(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<OdDto> odDtoList = collector.retrieveSource(sourceName());
        return odDtoList.stream().filter(dto -> codes.contains(dto.getCode())).count();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Od5ToothCount;
    }
}
