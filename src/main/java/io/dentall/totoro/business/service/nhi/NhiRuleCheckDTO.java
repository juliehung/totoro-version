package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.vm.nhi.NhiRuleCheckTxSnapshot;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.Patient;

import javax.validation.constraints.NotNull;
import java.util.List;

public class NhiRuleCheckDTO {

    @NotNull
    private Patient patient;

    private Long doctorId;

    private NhiExtendDisposal nhiExtendDisposal;

    @NotNull
    private NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure;

    private List<Long> excludeDisposalIds;

    private List<String> includeNhiCodes;

    private List<NhiRuleCheckTxSnapshot> txSnapshots;

    private boolean referral;

    /**
     * 主要用來提升月申報速度，使得重複查詢的次數減少，未來期望在處置單檢查時也可以改用這個方式。
     */
    private List<NhiHybridRecordDTO> sourceData;

    public List<NhiHybridRecordDTO> getSourceData() {
        return sourceData;
    }

    public void setSourceData(List<NhiHybridRecordDTO> sourceData) {
        this.sourceData = sourceData;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public List<NhiRuleCheckTxSnapshot> getTxSnapshots() {
        return txSnapshots;
    }

    public void setTxSnapshots(List<NhiRuleCheckTxSnapshot> txSnapshots) {
        this.txSnapshots = txSnapshots;
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

    public List<Long> getExcludeDisposalIds() {
        return excludeDisposalIds;
    }

    public void setExcludeDisposalIds(List<Long> excludeDisposalIds) {
        this.excludeDisposalIds = excludeDisposalIds;
    }
}
