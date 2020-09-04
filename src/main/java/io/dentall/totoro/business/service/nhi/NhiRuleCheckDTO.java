package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.service.dto.table.NhiExtendDisposalTable;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentProcedureTable;
import io.dentall.totoro.service.dto.table.PatientTable;

public class NhiRuleCheckDTO {

    private PatientTable patient;

    private NhiExtendDisposalTable nhiExtendDisposal;

    private NhiExtendTreatmentProcedureTable nhiExtendTreatmentProcedure;

    public PatientTable getPatient() {
        return patient;
    }

    public void setPatient(PatientTable patient) {
        this.patient = patient;
    }

    public NhiExtendDisposalTable getNhiExtendDisposal() {
        return nhiExtendDisposal;
    }

    public void setNhiExtendDisposal(NhiExtendDisposalTable nhiExtendDisposal) {
        this.nhiExtendDisposal = nhiExtendDisposal;
    }

    public NhiExtendTreatmentProcedureTable getNhiExtendTreatmentProcedure() {
        return nhiExtendTreatmentProcedure;
    }

    public void setNhiExtendTreatmentProcedure(NhiExtendTreatmentProcedureTable nhiExtendTreatmentProcedure) {
        this.nhiExtendTreatmentProcedure = nhiExtendTreatmentProcedure;
    }

}
