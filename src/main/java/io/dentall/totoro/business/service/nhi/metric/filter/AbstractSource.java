package io.dentall.totoro.business.service.nhi.metric.filter;

public abstract class AbstractSource<S, R> implements Source<S, R> {

    private final Collector collector;

    public AbstractSource(Collector collector) {
        this.collector = collector;
    }

}
