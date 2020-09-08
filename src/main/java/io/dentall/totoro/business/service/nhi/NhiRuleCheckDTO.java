package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.Patient;

import javax.validation.constraints.NotNull;

public class NhiRuleCheckDTO {

    @NotNull
    private Patient patient;

    private NhiExtendDisposal nhiExtendDisposal;

    @NotNull
    private NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure;

    public NhiRuleCheckDTO patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public NhiRuleCheckDTO nhiExtendDisposal(NhiExtendDisposal nhiExtendDisposal) {
        this.nhiExtendDisposal = nhiExtendDisposal;
        return this;
    }

    public NhiRuleCheckDTO nhiExtendTreatmentProcedure(NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        this.nhiExtendTreatmentProcedure = nhiExtendTreatmentProcedure;
        return this;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public NhiExtendDisposal getNhiExtendDisposal() {
        return nhiExtendDisposal;
    }

    public void setNhiExtendDisposal(NhiExtendDisposal nhiExtendDisposal) {
        this.nhiExtendDisposal = nhiExtendDisposal;
    }

    public NhiExtendTreatmentProcedure getNhiExtendTreatmentProcedure() {
        return nhiExtendTreatmentProcedure;
    }

    public void setNhiExtendTreatmentProcedure(NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        this.nhiExtendTreatmentProcedure = nhiExtendTreatmentProcedure;
    }
}
