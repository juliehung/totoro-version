package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

public abstract class SingleSourceCalculator<T> extends AbstractCalculator<T> {

    private final String sourceName;

    public SingleSourceCalculator(Collector collector, String sourceName) {
        super(collector);
        this.sourceName = sourceName;
    }

    @Override
    public String sourceName() {
        return sourceName;
    }
}
