package io.dentall.totoro.business.service.nhi.metric.meta;

public interface Calculator<R> {

    R calculate();

    default <T extends Calculator<R>> T apply() {
        calculate();
        return (T) this;
    }
}
