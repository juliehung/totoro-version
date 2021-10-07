package io.dentall.totoro.business.service.nhi.metric.dto;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;

public class MetricTooth implements NhiMetricRawVM {

    private final MetricTreatment treatment;

    private final String tooth;

    public MetricTooth(MetricTreatment treatment, String tooth) {
        this.treatment = treatment;
        this.tooth = tooth;
    }

    public MetricTreatment getTreatment() {
        return treatment;
    }

    public String getTooth() {
        return tooth;
    }

    @Override
    public Long getPatientId() {
        return treatment.getPatientId();
    }

    @Override
    public String getPatientName() {
        return treatment.getPatientName();
    }

    @Override
    public LocalDate getPatientBirth() {
        return treatment.getPatientBirth();
    }

    @Override
    public Long getDisposalId() {
        return treatment.getDisposalId();
    }

    @Override
    public LocalDate getDisposalDate() {
        return treatment.getDisposalDate();
    }

    @Override
    public String getCardNumber() {
        return treatment.getCardNumber();
    }

    @Override
    public String getCardReplenishment() {
        return treatment.getCardReplenishment();
    }

    @Override
    public String getNhiCategory() {
        return treatment.getNhiCategory();
    }

    @Override
    public String getPartialBurden() {
        return treatment.getPartialBurden();
    }

    @Override
    public LocalDate getCardReplenishmentDisposalDate() {
        return treatment.getCardReplenishmentDisposalDate();
    }

    @Override
    public String getExamCode() {
        return treatment.getExamCode();
    }

    @Override
    public String getExamPoint() {
        return treatment.getExamPoint();
    }

    @Override
    public String getPatientIdentity() {
        return treatment.getPatientIdentity();
    }

    @Override
    public String getSerialNumber() {
        return treatment.getSerialNumber();
    }

    @Override
    public String getTreatmentProcedureCode() {
        return treatment.getTreatmentProcedureCode();
    }

    @Override
    public String getTreatmentProcedureTooth() {
        return treatment.getTreatmentProcedureTooth();
    }

    @Override
    public String getTreatmentProcedureSurface() {
        return treatment.getTreatmentProcedureSurface();
    }

    @Override
    public Long getTreatmentProcedureTotal() {
        return treatment.getTreatmentProcedureTotal();
    }

    @Override
    public Long getNhiOriginPoint() {
        return treatment.getNhiOriginPoint();
    }

    @Override
    public String getTreatmentProcedureSpecificCode() {
        return treatment.getTreatmentProcedureSpecificCode();
    }

    @Override
    public Long getDoctorId() {
        return treatment.getDoctorId();
    }

    @Override
    public String getDoctorName() {
        return treatment.getDoctorName();
    }

}
