package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

public abstract class AbstractCalculator implements Calculator<Meta> {

    private final Collector collector;

    private Meta meta;

    public AbstractCalculator(Collector collector) {
        this.collector = collector;
    }

    @Override
    public Meta calculate() {
        String key = storeKey();

        if (!collector.isMetaExist(key)) {
            Long result = doCalculate(collector);
            collector.storeMeta(key, new Meta(metaType(), result));
        }

        this.meta = collector.retrieveMeta(key);

        return this.meta;
    }

    public String storeKey() {
        return sourceName() + ":" + metaType().name();
    }

    public Meta getMeta() {
        return this.meta;
    }

    public Long getResult() {
        return this.meta.value();
    }

    public abstract Long doCalculate(Collector collector);

    public abstract MetaType metaType();

    public abstract String sourceName();
}
