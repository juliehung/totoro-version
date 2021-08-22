package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.Calculator;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;

public abstract class AbstractFormula<R> implements Calculator<R> {

    private final MetricConfig metricConfig;

    public AbstractFormula(MetricConfig metricConfig) {
        this.metricConfig = metricConfig;
    }

    @Override
    public R calculate() {
        return doCalculate(metricConfig);
    }

    protected abstract R doCalculate(MetricConfig metricConfig);
}
