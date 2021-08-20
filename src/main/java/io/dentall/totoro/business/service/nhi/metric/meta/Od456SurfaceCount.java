package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

/**
 * @ OD-4@齒數
 * @ OD-5@齒數
 * @ OD-6@齒數
 */
public class Od456SurfaceCount extends SingleSourceCalculator<Long> {

    public Od456SurfaceCount(Collector collector, Source<?, ?> source) {
        this(collector, null, source);
    }

    public Od456SurfaceCount(Collector collector, MetaConfig config, Source<?, ?> source) {
        super(collector, config, source);
    }

    @Override
    public Long doCalculate(Collector collector) {
        MetaConfig config = getConfig();
        Od4TreatmentCount od4TreatmentCount = new Od4TreatmentCount(collector, config, source()).apply();
        Od5TreatmentCount od5TreatmentCount = new Od5TreatmentCount(collector, config, source()).apply();
        Od6TreatmentCount od6TreatmentCount = new Od6TreatmentCount(collector, config, source()).apply();
        return od4TreatmentCount.getResult() + 2 * od5TreatmentCount.getResult() + 3 * od6TreatmentCount.getResult();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Od456SurfaceCount;
    }
}
