package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

public abstract class AbstractCalculator implements Calculator {

    private final String sourceName;

    private Meta meta;

    public AbstractCalculator(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public Meta calculate(Collector collector) {
        String key = storeKey();

        if (!collector.isMetaExist(key)) {
            Long result = doCalculate(collector);
            collector.storeMeta(key, new Meta(metaType(), result));
        }

        this.meta = collector.retrieveMeta(key);

        return this.meta;
    }

    public String sourceName() {
        return this.sourceName;
    }

    public String storeKey() {
        return storeKey(metaType());
    }

    public String storeKey(MetaType metaType) {
        return sourceName() + ":" + metaType.name();
    }

    public Meta getMeta() {
        return this.meta;
    }

    public Long getResult() {
        return this.meta.value();
    }

    public abstract Long doCalculate(Collector collector);

    public abstract MetaType metaType();
}
