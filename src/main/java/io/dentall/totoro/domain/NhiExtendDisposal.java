package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.domain.enumeration.NhiExtendDisposalUploadStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

/**
 * A NhiExtendDisposal.
 */
@Entity
@Table(name = "nhi_extend_disposal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NhiExtendDisposal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = {"status", "total", "dateTime", "prescription", "todo", "registration", "treatmentProcedures", "teeth", "nhiExtendDisposals"}, allowSetters = true)
    private Disposal disposal;

    // 卡片號碼
    @Column(name = "a11")
    private String a11;

    // 身份證字號
    @Column(name = "a12")
    private String a12;

    // 出生日期
    @Column(name = "a13")
    private String a13;

    // 醫事服務機構代碼
    @Column(name = "a14")
    private String a14;

    // 醫事人員代碼
    @Column(name = "a15")
    private String a15;

    // 安全模組代碼
    @Column(name = "a16")
    private String a16;

    // 就診日期時間
    @Column(name = "a17")
    private String a17;

    // 就醫序號
    @Column(name = "a18")
    private String a18;

    // 補卡註記
    @Column(name = "a19")
    private String a19;

    // 安全簽章
    @Size(max = 765)
    @Column(name = "a22", length = 765)
    private String a22;

    // 就醫類別
    @Column(name = "a23")
    private String a23;

    // 主要診斷碼1
    @Column(name = "a25")
    private String a25;

    // 次主要診斷碼1
    @Column(name = "a26")
    private String a26;

    // 次主要診斷碼2
    @Column(name = "a27")
    private String a27;

    // 門診醫療費用(當次)
    @Column(name = "a31")
    private String a31;

    // 門診部分負擔費用(當次)
    @Column(name = "a32")
    private String a32;

    // 保健服務項目註記
    @Column(name = "a41")
    private String a41;

    // 預防保健檢查日期
    @Column(name = "a42")
    private String a42;

    // 預防保健醫事服務機構代碼
    @Column(name = "a43")
    private String a43;

    // 預防保健檢查項目代號
    @Column(name = "a44")
    private String a44;

    // 實際就醫(調劑或檢查)日期
    @Column(name = "a54")
    private String a54;

    @JsonProperty(access = READ_ONLY)
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "upload_status", nullable = false)
    private NhiExtendDisposalUploadStatus uploadStatus;

    // 診察費項目代號
    @Column(name = "examination_code")
    private String examinationCode;

    // 診察費點數
    @Column(name = "examination_point")
    private Integer examinationPoint;

    // 病患身份
    @Column(name = "patient_identity")
    private String patientIdentity;

    // 流水編號
    @Column(name = "serial_number")
    private String serialNumber = "";

    @JsonProperty(access = READ_ONLY)
    @Column(name = "patient_id")
    private Long patientId;

    @OneToMany(mappedBy = "nhiExtendDisposal", fetch = FetchType.EAGER)
    private Set<NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedures = null;

    @OneToMany(mappedBy = "nhiExtendDisposal", fetch = FetchType.EAGER)
    private Set<NhiExtendTreatmentDrug> nhiExtendTreatmentDrugs = null;

    @ManyToMany(mappedBy = "nhiExtendDisposals")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<NhiDayUploadDetails> nhiDayUploadDetails = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Disposal getDisposal() {
        return disposal;
    }

    public NhiExtendDisposal disposal(Disposal disposal) {
        this.disposal = disposal;
        return this;
    }

    public void setDisposal(Disposal disposal) {
        this.disposal = disposal;
    }

    public String getA11() {
        return a11;
    }

    public NhiExtendDisposal a11(String a11) {
        this.a11 = a11;
        return this;
    }

    public void setA11(String a11) {
        this.a11 = a11;
    }

    public String getA12() {
        return a12;
    }

    public NhiExtendDisposal a12(String a12) {
        this.a12 = a12;
        return this;
    }

    public void setA12(String a12) {
        this.a12 = a12;
    }

    public String getA13() {
        return a13;
    }

    public NhiExtendDisposal a13(String a13) {
        this.a13 = a13;
        return this;
    }

    public void setA13(String a13) {
        this.a13 = a13;
    }

    public String getA14() {
        return a14;
    }

    public NhiExtendDisposal a14(String a14) {
        this.a14 = a14;
        return this;
    }

    public void setA14(String a14) {
        this.a14 = a14;
    }

    public String getA15() {
        return a15;
    }

    public NhiExtendDisposal a15(String a15) {
        this.a15 = a15;
        return this;
    }

    public void setA15(String a15) {
        this.a15 = a15;
    }

    public String getA16() {
        return a16;
    }

    public NhiExtendDisposal a16(String a16) {
        this.a16 = a16;
        return this;
    }

    public void setA16(String a16) {
        this.a16 = a16;
    }

    public String getA17() {
        return a17;
    }

    public NhiExtendDisposal a17(String a17) {
        this.a17 = a17;
        setDate(a17);
        return this;
    }

    public void setA17(String a17) {
        this.a17 = a17;
        setDate(a17);
    }

    public String getA18() {
        return a18;
    }

    public NhiExtendDisposal a18(String a18) {
        this.a18 = a18;
        return this;
    }

    public void setA18(String a18) {
        this.a18 = a18;
    }

    public String getA19() {
        return a19;
    }

    public NhiExtendDisposal a19(String a19) {
        this.a19 = a19;
        return this;
    }

    public void setA19(String a19) {
        this.a19 = a19;
    }

    public String getA22() {
        return a22;
    }

    public NhiExtendDisposal a22(String a22) {
        this.a22 = a22;
        return this;
    }

    public void setA22(String a22) {
        this.a22 = a22;
    }

    public String getA23() {
        return a23;
    }

    public NhiExtendDisposal a23(String a23) {
        this.a23 = a23;
        return this;
    }

    public void setA23(String a23) {
        this.a23 = a23;
    }

    public String getA25() {
        return a25;
    }

    public NhiExtendDisposal a25(String a25) {
        this.a25 = a25;
        return this;
    }

    public void setA25(String a25) {
        this.a25 = a25;
    }

    public String getA26() {
        return a26;
    }

    public NhiExtendDisposal a26(String a26) {
        this.a26 = a26;
        return this;
    }

    public void setA26(String a26) {
        this.a26 = a26;
    }

    public String getA27() {
        return a27;
    }

    public NhiExtendDisposal a27(String a27) {
        this.a27 = a27;
        return this;
    }

    public void setA27(String a27) {
        this.a27 = a27;
    }

    public String getA31() {
        return a31;
    }

    public NhiExtendDisposal a31(String a31) {
        this.a31 = a31;
        return this;
    }

    public void setA31(String a31) {
        this.a31 = a31;
    }

    public String getA32() {
        return a32;
    }

    public NhiExtendDisposal a32(String a32) {
        this.a32 = a32;
        return this;
    }

    public void setA32(String a32) {
        this.a32 = a32;
    }

    public String getA41() {
        return a41;
    }

    public NhiExtendDisposal a41(String a41) {
        this.a41 = a41;
        return this;
    }

    public void setA41(String a41) {
        this.a41 = a41;
    }

    public String getA42() {
        return a42;
    }

    public NhiExtendDisposal a42(String a42) {
        this.a42 = a42;
        return this;
    }

    public void setA42(String a42) {
        this.a42 = a42;
    }

    public String getA43() {
        return a43;
    }

    public NhiExtendDisposal a43(String a43) {
        this.a43 = a43;
        return this;
    }

    public void setA43(String a43) {
        this.a43 = a43;
    }

    public String getA44() {
        return a44;
    }

    public NhiExtendDisposal a44(String a44) {
        this.a44 = a44;
        return this;
    }

    public void setA44(String a44) {
        this.a44 = a44;
    }

    public String getA54() {
        return a54;
    }

    public NhiExtendDisposal a54(String a54) {
        this.a54 = a54;
        return this;
    }

    public void setA54(String a54) {
        this.a54 = a54;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(String a17) {
        if (a17 != null) {
            date = LocalDate.of(Integer.parseInt(a17.substring(0, 3)) + 1911, Integer.parseInt(a17.substring(3, 5)), Integer.parseInt(a17.substring(5, 7)));
        }
    }

    public NhiExtendDisposalUploadStatus getUploadStatus() {
        return uploadStatus;
    }

    public NhiExtendDisposal uploadStatus(NhiExtendDisposalUploadStatus uploadStatus) {
        this.uploadStatus = uploadStatus;
        return this;
    }

    public void setUploadStatus(NhiExtendDisposalUploadStatus uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getExaminationCode() {
        return examinationCode;
    }

    public NhiExtendDisposal examinationCode(String examinationCode) {
        this.examinationCode = examinationCode;
        return this;
    }

    public void setExaminationCode(String examinationCode) {
        this.examinationCode = examinationCode;
    }

    public Integer getExaminationPoint() {
        return examinationPoint;
    }

    public NhiExtendDisposal examinationPoint(Integer examinationPoint) {
        this.examinationPoint = examinationPoint;
        return this;
    }

    public void setExaminationPoint(Integer examinationPoint) {
        this.examinationPoint = examinationPoint;
    }

    public Set<NhiExtendTreatmentProcedure> getNhiExtendTreatmentProcedures() {
        return nhiExtendTreatmentProcedures;
    }

    public NhiExtendDisposal nhiExtendTreatmentProcedures(Set<NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedures) {
        this.nhiExtendTreatmentProcedures = nhiExtendTreatmentProcedures;
        return this;
    }

    public NhiExtendDisposal addNhiExtendTreatmentProcedure(NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        this.nhiExtendTreatmentProcedures.add(nhiExtendTreatmentProcedure);
        nhiExtendTreatmentProcedure.setNhiExtendDisposal(this);
        return this;
    }

    public NhiExtendDisposal removeNhiExtendTreatmentProcedure(NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        this.nhiExtendTreatmentProcedures.remove(nhiExtendTreatmentProcedure);
        nhiExtendTreatmentProcedure.setNhiExtendDisposal(null);
        return this;
    }

    public void setNhiExtendTreatmentProcedures(Set<NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedures) {
        this.nhiExtendTreatmentProcedures = nhiExtendTreatmentProcedures;
    }

    public Set<NhiExtendTreatmentDrug> getNhiExtendTreatmentDrugs() {
        return nhiExtendTreatmentDrugs;
    }

    public NhiExtendDisposal nhiExtendTreatmentDrugs(Set<NhiExtendTreatmentDrug> nhiExtendTreatmentDrugs) {
        this.nhiExtendTreatmentDrugs = nhiExtendTreatmentDrugs;
        return this;
    }

    public NhiExtendDisposal addNhiExtendTreatmentDrug(NhiExtendTreatmentDrug nhiExtendTreatmentDrug) {
        this.nhiExtendTreatmentDrugs.add(nhiExtendTreatmentDrug);
        nhiExtendTreatmentDrug.setNhiExtendDisposal(this);
        return this;
    }

    public NhiExtendDisposal removeNhiExtendTreatmentDrug(NhiExtendTreatmentDrug nhiExtendTreatmentDrug) {
        this.nhiExtendTreatmentDrugs.remove(nhiExtendTreatmentDrug);
        nhiExtendTreatmentDrug.setNhiExtendDisposal(null);
        return this;
    }

    public void setNhiExtendTreatmentDrugs(Set<NhiExtendTreatmentDrug> nhiExtendTreatmentDrugs) {
        this.nhiExtendTreatmentDrugs = nhiExtendTreatmentDrugs;
    }

    public Set<NhiDayUploadDetails> getNhiDayUploadDetails() {
        return nhiDayUploadDetails;
    }

    public NhiExtendDisposal nhiDayUploadDetails(Set<NhiDayUploadDetails> nhiDayUploadDetails) {
        this.nhiDayUploadDetails = nhiDayUploadDetails;
        return this;
    }

    public void setNhiDayUploadDetails(Set<NhiDayUploadDetails> nhiDayUploadDetails) {
        this.nhiDayUploadDetails = nhiDayUploadDetails;
    }

    public String getPatientIdentity() {
        return patientIdentity;
    }

    public NhiExtendDisposal patientIdentity(String patientIdentity) {
        this.patientIdentity = patientIdentity;
        return this;
    }

    public void setPatientIdentity(String patientIdentity) {
        this.patientIdentity = patientIdentity;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public NhiExtendDisposal serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getPatientId() {
        return patientId;
    }

    public NhiExtendDisposal patientId(Long patientId) {
        this.patientId = patientId;
        return this;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NhiExtendDisposal nhiExtendDisposal = (NhiExtendDisposal) o;
        if (nhiExtendDisposal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhiExtendDisposal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhiExtendDisposal{" +
            "id=" + getId() +
            ", a11='" + getA11() + "'" +
            ", a12='" + getA12() + "'" +
            ", a13='" + getA13() + "'" +
            ", a14='" + getA14() + "'" +
            ", a15='" + getA15() + "'" +
            ", a16='" + getA16() + "'" +
            ", a17='" + getA17() + "'" +
            ", a18='" + getA18() + "'" +
            ", a19='" + getA19() + "'" +
            ", a22='" + getA22() + "'" +
            ", a23='" + getA23() + "'" +
            ", a25='" + getA25() + "'" +
            ", a26='" + getA26() + "'" +
            ", a27='" + getA27() + "'" +
            ", a31='" + getA31() + "'" +
            ", a32='" + getA32() + "'" +
            ", a41='" + getA41() + "'" +
            ", a42='" + getA42() + "'" +
            ", a43='" + getA43() + "'" +
            ", a44='" + getA44() + "'" +
            ", a54='" + getA54() + "'" +
            ", date='" + getDate() + "'" +
            ", uploadStatus='" + getUploadStatus() + "'" +
            ", examinationCode='" + getExaminationCode() + "'" +
            ", examinationPoint='" + getExaminationPoint() + "'" +
            ", patientIdentity='" + getPatientIdentity() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", patientId='" + getPatientId() + "'" +
            "}";
    }
}
