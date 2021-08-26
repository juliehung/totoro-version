package io.dentall.totoro.business.service.nhi.metric.dto;

import io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType;
import io.dentall.totoro.business.service.nhi.metric.vm.DoctorData;

import java.math.BigDecimal;
import java.util.List;

public class KaoPingDistrictReductionDto {

    private MetricSubjectType type;

    private DoctorData doctor;

    private List<DoctorPoint1Dto> j1h1;

    private BigDecimal j1h2;

    private BigDecimal j2h2;

    private BigDecimal j2h3;

    private BigDecimal j2h4;

    private BigDecimal j2h5;

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

    public List<DoctorPoint1Dto> getJ1h1() {
        return j1h1;
    }

    public void setJ1h1(List<DoctorPoint1Dto> j1h1) {
        this.j1h1 = j1h1;
    }

    public BigDecimal getJ1h2() {
        return j1h2;
    }

    public void setJ1h2(BigDecimal j1h2) {
        this.j1h2 = j1h2;
    }

    public BigDecimal getJ2h2() {
        return j2h2;
    }

    public void setJ2h2(BigDecimal j2h2) {
        this.j2h2 = j2h2;
    }

    public BigDecimal getJ2h3() {
        return j2h3;
    }

    public void setJ2h3(BigDecimal j2h3) {
        this.j2h3 = j2h3;
    }

    public BigDecimal getJ2h4() {
        return j2h4;
    }

    public void setJ2h4(BigDecimal j2h4) {
        this.j2h4 = j2h4;
    }

    public BigDecimal getJ2h5() {
        return j2h5;
    }

    public void setJ2h5(BigDecimal j2h5) {
        this.j2h5 = j2h5;
    }
}
