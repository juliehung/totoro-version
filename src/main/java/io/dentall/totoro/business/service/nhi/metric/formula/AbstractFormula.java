package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.Calculator;

import java.math.BigDecimal;

public abstract class AbstractFormula implements Calculator<BigDecimal> {

    private final Collector collector;

    public AbstractFormula(Collector collector) {
        this.collector = collector;
    }

    @Override
    public BigDecimal calculate() {
        return doCalculate(collector);
    }

    protected abstract BigDecimal doCalculate(Collector collector);
}
