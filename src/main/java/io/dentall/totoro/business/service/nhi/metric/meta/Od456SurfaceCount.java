package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

/**
 * @ OD-4@齒數
 * @ OD-5@齒數
 * @ OD-6@齒數
 */
public class Od456SurfaceCount extends SingleSourceCalculator<Long> {

    public Od456SurfaceCount(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        Od4ToothCount od4ToothCount = new Od4ToothCount(collector, sourceName()).apply();
        Od5ToothCount od5ToothCount = new Od5ToothCount(collector, sourceName()).apply();
        Od6ToothCount od6ToothCount = new Od6ToothCount(collector, sourceName()).apply();
        return od4ToothCount.getResult() + 2 * od5ToothCount.getResult() + 3 * od6ToothCount.getResult();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Od456SurfaceCount;
    }
}
