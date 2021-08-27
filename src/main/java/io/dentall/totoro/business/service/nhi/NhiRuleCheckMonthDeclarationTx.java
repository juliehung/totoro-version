package io.dentall.totoro.business.service.nhi;

public class NhiRuleCheckMonthDeclarationTx {

    private Long disposalId;

    // a17 or a54
    private String disposalTime;

    // a23
    private String nhiCategory;

    private Long doctorId;

    private String doctorName;

    private Long patientId;

    private String patientName;

    private Long treatmentProcedureId;

    private String nhiCode;

    private String teeth;

    private String surface;

    private String nhiTxName;

    public NhiRuleCheckMonthDeclarationTx(
        Long disposalId,
        String disposalTime,
        String nhiCategory,
        Long doctorId,
        String doctorName,
        Long patientId,
        String patientName,
        Long treatmentProcedureId,
        String nhiCode,
        String teeth,
        String surface,
        String nhiTxName
    ) {
        this.disposalId = disposalId;
        this.disposalTime = disposalTime;
        this.nhiCategory = nhiCategory;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.patientId = patientId;
        this.patientName = patientName;
        this.treatmentProcedureId = treatmentProcedureId;
        this.nhiCode = nhiCode;
        this.teeth = teeth;
        this.surface = surface;
        this.nhiTxName = nhiTxName;
    }

    public String getNhiTxName() {
        return nhiTxName;
    }

    public void setNhiTxName(String nhiTxName) {
        this.nhiTxName = nhiTxName;
    }

    public Long getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(Long disposalId) {
        this.disposalId = disposalId;
    }

    public String getDisposalTime() {
        return disposalTime;
    }

    public void setDisposalTime(String disposalTime) {
        this.disposalTime = disposalTime;
    }

    public String getNhiCategory() {
        return nhiCategory;
    }

    public void setNhiCategory(String nhiCategory) {
        this.nhiCategory = nhiCategory;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Long getTreatmentProcedureId() {
        return treatmentProcedureId;
    }

    public void setTreatmentProcedureId(Long treatmentProcedureId) {
        this.treatmentProcedureId = treatmentProcedureId;
    }

    public String getNhiCode() {
        return nhiCode;
    }

    public void setNhiCode(String nhiCode) {
        this.nhiCode = nhiCode;
    }

    public String getTeeth() {
        return teeth;
    }

    public void setTeeth(String teeth) {
        this.teeth = teeth;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    @Override
    public String toString() {
        return "{"
            .concat(disposalId == null ? "" : " \"disposalId\": \"" + disposalId + "\"")
            .concat(disposalTime == null ? "" : ", \"disposalTime\": \"" + disposalTime + "\"")
            .concat(nhiCategory == null ? "" : ", \"nhiCategory\": \"" + nhiCategory + "\"")
            .concat(doctorId == null ? "" : ", \"doctorId\": \"" + doctorId + "\"")
            .concat(doctorName == null ? "" : ", \"doctorName\": \"" + doctorName + "\"")
            .concat(patientId == null ? "" : ", \"patientId\": \"" + patientId + "\"")
            .concat(patientName == null ? "" : ", \"patientName\": \"" + patientName + "\"")
            .concat(treatmentProcedureId == null ? "" : ", \"treatmentProcedureId\": \"" + treatmentProcedureId + "\"")
            .concat(nhiCode == null ? "" : ", \"nhiCode\": \"" + nhiCode + "\"")
            .concat(teeth == null ? "" : ", \"teeth\": \"" + teeth + "\"")
            .concat(surface == null ? "" : ", \"surface\": \"" + surface + "\"")
            .concat("}");
    }
}
