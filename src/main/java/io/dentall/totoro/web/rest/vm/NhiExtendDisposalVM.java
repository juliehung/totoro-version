package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.Patient;

import java.io.Serializable;

public class NhiExtendDisposalVM implements Serializable {

    @JsonProperty
    private String name;

    @JsonProperty
    private Boolean vipPatient;

    @JsonProperty
    private String doctor;

    @JsonProperty
    private String medicalId;

    @JsonUnwrapped
    private NhiExtendDisposal nhiExtendDisposal;

    private Long dependedDisposalId;

    public NhiExtendDisposalVM() {

    }

    public NhiExtendDisposalVM(NhiExtendDisposal nhiExtendDisposal) {
        Patient patient = nhiExtendDisposal.getDisposal().getRegistration().getAppointment().getPatient();
        name = patient.getName();
        vipPatient = patient.getVipPatient();
        medicalId = patient.getMedicalId();
        doctor = nhiExtendDisposal.getDisposal().getCreatedBy();

        this.nhiExtendDisposal = nhiExtendDisposal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVipPatient() { return vipPatient; }

    public void setVipPatient(Boolean vipPatient) { this.vipPatient = vipPatient; }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getMedicalId() {
        return medicalId;
    }

    public void setMedicalId(String medicalId) {
        this.medicalId = medicalId;
    }

    public NhiExtendDisposal getNhiExtendDisposal() {
        return nhiExtendDisposal;
    }

    public void setNhiExtendDisposal(NhiExtendDisposal nhiExtendDisposal) {
        this.nhiExtendDisposal = nhiExtendDisposal;
    }

    public Long getDependedDisposalId() {
        return dependedDisposalId;
    }

    public void setDependedDisposalId(Long dependedDisposalId) {
        this.dependedDisposalId = dependedDisposalId;
    }
}
