package io.dentall.totoro.business.service.nhi.metric.dto;

import java.math.BigDecimal;

public class HighestPatientDto {

    private final Long id;

    private final BigDecimal value;

    public HighestPatientDto(Long id, BigDecimal value) {
        this.id = id;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValue() {
        return value;
    }
}
