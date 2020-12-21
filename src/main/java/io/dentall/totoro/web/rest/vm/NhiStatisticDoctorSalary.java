package io.dentall.totoro.web.rest.vm;

import java.time.Instant;

public class NhiStatisticDoctorSalary {

    /**
     * 總點數
     */
    private Long total = 0L;

    /**
     * 感染控制診察點數
     */
    private Long infectionExaminationPoint = 0L;

    /**
     * 一般診察點數
     */
    private Long regularExaminationPoint = 0L;

    /**
     * 診療點數
     */
    private Long treatmentPoint = 0L;

    /**
     * 牙周類型點數
     */
    private Long perioPoint = 0L;

    /**
     * 兒童類型點數
     */
    private Long pedoPoint = 0L;

    /**
     * 根管類型點數
     */
    private Long endoPoint = 0L;

    /**
     * 總處置單數量
     */
    private Long totalDisposal = 0L;

    /**
     * 總部分負擔
     */
    private Long copayment = 0L;

    /**
     * 病患號碼(expand 才有)
     */
    private Long patientId;

    /**
     * 病患姓名(expand 才有)
     */
    private String patientName;

    /**
     * 治療時間(expand 才有)
     */
    private Instant disposalDate;

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Instant getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(Instant disposalDate) {
        this.disposalDate = disposalDate;
    }

    public Long getCopayment() {
        return copayment;
    }

    public void setCopayment(Long copayment) {
        this.copayment = copayment;
    }

    public Long getTotalDisposal() {
        return totalDisposal;
    }

    public void setTotalDisposal(Long totalDisposal) {
        this.totalDisposal = totalDisposal;
    }

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

    public Long getTreatmentPoint() {
        return treatmentPoint;
    }

    public void setTreatmentPoint(Long treatmentPoint) {
        this.treatmentPoint = treatmentPoint;
    }

    public Long getPerioPoint() {
        return perioPoint;
    }

    public void setPerioPoint(Long perioPoint) {
        this.perioPoint = perioPoint;
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
