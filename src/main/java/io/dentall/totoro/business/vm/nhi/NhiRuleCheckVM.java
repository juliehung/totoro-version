package io.dentall.totoro.business.vm.nhi;

import javax.validation.constraints.NotNull;

public class NhiRuleCheckVM {

    @NotNull
    private Long patientId;

    private Long treatmentId;

    private String a74;

    private String a75;

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

    public String getA74() {
        return a74;
    }

    public void setA74(String a74) {
        this.a74 = a74;
    }

    public String getA75() {
        return a75;
    }

    public void setA75(String a75) {
        this.a75 = a75;
    }
}
