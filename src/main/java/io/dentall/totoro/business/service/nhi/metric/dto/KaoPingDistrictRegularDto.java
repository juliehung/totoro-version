package io.dentall.totoro.business.service.nhi.metric.dto;

import io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType;
import io.dentall.totoro.business.service.nhi.metric.vm.DoctorData;

import java.math.BigDecimal;

public class KaoPingDistrictRegularDto implements DistrictDto {

    private MetricSubjectType type;

    private DoctorData doctor;

    private BigDecimal k1;

    private BigDecimal k2;

    private BigDecimal k11;

    private BigDecimal j2h5;

    private BigDecimal k10;

    private BigDecimal k3;

    private BigDecimal k4;

    private BigDecimal k5;

    private BigDecimal k6;

    private BigDecimal k7;

    private BigDecimal k12;

    private BigDecimal k14;


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

    public BigDecimal getK1() {
        return k1;
    }

    public void setK1(BigDecimal k1) {
        this.k1 = k1;
    }

    public BigDecimal getK2() {
        return k2;
    }

    public void setK2(BigDecimal k2) {
        this.k2 = k2;
    }

    public BigDecimal getK11() {
        return k11;
    }

    public void setK11(BigDecimal k11) {
        this.k11 = k11;
    }

    public BigDecimal getJ2h5() {
        return j2h5;
    }

    public void setJ2h5(BigDecimal j2h5) {
        this.j2h5 = j2h5;
    }

    public BigDecimal getK10() {
        return k10;
    }

    public void setK10(BigDecimal k10) {
        this.k10 = k10;
    }

    public BigDecimal getK3() {
        return k3;
    }

    public void setK3(BigDecimal k3) {
        this.k3 = k3;
    }

    public BigDecimal getK4() {
        return k4;
    }

    public void setK4(BigDecimal k4) {
        this.k4 = k4;
    }

    public BigDecimal getK5() {
        return k5;
    }

    public void setK5(BigDecimal k5) {
        this.k5 = k5;
    }

    public BigDecimal getK6() {
        return k6;
    }

    public void setK6(BigDecimal k6) {
        this.k6 = k6;
    }

    public BigDecimal getK7() {
        return k7;
    }

    public void setK7(BigDecimal k7) {
        this.k7 = k7;
    }

    public BigDecimal getK12() {
        return k12;
    }

    public void setK12(BigDecimal k12) {
        this.k12 = k12;
    }

    public BigDecimal getK14() {
        return k14;
    }

    public void setK14(BigDecimal k14) {
        this.k14 = k14;
    }
}
