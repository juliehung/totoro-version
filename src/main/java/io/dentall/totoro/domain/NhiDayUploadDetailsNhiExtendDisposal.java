package io.dentall.totoro.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A NhiDayUploadDetails.
 */

@Entity
@Table(name = "nhi_day_upload_details_nhi_extend_disposal")
public class NhiDayUploadDetailsNhiExtendDisposal {

    @EmbeddedId
    private NhiDayUploadDetailsNhiExtendDisposalId id;

    public NhiDayUploadDetailsNhiExtendDisposalId getId() {
        return id;
    }

    public void setId(NhiDayUploadDetailsNhiExtendDisposalId id) {
        this.id = id;
    }
}

