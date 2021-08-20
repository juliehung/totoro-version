package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.Calculator;

public abstract class AbstractFormula<R> implements Calculator<R> {

    private final Collector collector;

    public AbstractFormula(Collector collector) {
        this.collector = collector;
    }

    @Override
    public R calculate() {
        return doCalculate(collector);
    }

    protected abstract R doCalculate(Collector collector);
}
