package io.dentall.totoro.web.rest.vm;

import java.util.List;

public class TreatmentProcedureDelUpdateVM {
    // for update tp del by tx id when it has been cancel by ic card mechanism
    private List<Long> treatmentProcedureIds;

    public List<Long> getTreatmentProcedureIds() {
        return treatmentProcedureIds;
    }

    public void setTreatmentProcedureIds(List<Long> treatmentProcedureIds) {
        this.treatmentProcedureIds = treatmentProcedureIds;
    }
}
