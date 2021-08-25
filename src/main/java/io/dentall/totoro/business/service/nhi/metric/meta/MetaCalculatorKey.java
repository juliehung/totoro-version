package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Key;

import java.util.Objects;

public class MetaCalculatorKey implements Key {

    private final String sourceNames;

    private final String calculatorName;

    private final MetaConfig config;

    public MetaCalculatorKey(MetaCalculator<?> calculator) {
        this.sourceNames = calculator.getSourceNames();
        this.calculatorName = calculator.getCalculatorName();
        this.config = calculator.getConfig();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MetaCalculatorKey that = (MetaCalculatorKey) o;
        return sourceNames.equals(that.sourceNames) && calculatorName.equals(that.calculatorName) && config.equals(that.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceNames, calculatorName, config);
    }
}
