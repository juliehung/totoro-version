package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.util.OdDto;

import java.util.List;
import java.util.Map;

/**
 * 申報OD之乳牙顆數
 */
public class OdDeciduousTreatment extends SingleSourceCalculator {

    public OdDeciduousTreatment(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<Map<Long, List<OdDto>>> source = collector.retrieveSource(sourceName());
        return source.get(0).values().stream().mapToLong(List::size).sum();
    }

    @Override
    public MetaType metaType() {
        return MetaType.OdDeciduousTreatment;
    }
}
