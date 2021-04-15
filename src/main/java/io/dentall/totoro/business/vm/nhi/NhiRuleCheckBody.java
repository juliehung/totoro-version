package io.dentall.totoro.business.vm.nhi;

import javax.validation.constraints.NotNull;
import java.util.List;

public class NhiRuleCheckBody {

    private Long disposalId;

    /**
     * 健保格式，同於 a71 YYYMMDDHHMIss
     */
    @NotNull
    private String disposalTime;

    @NotNull
    private Long patientId;

    private List<NhiRuleCheckTxSnapshot> txSnapshots;

    private String nhiCategory;

    public Long getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(Long disposalId) {
        this.disposalId = disposalId;
    }

    public String getDisposalTime() {
        return disposalTime;
    }

    public void setDisposalTime(String disposalTime) {
        this.disposalTime = disposalTime;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public List<NhiRuleCheckTxSnapshot> getTxSnapshots() {
        return txSnapshots;
    }

    public void setTxSnapshots(List<NhiRuleCheckTxSnapshot> txSnapshots) {
        this.txSnapshots = txSnapshots;
    }

    public String getNhiCategory() {
        return nhiCategory;
    }

    public void setNhiCategory(String nhiCategory) {
        this.nhiCategory = nhiCategory;
    }
}
