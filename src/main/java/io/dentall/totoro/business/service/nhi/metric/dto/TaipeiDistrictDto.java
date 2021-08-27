package io.dentall.totoro.business.service.nhi.metric.dto;

import io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType;
import io.dentall.totoro.business.service.nhi.metric.vm.DoctorData;

import java.math.BigDecimal;

public class TaipeiDistrictDto  implements DistrictDto {

    private MetricSubjectType type;

    private DoctorData doctor;

    private BigDecimal F1h1;

    private BigDecimal F1h2;

    private BigDecimal F1h3;

    private BigDecimal F2h4;

    private BigDecimal F3h1;

    private BigDecimal F3h2;

    private BigDecimal F4h3;

    private BigDecimal F5h3;

    private BigDecimal F5h4;

    private BigDecimal F5h5;

    private BigDecimal F5h6;

    private BigDecimal F5h7;

    private BigDecimal F5h8;

    private BigDecimal l1;

    private BigDecimal i12;

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

    public BigDecimal getF1h1() {
        return F1h1;
    }

    public void setF1h1(BigDecimal f1h1) {
        F1h1 = f1h1;
    }

    public BigDecimal getF1h2() {
        return F1h2;
    }

    public void setF1h2(BigDecimal f1h2) {
        F1h2 = f1h2;
    }

    public BigDecimal getF1h3() {
        return F1h3;
    }

    public void setF1h3(BigDecimal f1h3) {
        F1h3 = f1h3;
    }

    public BigDecimal getF2h4() {
        return F2h4;
    }

    public void setF2h4(BigDecimal f2h4) {
        F2h4 = f2h4;
    }

    public BigDecimal getF3h1() {
        return F3h1;
    }

    public void setF3h1(BigDecimal f3h1) {
        F3h1 = f3h1;
    }

    public BigDecimal getF3h2() {
        return F3h2;
    }

    public void setF3h2(BigDecimal f3h2) {
        F3h2 = f3h2;
    }

    public BigDecimal getF4h3() {
        return F4h3;
    }

    public void setF4h3(BigDecimal f4h3) {
        F4h3 = f4h3;
    }

    public BigDecimal getF5h3() {
        return F5h3;
    }

    public void setF5h3(BigDecimal f5h3) {
        F5h3 = f5h3;
    }

    public BigDecimal getF5h4() {
        return F5h4;
    }

    public void setF5h4(BigDecimal f5h4) {
        F5h4 = f5h4;
    }

    public BigDecimal getF5h5() {
        return F5h5;
    }

    public void setF5h5(BigDecimal f5h5) {
        F5h5 = f5h5;
    }

    public BigDecimal getF5h6() {
        return F5h6;
    }

    public void setF5h6(BigDecimal f5h6) {
        F5h6 = f5h6;
    }

    public BigDecimal getF5h7() {
        return F5h7;
    }

    public void setF5h7(BigDecimal f5h7) {
        F5h7 = f5h7;
    }

    public BigDecimal getF5h8() {
        return F5h8;
    }

    public void setF5h8(BigDecimal f5h8) {
        F5h8 = f5h8;
    }

    public BigDecimal getL1() {
        return l1;
    }

    public void setL1(BigDecimal l1) {
        this.l1 = l1;
    }

    public BigDecimal getI12() {
        return i12;
    }

    public void setI12(BigDecimal i12) {
        this.i12 = i12;
    }
}
