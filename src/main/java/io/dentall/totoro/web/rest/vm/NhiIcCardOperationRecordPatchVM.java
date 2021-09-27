package io.dentall.totoro.web.rest.vm;

import java.util.List;

public class NhiIcCardOperationRecordPatchVM {
    public Long disposalId;

    public List<Long> nhiIcCardOperationRecordIds;

    public Long getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(Long disposalId) {
        this.disposalId = disposalId;
    }

    public List<Long> getNhiIcCardOperationRecordIds() {
        return nhiIcCardOperationRecordIds;
    }

    public void setNhiIcCardOperationRecordIds(List<Long> nhiIcCardOperationRecordIds) {
        this.nhiIcCardOperationRecordIds = nhiIcCardOperationRecordIds;
    }
}
