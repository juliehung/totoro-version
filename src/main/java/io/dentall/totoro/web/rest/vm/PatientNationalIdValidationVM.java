package io.dentall.totoro.web.rest.vm;

public class PatientNationalIdValidationVM {

    private Boolean exist;

    public PatientNationalIdValidationVM exist(Boolean exist) {
        this.exist = exist;
        return this;
    }

    public Boolean getExist() {
        return exist;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }
}
