package io.dentall.totoro.business.service.nhi.metric.dto;

import io.dentall.totoro.business.service.nhi.NhiSpecialCode;

import java.time.LocalDate;

public class OdDto {

    private long patientId;

    private long disposalId;

    private LocalDate disposalDate;

    private String code;

    private String tooth;

    private String surface;

    private String category;

    private NhiSpecialCode specificCode;

    private String cardNumber;

    private Long treatmentProcedureTotal;

    private Long nhiOriginPoint;

    // 需要有方法可以辨別出，同一處置單不同處置(但同一個健保代碼)，所以利用該屬性，只要是同一處置，就會是一樣的seq
    // 即使是相同的健保代碼，但不同處置就會不一樣的seq
    private int treatmentSeq;

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(long disposalId) {
        this.disposalId = disposalId;
    }

    public LocalDate getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(LocalDate disposalDate) {
        this.disposalDate = disposalDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTooth() {
        return tooth;
    }

    public void setTooth(String tooth) {
        this.tooth = tooth;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public NhiSpecialCode getSpecificCode() {
        return specificCode;
    }

    public void setSpecificCode(NhiSpecialCode specificCode) {
        this.specificCode = specificCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getTreatmentProcedureTotal() {
        return treatmentProcedureTotal;
    }

    public void setTreatmentProcedureTotal(Long treatmentProcedureTotal) {
        this.treatmentProcedureTotal = treatmentProcedureTotal;
    }

    public Long getNhiOriginPoint() {
        return nhiOriginPoint;
    }

    public void setNhiOriginPoint(Long nhiOriginPoint) {
        this.nhiOriginPoint = nhiOriginPoint;
    }

    public int getTreatmentSeq() {
        return treatmentSeq;
    }

    public void setTreatmentSeq(int treatmentSeq) {
        this.treatmentSeq = treatmentSeq;
    }
}
