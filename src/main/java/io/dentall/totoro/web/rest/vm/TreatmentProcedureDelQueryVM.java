package io.dentall.totoro.web.rest.vm;

import java.util.List;

public class TreatmentProcedureDelQueryVM {
    // for query tx del belone to disposal
    private Long disposalId;

    public Long getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(Long disposalId) {
        this.disposalId = disposalId;
    }
}
