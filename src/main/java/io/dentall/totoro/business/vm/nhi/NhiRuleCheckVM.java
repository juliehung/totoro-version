package io.dentall.totoro.business.vm.nhi;

import javax.validation.constraints.NotNull;

public class NhiRuleCheckVM {

    @NotNull
    private Long patientId;

    private Long treatmentId;

    private String tmpTreatmentA71;

    private String tmpTreatmentA73;

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

    public String getTmpTreatmentA71() {
        return tmpTreatmentA71;
    }

    public void setTmpTreatmentA71(String tmpTreatmentA71) {
        this.tmpTreatmentA71 = tmpTreatmentA71;
    }

    public String getTmpTreatmentA73() {
        return tmpTreatmentA73;
    }

    public void setTmpTreatmentA73(String tmpTreatmentA73) {
        this.tmpTreatmentA73 = tmpTreatmentA73;
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
