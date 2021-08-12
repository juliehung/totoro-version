package io.dentall.totoro.business.service.nhi.metric.dto;

public class SummaryDto {
    // 合計點數
    private Long total = 0L;
    // 感染診察費
    private Long infectionExaminationPoint = 0L;
    // 一般診察點數
    private Long regularExaminationPoint = 0L;
    // 診察費合計(扣除感控差額)
    private Long pureExaminationPoint = 0L;
    // 診療費
    private Long treatmentPoint = 0L;
    // 部份負擔
    private Long copayment = 0L;
    // 牙周專科
    private Long periodPoint = 0L;
    // 兒童專科
    private Long pedoPoint = 0L;
    // 根管專科
    private Long endoPoint = 0L;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getInfectionExaminationPoint() {
        return infectionExaminationPoint;
    }

    public void setInfectionExaminationPoint(Long infectionExaminationPoint) {
        this.infectionExaminationPoint = infectionExaminationPoint;
    }

    public Long getRegularExaminationPoint() {
        return regularExaminationPoint;
    }

    public void setRegularExaminationPoint(Long regularExaminationPoint) {
        this.regularExaminationPoint = regularExaminationPoint;
    }

    public Long getPureExaminationPoint() {
        return pureExaminationPoint;
    }

    public void setPureExaminationPoint(Long pureExaminationPoint) {
        this.pureExaminationPoint = pureExaminationPoint;
    }

    public Long getTreatmentPoint() {
        return treatmentPoint;
    }

    public void setTreatmentPoint(Long treatmentPoint) {
        this.treatmentPoint = treatmentPoint;
    }

    public Long getCopayment() {
        return copayment;
    }

    public void setCopayment(Long copayment) {
        this.copayment = copayment;
    }

    public Long getPeriodPoint() {
        return periodPoint;
    }

    public void setPeriodPoint(Long periodPoint) {
        this.periodPoint = periodPoint;
    }

    public Long getPedoPoint() {
        return pedoPoint;
    }

    public void setPedoPoint(Long pedoPoint) {
        this.pedoPoint = pedoPoint;
    }

    public Long getEndoPoint() {
        return endoPoint;
    }

    public void setEndoPoint(Long endoPoint) {
        this.endoPoint = endoPoint;
    }
}
