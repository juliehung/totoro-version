package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

public abstract class AbstractCalculator<T> implements Calculator<Meta<T>> {

    private final Collector collector;

    private Meta<T> meta;

    private Exclude exclude;

    public AbstractCalculator(Collector collector) {
        this.collector = collector;
    }

    public AbstractCalculator(Collector collector, Exclude exclude) {
        this.collector = collector;
        this.exclude = exclude;
    }

    @Override
    public Meta<T> calculate() {
        String key = storeKey();

        if (!collector.isMetaExist(key)) {
            T result = doCalculate(collector);
            collector.storeMeta(key, new Meta<>(metaType(), result));
        }

        this.meta = collector.retrieveMeta(key);

        return this.meta;
    }

    public Exclude getExclude() {
        return exclude;
    }

    public String storeKey() {
        String key = sourceName() + ":" + metaType().name();

        String extractKey = extractKey();
        if (extractKey != null) {
            key += "+" + extractKey;
        }

        Exclude exclude = getExclude();
        if (exclude != null) {
            key += ":" + exclude.name();
        }
        return key;
    }

    public Meta<T> getMeta() {
        return this.meta;
    }

    public T getResult() {
        return this.meta.value();
    }

    public abstract T doCalculate(Collector collector);

    public abstract MetaType metaType();

    public abstract String sourceName();

    public String extractKey() {
        return null;
    }
}
