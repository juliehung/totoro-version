package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.Patient;

import java.io.Serializable;

public class NhiExtendDisposalVM extends NhiExtendDisposal implements Serializable {

    @JsonProperty
    private String name;

    @JsonProperty
    private String doctor;

    @JsonProperty
    private String medicalId;

    @JsonUnwrapped
    private NhiExtendDisposal nhiExtendDisposal;

    public NhiExtendDisposalVM(NhiExtendDisposal nhiExtendDisposal) {
        Patient patient = nhiExtendDisposal.getDisposal().getRegistration().getAppointment().getPatient();
        name = patient.getName();
        medicalId = patient.getMedicalId();
        doctor = nhiExtendDisposal.getDisposal().getCreatedBy();

        this.nhiExtendDisposal = nhiExtendDisposal;
    }
}
