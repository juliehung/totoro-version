package io.dentall.totoro.business.service.nhi.metric.dto;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;

public class OdDto implements NhiMetricRawVM {

    private Long patientId;

    private String patientName;

    private LocalDate patientBirth;

    private Long disposalId;

    private LocalDate disposalDate;

    private String cardNumber;

    private String cardReplenishment;

    private String nhiCategory;

    private String partialBurden;

    private LocalDate cardReplenishmentDisposalDate;

    private String examCode;

    private String examPoint;

    private String patientIdentity;

    private String serialNumber;

    private String treatmentProcedureCode;

    private String treatmentProcedureTooth;

    private String treatmentProcedureSurface;

    private Long treatmentProcedureTotal;

    private Long nhiOriginPoint;

    private String treatmentProcedureSpecificCode;

    private Long doctorId;

    private String doctorName;

    // 需要有方法可以辨別出，同一處置單不同診療(但同一個健保代碼)，所以利用該屬性，只要是同一診療，就會是一樣的seq
    // 即使是相同的健保代碼，但不同診療就會不一樣的seq
    private int treatmentSeq;

    @Override
    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Override
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    @Override
    public LocalDate getPatientBirth() {
        return patientBirth;
    }

    public void setPatientBirth(LocalDate patientBirth) {
        this.patientBirth = patientBirth;
    }

    @Override
    public Long getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(Long disposalId) {
        this.disposalId = disposalId;
    }

    @Override
    public LocalDate getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(LocalDate disposalDate) {
        this.disposalDate = disposalDate;
    }

    @Override
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String getCardReplenishment() {
        return cardReplenishment;
    }

    public void setCardReplenishment(String cardReplenishment) {
        this.cardReplenishment = cardReplenishment;
    }

    @Override
    public String getNhiCategory() {
        return nhiCategory;
    }

    public void setNhiCategory(String nhiCategory) {
        this.nhiCategory = nhiCategory;
    }

    @Override
    public String getPartialBurden() {
        return partialBurden;
    }

    public void setPartialBurden(String partialBurden) {
        this.partialBurden = partialBurden;
    }

    @Override
    public LocalDate getCardReplenishmentDisposalDate() {
        return cardReplenishmentDisposalDate;
    }

    public void setCardReplenishmentDisposalDate(LocalDate cardReplenishmentDisposalDate) {
        this.cardReplenishmentDisposalDate = cardReplenishmentDisposalDate;
    }

    @Override
    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    @Override
    public String getExamPoint() {
        return examPoint;
    }

    public void setExamPoint(String examPoint) {
        this.examPoint = examPoint;
    }

    @Override
    public String getPatientIdentity() {
        return patientIdentity;
    }

    public void setPatientIdentity(String patientIdentity) {
        this.patientIdentity = patientIdentity;
    }

    @Override
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String getTreatmentProcedureCode() {
        return treatmentProcedureCode;
    }

    public void setTreatmentProcedureCode(String treatmentProcedureCode) {
        this.treatmentProcedureCode = treatmentProcedureCode;
    }

    @Override
    public String getTreatmentProcedureTooth() {
        return treatmentProcedureTooth;
    }

    public void setTreatmentProcedureTooth(String treatmentProcedureTooth) {
        this.treatmentProcedureTooth = treatmentProcedureTooth;
    }

    @Override
    public String getTreatmentProcedureSurface() {
        return treatmentProcedureSurface;
    }

    public void setTreatmentProcedureSurface(String treatmentProcedureSurface) {
        this.treatmentProcedureSurface = treatmentProcedureSurface;
    }

    @Override
    public Long getTreatmentProcedureTotal() {
        return treatmentProcedureTotal;
    }

    public void setTreatmentProcedureTotal(Long treatmentProcedureTotal) {
        this.treatmentProcedureTotal = treatmentProcedureTotal;
    }

    @Override
    public Long getNhiOriginPoint() {
        return nhiOriginPoint;
    }

    public void setNhiOriginPoint(Long nhiOriginPoint) {
        this.nhiOriginPoint = nhiOriginPoint;
    }

    @Override
    public String getTreatmentProcedureSpecificCode() {
        return treatmentProcedureSpecificCode;
    }

    public void setTreatmentProcedureSpecificCode(String treatmentProcedureSpecificCode) {
        this.treatmentProcedureSpecificCode = treatmentProcedureSpecificCode;
    }

    @Override
    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public int getTreatmentSeq() {
        return treatmentSeq;
    }

    public void setTreatmentSeq(int treatmentSeq) {
        this.treatmentSeq = treatmentSeq;
    }
}
