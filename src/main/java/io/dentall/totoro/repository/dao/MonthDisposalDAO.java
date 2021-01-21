package io.dentall.totoro.repository.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.domain.enumeration.NhiExtendDisposalUploadStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

public class MonthDisposalDAO {

    @JsonIgnore
    private final Long disposalId;

    @JsonProperty(value = "id")
    private final Long nhiExtendDisposalId;

    private final String a11;

    private final String a12;

    private final String a13;

    private final String a14;

    private final String a15;

    private final String a16;

    private final String a17;

    private final String a18;

    private final String a19;

    private final String a22;

    private final String a23;

    private final String a25;

    private final String a26;

    private final String a27;

    private final String a31;

    private final String a32;

    private final String a41;

    private final String a42;

    private final String a43;

    private final String a44;

    private final String a54;

    private final LocalDate date;

    @Enumerated(EnumType.STRING)
    private final NhiExtendDisposalUploadStatus uploadStatus;

    private final String examinationCode;

    private final Integer examinationPoint;

    private final String patientIdentity;

    private final String serialNumber;

    private final Long patientId;

    private final String category;

    private final LocalDate replenishmentDate;

    private final Boolean checkedMonthDeclaration;

    private final Boolean checkedAuditing;

    private final String patientName;

    private final Boolean vipPatient;

    public MonthDisposalDAO(
        Long disposalId, Long nhiExtendDisposalId, String a11, String a12, String a13, String a14, String a15, String a16, String a17, String a18, String a19,
        String a22, String a23, String a25, String a26, String a27, String a31, String a32, String a41, String a42, String a43, String a44, String a54, LocalDate date,
        NhiExtendDisposalUploadStatus uploadStatus, String examinationCode, Integer examinationPoint, String patientIdentity, String serialNumber, Long patientId, String category,
        LocalDate replenishmentDate, Boolean checkedMonthDeclaration, Boolean checkedAuditing, String patientName, Boolean vipPatient
    ) {
        this.disposalId = disposalId;
        this.nhiExtendDisposalId = nhiExtendDisposalId;
        this.a11 = a11;
        this.a12 = a12;
        this.a13 = a13;
        this.a14 = a14;
        this.a15 = a15;
        this.a16 = a16;
        this.a17 = a17;
        this.a18 = a18;
        this.a19 = a19;
        this.a22 = a22;
        this.a23 = a23;
        this.a25 = a25;
        this.a26 = a26;
        this.a27 = a27;
        this.a31 = a31;
        this.a32 = a32;
        this.a41 = a41;
        this.a42 = a42;
        this.a43 = a43;
        this.a44 = a44;
        this.a54 = a54;
        this.date = date;
        this.uploadStatus = uploadStatus;
        this.examinationCode = examinationCode;
        this.examinationPoint = examinationPoint;
        this.patientIdentity = patientIdentity;
        this.serialNumber = serialNumber;
        this.patientId = patientId;
        this.category = category;
        this.replenishmentDate = replenishmentDate;
        this.checkedMonthDeclaration = checkedMonthDeclaration;
        this.checkedAuditing = checkedAuditing;
        this.patientName = patientName;
        this.vipPatient = vipPatient;
    }

    public String getPatientName() {
        return patientName;
    }

    public Boolean getVipPatient() {
        return vipPatient;
    }

    public Long getDisposalId() {
        return disposalId;
    }

    public Long getNhiExtendDisposalId() {
        return nhiExtendDisposalId;
    }

    public String getA11() {
        return a11;
    }

    public String getA12() {
        return a12;
    }

    public String getA13() {
        return a13;
    }

    public String getA14() {
        return a14;
    }

    public String getA15() {
        return a15;
    }

    public String getA16() {
        return a16;
    }

    public String getA17() {
        return a17;
    }

    public String getA18() {
        return a18;
    }

    public String getA19() {
        return a19;
    }

    public String getA22() {
        return a22;
    }

    public String getA23() {
        return a23;
    }

    public String getA25() {
        return a25;
    }

    public String getA26() {
        return a26;
    }

    public String getA27() {
        return a27;
    }

    public String getA31() {
        return a31;
    }

    public String getA32() {
        return a32;
    }

    public String getA41() {
        return a41;
    }

    public String getA42() {
        return a42;
    }

    public String getA43() {
        return a43;
    }

    public String getA44() {
        return a44;
    }

    public String getA54() {
        return a54;
    }

    public LocalDate getDate() {
        return date;
    }

    public NhiExtendDisposalUploadStatus getUploadStatus() {
        return uploadStatus;
    }

    public String getExaminationCode() {
        return examinationCode;
    }

    public Integer getExaminationPoint() {
        return examinationPoint;
    }

    public String getPatientIdentity() {
        return patientIdentity;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getReplenishmentDate() {
        return replenishmentDate;
    }

    public Boolean getCheckedMonthDeclaration() {
        return checkedMonthDeclaration;
    }

    public Boolean getCheckedAuditing() {
        return checkedAuditing;
    }
}
