package io.dentall.totoro.business.service.nhi.metric.dto;

import io.dentall.totoro.business.service.nhi.NhiSpecialCode;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

public class MetricTreatment implements NhiMetricRawVM {

    Long patientId;

    String patientName;

    LocalDate patientBirth;

    Long disposalId;

    // jhi_date
    LocalDate disposalDate;

    // A18
    String cardNumber;

    // A19
    String cardReplenishment;

    // A23
    String nhiCategory;

    // A32
    String partialBurden;

    // replenishment date
    LocalDate cardReplenishmentDisposalDate;

    String examCode;

    String examPoint;

    String patientIdentity;

    String serialNumber;

    String treatmentProcedureCode;

    String treatmentProcedureTooth;

    String treatmentProcedureSurface;

    Long treatmentProcedureTotal;

    Long nhiOriginPoint;

    @Enumerated(EnumType.STRING)
    NhiSpecialCode treatmentProcedureSpecificCode;

    Long doctorId;

    String doctorName;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public LocalDate getPatientBirth() {
        return patientBirth;
    }

    public void setPatientBirth(LocalDate patientBirth) {
        this.patientBirth = patientBirth;
    }

    public Long getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(Long disposalId) {
        this.disposalId = disposalId;
    }

    public LocalDate getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(LocalDate disposalDate) {
        this.disposalDate = disposalDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardReplenishment() {
        return cardReplenishment;
    }

    public void setCardReplenishment(String cardReplenishment) {
        this.cardReplenishment = cardReplenishment;
    }

    public String getNhiCategory() {
        return nhiCategory;
    }

    public void setNhiCategory(String nhiCategory) {
        this.nhiCategory = nhiCategory;
    }

    public String getPartialBurden() {
        return partialBurden;
    }

    public void setPartialBurden(String partialBurden) {
        this.partialBurden = partialBurden;
    }

    public LocalDate getCardReplenishmentDisposalDate() {
        return cardReplenishmentDisposalDate;
    }

    public void setCardReplenishmentDisposalDate(LocalDate cardReplenishmentDisposalDate) {
        this.cardReplenishmentDisposalDate = cardReplenishmentDisposalDate;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getExamPoint() {
        return examPoint;
    }

    public void setExamPoint(String examPoint) {
        this.examPoint = examPoint;
    }

    public String getPatientIdentity() {
        return patientIdentity;
    }

    public void setPatientIdentity(String patientIdentity) {
        this.patientIdentity = patientIdentity;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTreatmentProcedureCode() {
        return treatmentProcedureCode;
    }

    public void setTreatmentProcedureCode(String treatmentProcedureCode) {
        this.treatmentProcedureCode = treatmentProcedureCode;
    }

    public String getTreatmentProcedureTooth() {
        return treatmentProcedureTooth;
    }

    public void setTreatmentProcedureTooth(String treatmentProcedureTooth) {
        this.treatmentProcedureTooth = treatmentProcedureTooth;
    }

    public String getTreatmentProcedureSurface() {
        return treatmentProcedureSurface;
    }

    public void setTreatmentProcedureSurface(String treatmentProcedureSurface) {
        this.treatmentProcedureSurface = treatmentProcedureSurface;
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

    public NhiSpecialCode getTreatmentProcedureSpecificCode() {
        return treatmentProcedureSpecificCode;
    }

    public void setTreatmentProcedureSpecificCode(NhiSpecialCode treatmentProcedureSpecificCode) {
        this.treatmentProcedureSpecificCode = treatmentProcedureSpecificCode;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
