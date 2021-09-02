package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

public abstract class AbstractMetaCalculator<T> implements MetaCalculator<T> {

    private final MetricConfig metricConfig;

    private Meta<T> meta;

    private final MetaConfig config;

    private final Source<?, ?>[] sources;

    public AbstractMetaCalculator(MetricConfig metricConfig, MetaConfig config, Source<?, ?>[] sources) {
        this.metricConfig = metricConfig;
        this.config = ofNullable(config).orElse(new MetaConfig(this.metricConfig));
        this.sources = sources;
    }

    @Override
    public Meta<T> calculate() {
        if (!metricConfig.isMetaExist(key())) {
            T result = doCalculate(metricConfig);
            metricConfig.storeMeta(key(), new Meta<>(metaType(), result));
        }

        this.meta = metricConfig.retrieveMeta(key());
        return this.meta;
    }

    protected abstract MetaType metaType();

    public final T getResult() {
        return this.meta.value();
    }

    public abstract T doCalculate(MetricConfig metricConfig);

    public final MetaCalculatorKey key() {
        return new MetaCalculatorKey(this);
    }

    public final Meta<T> getMeta() {
        return this.meta;
    }

    @Override
    public final String getSourceNames() {
        return Arrays.stream(sources).map(source -> source.getClass().getName()).collect(joining("+"));
    }

    @Override
    public final String getCalculatorName() {
        return this.getClass().getName();
    }

    @Override
    public MetaConfig getConfig() {
        return this.config;
    }

    @Override
    public List<?> getExtraKeyAttribute() {
        return null;
    }
}
