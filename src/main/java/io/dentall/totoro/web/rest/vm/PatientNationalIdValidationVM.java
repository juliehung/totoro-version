package io.dentall.totoro.web.rest.vm;

public class PatientNationalIdValidationVM {

    private Long patientId;

    private Boolean exist;

    public PatientNationalIdValidationVM(Long id) {
        if (id != null) {
            this.patientId = id;
            this.exist = true;
        } else {
            this.exist = false;
        }
    }

    public PatientNationalIdValidationVM exist(Boolean exist) {
        this.exist = exist;
        return this;
    }

    public PatientNationalIdValidationVM patientId(Long patientId) {
        this.patientId = patientId;
        return this;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Boolean getExist() {
        return exist;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }
}
