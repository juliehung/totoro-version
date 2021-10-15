package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.domain.enumeration.NhiExtendDisposalUploadStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;
import java.time.LocalDate;

public class MonthDisposalVM {

    @JsonIgnore
    private Long disposalId;

    private Instant disposalDateTime;

    @JsonProperty(value = "id")
    private Long nhiExtendDisposalId;

    private String a11;

    private String a12;

    private String a13;

    private String a14;

    private String a15;

    private String a16;

    private String a17;

    private String a18;

    private String a19;

    private String a22;

    private String a23;

    private String a25;

    private String a26;

    private String a27;

    private String a31;

    private String a32;

    private String a41;

    private String a42;

    private String a43;

    private String a44;

    private String a54;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private NhiExtendDisposalUploadStatus uploadStatus;

    private String examinationCode;

    private Integer examinationPoint;

    private String patientIdentity;

    private String serialNumber;

    private Long patientId;

    private String category;

    private LocalDate replenishmentDate;

    private Boolean checkedMonthDeclaration;

    private Boolean checkedAuditing;

    private String patientName;

    private Boolean vipPatient;

    private Long dependedDisposalId;

    public Long getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(Long disposalId) {
        this.disposalId = disposalId;
    }

    public Instant getDisposalDateTime() {
        return disposalDateTime;
    }

    public void setDisposalDateTime(Instant disposalDateTime) {
        this.disposalDateTime = disposalDateTime;
    }

    public Long getNhiExtendDisposalId() {
        return nhiExtendDisposalId;
    }

    public void setNhiExtendDisposalId(Long nhiExtendDisposalId) {
        this.nhiExtendDisposalId = nhiExtendDisposalId;
    }

    public String getA11() {
        return a11;
    }

    public void setA11(String a11) {
        this.a11 = a11;
    }

    public String getA12() {
        return a12;
    }

    public void setA12(String a12) {
        this.a12 = a12;
    }

    public String getA13() {
        return a13;
    }

    public void setA13(String a13) {
        this.a13 = a13;
    }

    public String getA14() {
        return a14;
    }

    public void setA14(String a14) {
        this.a14 = a14;
    }

    public String getA15() {
        return a15;
    }

    public void setA15(String a15) {
        this.a15 = a15;
    }

    public String getA16() {
        return a16;
    }

    public void setA16(String a16) {
        this.a16 = a16;
    }

    public String getA17() {
        return a17;
    }

    public void setA17(String a17) {
        this.a17 = a17;
    }

    public String getA18() {
        return a18;
    }

    public void setA18(String a18) {
        this.a18 = a18;
    }

    public String getA19() {
        return a19;
    }

    public void setA19(String a19) {
        this.a19 = a19;
    }

    public String getA22() {
        return a22;
    }

    public void setA22(String a22) {
        this.a22 = a22;
    }

    public String getA23() {
        return a23;
    }

    public void setA23(String a23) {
        this.a23 = a23;
    }

    public String getA25() {
        return a25;
    }

    public void setA25(String a25) {
        this.a25 = a25;
    }

    public String getA26() {
        return a26;
    }

    public void setA26(String a26) {
        this.a26 = a26;
    }

    public String getA27() {
        return a27;
    }

    public void setA27(String a27) {
        this.a27 = a27;
    }

    public String getA31() {
        return a31;
    }

    public void setA31(String a31) {
        this.a31 = a31;
    }

    public String getA32() {
        return a32;
    }

    public void setA32(String a32) {
        this.a32 = a32;
    }

    public String getA41() {
        return a41;
    }

    public void setA41(String a41) {
        this.a41 = a41;
    }

    public String getA42() {
        return a42;
    }

    public void setA42(String a42) {
        this.a42 = a42;
    }

    public String getA43() {
        return a43;
    }

    public void setA43(String a43) {
        this.a43 = a43;
    }

    public String getA44() {
        return a44;
    }

    public void setA44(String a44) {
        this.a44 = a44;
    }

    public String getA54() {
        return a54;
    }

    public void setA54(String a54) {
        this.a54 = a54;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public NhiExtendDisposalUploadStatus getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(NhiExtendDisposalUploadStatus uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getExaminationCode() {
        return examinationCode;
    }

    public void setExaminationCode(String examinationCode) {
        this.examinationCode = examinationCode;
    }

    public Integer getExaminationPoint() {
        return examinationPoint;
    }

    public void setExaminationPoint(Integer examinationPoint) {
        this.examinationPoint = examinationPoint;
    }

    public String getPatientIdentity() {
        return patientIdentity;
    }

    public void setPatientIdentity(String patientIdentity) {
        this.patientIdentity = patientIdentity;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getReplenishmentDate() {
        return replenishmentDate;
    }

    public void setReplenishmentDate(LocalDate replenishmentDate) {
        this.replenishmentDate = replenishmentDate;
    }

    public Boolean getCheckedMonthDeclaration() {
        return checkedMonthDeclaration;
    }

    public void setCheckedMonthDeclaration(Boolean checkedMonthDeclaration) {
        this.checkedMonthDeclaration = checkedMonthDeclaration;
    }

    public Boolean getCheckedAuditing() {
        return checkedAuditing;
    }

    public void setCheckedAuditing(Boolean checkedAuditing) {
        this.checkedAuditing = checkedAuditing;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Boolean getVipPatient() {
        return vipPatient;
    }

    public void setVipPatient(Boolean vipPatient) {
        this.vipPatient = vipPatient;
    }

    public Long getDependedDisposalId() {
        return dependedDisposalId;
    }

    public void setDependedDisposalId(Long dependedDisposalId) {
        this.dependedDisposalId = dependedDisposalId;
    }

}
