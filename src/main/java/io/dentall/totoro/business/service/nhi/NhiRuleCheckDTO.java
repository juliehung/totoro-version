package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.Patient;

public class NhiRuleCheckDTO {
    private Patient patient;
    private NhiExtendDisposal nhiExtendDisposal;
    private NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure;

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
