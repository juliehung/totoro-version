package io.dentall.totoro.business.service.nhi.metric.dto;

import io.dentall.totoro.business.service.nhi.NhiSpecialCode;

public class ExcludeDto {

    private String nhiCategory;

    private String treatmentProcedureCode;

    private NhiSpecialCode treatmentProcedureSpecificCode;

    public String getNhiCategory() {
        return nhiCategory;
    }

    public void setNhiCategory(String nhiCategory) {
        this.nhiCategory = nhiCategory;
    }

    public String getTreatmentProcedureCode() {
        return treatmentProcedureCode;
    }

    public void setTreatmentProcedureCode(String treatmentProcedureCode) {
        this.treatmentProcedureCode = treatmentProcedureCode;
    }

    public NhiSpecialCode getTreatmentProcedureSpecificCode() {
        return treatmentProcedureSpecificCode;
    }

    public void setTreatmentProcedureSpecificCode(NhiSpecialCode treatmentProcedureSpecificCode) {
        this.treatmentProcedureSpecificCode = treatmentProcedureSpecificCode;
    }
}
