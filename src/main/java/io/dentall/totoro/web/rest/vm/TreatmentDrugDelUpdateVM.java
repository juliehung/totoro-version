package io.dentall.totoro.web.rest.vm;

import java.util.List;

public class TreatmentDrugDelUpdateVM {
    // for update td del by td id when it has been cancel by ic card mechanism
    private List<Long> treatmentDrugIds;

    public List<Long> getTreatmentDrugIds() {
        return treatmentDrugIds;
    }

    public void setTreatmentDrugIds(List<Long> treatmentDrugIds) {
        this.treatmentDrugIds = treatmentDrugIds;
    }
}
