package io.dentall.totoro.business.service.nhi.metric.meta;

public interface MetaCalculator<R> extends Calculator<Meta<R>> {

    String getSourceNames();

    String getCalculatorName();

    MetaConfig getConfig();

}
