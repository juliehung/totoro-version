package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

import java.util.List;

/**
 * @ OD-1@齒數
 */
public class Od1ToothCount extends SingleSourceCalculator {

    public Od1ToothCount(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<OdDto> odDtoList = collector.retrieveSource(sourceName());
        return (long) odDtoList.size();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Od1ToothCount;
    }
}
