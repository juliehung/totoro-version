package io.dentall.totoro.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class NhiDayUploadDetailsNhiExtendDisposalId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "nhi_day_upload_details_id")
    private Long nhiDayUploadDetailsId;

    @Column(name = "nhi_extend_disposals_id")
    private Long nhiExtendDisposalsId;

    public NhiDayUploadDetailsNhiExtendDisposalId() {

    }

    public NhiDayUploadDetailsNhiExtendDisposalId(
        Long nhiDayUploadId,
        Long nhiExtendDisposalsId
    ) {
       this.nhiDayUploadDetailsId = nhiDayUploadId;
       this.nhiExtendDisposalsId = nhiExtendDisposalsId;
    }

    public Long getNhiDayUploadDetailsId() {
        return nhiDayUploadDetailsId;
    }

    public void setNhiDayUploadDetailsId(Long nhiDayUploadDetailsId) {
        this.nhiDayUploadDetailsId = nhiDayUploadDetailsId;
    }

    public Long getNhiExtendDisposalsId() {
        return nhiExtendDisposalsId;
    }

    public void setNhiExtendDisposalsId(Long nhiExtendDisposalsId) {
        this.nhiExtendDisposalsId = nhiExtendDisposalsId;
    }
}
