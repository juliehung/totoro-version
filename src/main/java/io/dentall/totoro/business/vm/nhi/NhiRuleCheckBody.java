package io.dentall.totoro.business.vm.nhi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.dentall.totoro.business.service.nhi.NhiHybridRecordDTO;

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

    private Long doctorId;

    private List<NhiRuleCheckTxSnapshot> txSnapshots;

    private String nhiCategory;

    /**
     * 這個是後端在月申報產報表時，在模仿前端打單一治療項目檢核時，
     * 會需要帶入的額外項目，
     * 前端並不會用到，因此不呈顯
     */
    @JsonIgnore
    private List<Long> excludeDisposalIds;

    /**
     * 這個是後端在月申報產報表時，在模仿前端打單一治療項目檢核時，
     * 用來加快速度用的項目，
     * 前端並不會用到，因此不呈顯
     */
    @JsonIgnore
    private List<NhiHybridRecordDTO> sourceData;

    public List<NhiHybridRecordDTO> getSourceData() {
        return sourceData;
    }

    public void setSourceData(List<NhiHybridRecordDTO> sourceData) {
        this.sourceData = sourceData;
    }

    public List<Long> getExcludeDisposalIds() {
        return excludeDisposalIds;
    }

    public void setExcludeDisposalIds(List<Long> excludeDisposalIds) {
        this.excludeDisposalIds = excludeDisposalIds;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

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
