package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

public abstract class SingleSourceCalculator<T> extends AbstractCalculator<T> {

    private final String sourceName;

    public SingleSourceCalculator(Collector collector, String sourceName) {
        this(collector, null, sourceName);
    }

    public SingleSourceCalculator(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config);
        this.sourceName = sourceName;
    }

    @Override
    public String sourceName() {
        return sourceName;
    }
}
