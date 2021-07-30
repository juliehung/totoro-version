package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.Calculator;

import java.math.BigDecimal;

public abstract class AbstractFormula implements Formula {

    private Collector collector;

    @Override
    public BigDecimal calculate(Collector collector) {
        this.collector = collector;
        return doCalculate();
    }

    protected <T extends Calculator> T apply(T calculator) {
        this.collector.apply(calculator);
        return calculator;
    }

    protected abstract BigDecimal doCalculate();
}
