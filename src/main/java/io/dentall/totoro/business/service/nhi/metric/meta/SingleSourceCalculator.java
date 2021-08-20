package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

public abstract class SingleSourceCalculator<T> extends AbstractCalculator<T> {

    private final Source<?, ?> source;

    public SingleSourceCalculator(Collector collector, Source<?, ?> source) {
        this(collector, null, source);
    }

    public SingleSourceCalculator(Collector collector, MetaConfig config, Source<?, ?> source) {
        super(collector, config);
        this.source = source;
    }

    public Source<?, ?> source() {
        return source;
    }

    @Override
    public String sourceName() {
        return source.name();
    }
}
