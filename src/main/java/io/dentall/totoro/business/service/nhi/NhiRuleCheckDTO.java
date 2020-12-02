package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.Patient;

import javax.validation.constraints.NotNull;
import java.util.List;

public class NhiRuleCheckDTO {

    @NotNull
    private Patient patient;

    private NhiExtendDisposal nhiExtendDisposal;

    @NotNull
    private NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure;

    private List<Long> excludeTreatmentProcedureIds;

    private List<String> includeNhiCodes;

    private boolean referral;

    public NhiRuleCheckDTO referral(boolean referral) {
        this.referral = referral;
        return this;
    }

    public NhiRuleCheckDTO includeNhiCodes(List<String> includeNhiCodes) {
        this.includeNhiCodes = includeNhiCodes;
        return this;
    }

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

    public List<String> getIncludeNhiCodes() {
        return includeNhiCodes;
    }

    public void setIncludeNhiCodes(List<String> includeNhiCodes) {
        this.includeNhiCodes = includeNhiCodes;
    }

    public boolean isReferral() {
        return referral;
    }

    public void setReferral(boolean referral) {
        this.referral = referral;
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

    public List<Long> getExcludeTreatmentProcedureIds() {
        return excludeTreatmentProcedureIds;
    }

    public void setExcludeTreatmentProcedureIds(List<Long> excludeTreatmentProcedureIds) {
        this.excludeTreatmentProcedureIds = excludeTreatmentProcedureIds;
    }
}
