package io.dentall.totoro.business.vm.nhi;

import javax.validation.constraints.NotNull;
import java.util.List;

public class NhiRuleCheckVM {

    @NotNull
    private Long patientId;

    private Long treatmentProcedureId;

    /**
     * 民國時間
     */
    private String a71;

    /**
     * 牙位
     */
    private String a74;

    /**
     * 牙面
     */
    private String a75;

    /**
     * 排除列出的 treatment procedure id，於查詢結果的階段
     */
    private List<Long> excludeTreatmentProcedureIds;
    /**
     * 同一處單底下的其他健保代碼
     */
    private List<String> includeNhiCodes;

    /**
     * 轉診註記
     */
    private boolean referral;

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

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getTreatmentProcedureId() {
        return treatmentProcedureId;
    }

    public void setTreatmentProcedureId(Long treatmentProcedureId) {
        this.treatmentProcedureId = treatmentProcedureId;
    }

    public String getA74() {
        return a74;
    }

    public void setA74(String a74) {
        this.a74 = a74;
    }

    public String getA75() {
        return a75;
    }

    public void setA75(String a75) {
        this.a75 = a75;
    }

    public String getA71() {
        return a71;
    }

    public void setA71(String a71) {
        this.a71 = a71;
    }

    public List<Long> getExcludeTreatmentProcedureIds() {
        return excludeTreatmentProcedureIds;
    }

    public void setExcludeTreatmentProcedureIds(List<Long> excludeTreatmentProcedureIds) {
        this.excludeTreatmentProcedureIds = excludeTreatmentProcedureIds;
    }
}
