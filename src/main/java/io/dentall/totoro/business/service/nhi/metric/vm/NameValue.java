package io.dentall.totoro.business.service.nhi.metric.vm;

import java.math.BigDecimal;

public class NameValue {

    private String name;

    private BigDecimal value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
