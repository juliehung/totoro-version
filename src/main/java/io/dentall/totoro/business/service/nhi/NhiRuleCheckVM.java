package io.dentall.totoro.business.service.nhi;

import javax.validation.constraints.NotNull;

public class NhiRuleCheckVM {

    @NotNull
    private Long patientId;

    // nhi_extend_treatment_procedure.a73
    @NotNull
    private String code;

    private Long treatmentId;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Long treatmentId) {
        this.treatmentId = treatmentId;
    }
}
