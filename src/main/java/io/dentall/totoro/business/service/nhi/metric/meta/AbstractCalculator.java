package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

import java.util.Optional;

public abstract class AbstractCalculator<T> implements Calculator<Meta<T>> {

    private final Collector collector;

    private Meta<T> meta;

    private Exclude exclude;

    private MetaConfig config;

    public AbstractCalculator(Collector collector) {
        this(collector, new MetaConfig(collector));
    }

    public AbstractCalculator(Collector collector, MetaConfig config) {
        this.collector = collector;
        this.config = Optional.ofNullable(config).orElse(new MetaConfig(this.collector));
        this.exclude = this.config.getExclude();
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

        String extractKey = extraKey();
        if (extractKey != null) {
            key += "+" + extractKey;
        }

        Exclude exclude = getExclude();
        if (exclude != null) {
            key += ":" + exclude.name();
        }
        return key;
    }

    protected MetaConfig getConfig() {
        return this.config;
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

    public String extraKey() {
        return null;
    }
}
