package io.dentall.totoro.business.vm.nhi;

import javax.validation.constraints.NotNull;

public class NhiRuleCheckVM {

    @NotNull
    private Long patientId;

    private Long treatmentId;

    private String tmpTreatmentA74;

    private String tmpTreatmentA75;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Long treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getTmpTreatmentA74() {
        return tmpTreatmentA74;
    }

    public void setTmpTreatmentA74(String tmpTreatmentA74) {
        this.tmpTreatmentA74 = tmpTreatmentA74;
    }

    public String getTmpTreatmentA75() {
        return tmpTreatmentA75;
    }

    public void setTmpTreatmentA75(String tmpTreatmentA75) {
        this.tmpTreatmentA75 = tmpTreatmentA75;
    }
}
