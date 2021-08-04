package io.dentall.totoro.business.service.nhi.metric.vm;

import java.math.BigDecimal;

public class MetricData {

    private BigDecimal value;

    public MetricData(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

}
