package io.dentall.totoro.business.service.nhi.metric.dto;

import io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType;
import io.dentall.totoro.business.service.nhi.metric.vm.DoctorData;

import java.math.BigDecimal;

public class MiddleDistrictDto {

    private MetricSubjectType type;

    private DoctorData doctor;

    private BigDecimal h1;

    private BigDecimal h2;

    private BigDecimal h3;

    private BigDecimal h4;

    private BigDecimal h5;

    private BigDecimal h7;

    public MetricSubjectType getType() {
        return type;
    }

    public void setType(MetricSubjectType type) {
        this.type = type;
    }

    public DoctorData getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorData doctor) {
        this.doctor = doctor;
    }

    public BigDecimal getH1() {
        return h1;
    }

    public void setH1(BigDecimal h1) {
        this.h1 = h1;
    }

    public BigDecimal getH2() {
        return h2;
    }

    public void setH2(BigDecimal h2) {
        this.h2 = h2;
    }

    public BigDecimal getH3() {
        return h3;
    }

    public void setH3(BigDecimal h3) {
        this.h3 = h3;
    }

    public BigDecimal getH4() {
        return h4;
    }

    public void setH4(BigDecimal h4) {
        this.h4 = h4;
    }

    public BigDecimal getH5() {
        return h5;
    }

    public void setH5(BigDecimal h5) {
        this.h5 = h5;
    }

    public BigDecimal getH7() {
        return h7;
    }

    public void setH7(BigDecimal h7) {
        this.h7 = h7;
    }
}
