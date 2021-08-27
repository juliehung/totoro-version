package io.dentall.totoro.business.service.nhi.metric.dto;

import io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType;
import io.dentall.totoro.business.service.nhi.metric.vm.DoctorData;

import java.math.BigDecimal;

public class NorthDistrictDto {

    private MetricSubjectType type;

    private DoctorData doctor;

    private BigDecimal a10;

    private BigDecimal a14;

    private BigDecimal a15h1;

    private BigDecimal a15h2;

    private BigDecimal a17h1;

    private BigDecimal a17h2;

    private BigDecimal a18h1;

    private BigDecimal a18h2;

    private BigDecimal a7;

    private BigDecimal a8;

    private BigDecimal a9;

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

    public BigDecimal getA10() {
        return a10;
    }

    public void setA10(BigDecimal a10) {
        this.a10 = a10;
    }

    public BigDecimal getA14() {
        return a14;
    }

    public void setA14(BigDecimal a14) {
        this.a14 = a14;
    }

    public BigDecimal getA15h1() {
        return a15h1;
    }

    public void setA15h1(BigDecimal a15h1) {
        this.a15h1 = a15h1;
    }

    public BigDecimal getA15h2() {
        return a15h2;
    }

    public void setA15h2(BigDecimal a15h2) {
        this.a15h2 = a15h2;
    }

    public BigDecimal getA17h1() {
        return a17h1;
    }

    public void setA17h1(BigDecimal a17h1) {
        this.a17h1 = a17h1;
    }

    public BigDecimal getA17h2() {
        return a17h2;
    }

    public void setA17h2(BigDecimal a17h2) {
        this.a17h2 = a17h2;
    }

    public BigDecimal getA18h1() {
        return a18h1;
    }

    public void setA18h1(BigDecimal a18h1) {
        this.a18h1 = a18h1;
    }

    public BigDecimal getA18h2() {
        return a18h2;
    }

    public void setA18h2(BigDecimal a18h2) {
        this.a18h2 = a18h2;
    }

    public BigDecimal getA7() {
        return a7;
    }

    public void setA7(BigDecimal a7) {
        this.a7 = a7;
    }

    public BigDecimal getA8() {
        return a8;
    }

    public void setA8(BigDecimal a8) {
        this.a8 = a8;
    }

    public BigDecimal getA9() {
        return a9;
    }

    public void setA9(BigDecimal a9) {
        this.a9 = a9;
    }
}
