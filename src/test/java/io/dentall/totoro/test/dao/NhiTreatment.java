package io.dentall.totoro.test.dao;

public class NhiTreatment {

    private String sourceType;

    private Long disposalId;

    private Long nhiExtendTreatmentProcedureId;

    private String code;

    private String datetime;

    private String tooth;

    private String surface;


    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Long getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(Long disposalId) {
        this.disposalId = disposalId;
    }

    public Long getNhiExtendTreatmentProcedureId() {
        return nhiExtendTreatmentProcedureId;
    }

    public void setNhiExtendTreatmentProcedureId(Long nhiExtendTreatmentProcedureId) {
        this.nhiExtendTreatmentProcedureId = nhiExtendTreatmentProcedureId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getTooth() {
        return tooth;
    }

    public void setTooth(String tooth) {
        this.tooth = tooth;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }
}
