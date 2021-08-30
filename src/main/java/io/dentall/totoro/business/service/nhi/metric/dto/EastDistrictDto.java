package io.dentall.totoro.business.service.nhi.metric.dto;

import io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType;
import io.dentall.totoro.business.service.nhi.metric.vm.DoctorData;

import java.math.BigDecimal;

public class EastDistrictDto implements DistrictDto {

    private MetricSubjectType type;

    private DoctorData doctor;

    private BigDecimal g6;

    private BigDecimal g8h1;

    private BigDecimal g8h2;

    private BigDecimal g8h3;

    private BigDecimal g8h4;

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

    public BigDecimal getG6() {
        return g6;
    }

    public void setG6(BigDecimal g6) {
        this.g6 = g6;
    }

    public BigDecimal getG8h1() {
        return g8h1;
    }

    public void setG8h1(BigDecimal g8h1) {
        this.g8h1 = g8h1;
    }

    public BigDecimal getG8h2() {
        return g8h2;
    }

    public void setG8h2(BigDecimal g8h2) {
        this.g8h2 = g8h2;
    }

    public BigDecimal getG8h3() {
        return g8h3;
    }

    public void setG8h3(BigDecimal g8h3) {
        this.g8h3 = g8h3;
    }

    public BigDecimal getG8h4() {
        return g8h4;
    }

    public void setG8h4(BigDecimal g8h4) {
        this.g8h4 = g8h4;
    }
}
