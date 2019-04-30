package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * A NHIExtendDisposal.
 */
@Entity
@Table(name = "nhi_extend_disposal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NHIExtendDisposal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JsonProperty(access = WRITE_ONLY)
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
    @Column(name = "a22")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Disposal getDisposal() {
        return disposal;
    }

    public NHIExtendDisposal disposal(Disposal disposal) {
        this.disposal = disposal;
        return this;
    }

    public void setDisposal(Disposal disposal) {
        this.disposal = disposal;
    }

    public String getA11() {
        return a11;
    }

    public NHIExtendDisposal a11(String a11) {
        this.a11 = a11;
        return this;
    }

    public void setA11(String a11) {
        this.a11 = a11;
    }

    public String getA12() {
        return a12;
    }

    public NHIExtendDisposal a12(String a12) {
        this.a12 = a12;
        return this;
    }

    public void setA12(String a12) {
        this.a12 = a12;
    }

    public String getA13() {
        return a13;
    }

    public NHIExtendDisposal a13(String a13) {
        this.a13 = a13;
        return this;
    }

    public void setA13(String a13) {
        this.a13 = a13;
    }

    public String getA14() {
        return a14;
    }

    public NHIExtendDisposal a14(String a14) {
        this.a14 = a14;
        return this;
    }

    public void setA14(String a14) {
        this.a14 = a14;
    }

    public String getA15() {
        return a15;
    }

    public NHIExtendDisposal a15(String a15) {
        this.a15 = a15;
        return this;
    }

    public void setA15(String a15) {
        this.a15 = a15;
    }

    public String getA16() {
        return a16;
    }

    public NHIExtendDisposal a16(String a16) {
        this.a16 = a16;
        return this;
    }

    public void setA16(String a16) {
        this.a16 = a16;
    }

    public String getA17() {
        return a17;
    }

    public NHIExtendDisposal a17(String a17) {
        this.a17 = a17;
        return this;
    }

    public void setA17(String a17) {
        this.a17 = a17;
    }

    public String getA18() {
        return a18;
    }

    public NHIExtendDisposal a18(String a18) {
        this.a18 = a18;
        return this;
    }

    public void setA18(String a18) {
        this.a18 = a18;
    }

    public String getA19() {
        return a19;
    }

    public NHIExtendDisposal a19(String a19) {
        this.a19 = a19;
        return this;
    }

    public void setA19(String a19) {
        this.a19 = a19;
    }

    public String getA22() {
        return a22;
    }

    public NHIExtendDisposal a22(String a22) {
        this.a22 = a22;
        return this;
    }

    public void setA22(String a22) {
        this.a22 = a22;
    }

    public String getA23() {
        return a23;
    }

    public NHIExtendDisposal a23(String a23) {
        this.a23 = a23;
        return this;
    }

    public void setA23(String a23) {
        this.a23 = a23;
    }

    public String getA25() {
        return a25;
    }

    public NHIExtendDisposal a25(String a25) {
        this.a25 = a25;
        return this;
    }

    public void setA25(String a25) {
        this.a25 = a25;
    }

    public String getA26() {
        return a26;
    }

    public NHIExtendDisposal a26(String a26) {
        this.a26 = a26;
        return this;
    }

    public void setA26(String a26) {
        this.a26 = a26;
    }

    public String getA27() {
        return a27;
    }

    public NHIExtendDisposal a27(String a27) {
        this.a27 = a27;
        return this;
    }

    public void setA27(String a27) {
        this.a27 = a27;
    }

    public String getA31() {
        return a31;
    }

    public NHIExtendDisposal a31(String a31) {
        this.a31 = a31;
        return this;
    }

    public void setA31(String a31) {
        this.a31 = a31;
    }

    public String getA32() {
        return a32;
    }

    public NHIExtendDisposal a32(String a32) {
        this.a32 = a32;
        return this;
    }

    public void setA32(String a32) {
        this.a32 = a32;
    }

    public String getA41() {
        return a41;
    }

    public NHIExtendDisposal a41(String a41) {
        this.a41 = a41;
        return this;
    }

    public void setA41(String a41) {
        this.a41 = a41;
    }

    public String getA42() {
        return a42;
    }

    public NHIExtendDisposal a42(String a42) {
        this.a42 = a42;
        return this;
    }

    public void setA42(String a42) {
        this.a42 = a42;
    }

    public String getA43() {
        return a43;
    }

    public NHIExtendDisposal a43(String a43) {
        this.a43 = a43;
        return this;
    }

    public void setA43(String a43) {
        this.a43 = a43;
    }

    public String getA44() {
        return a44;
    }

    public NHIExtendDisposal a44(String a44) {
        this.a44 = a44;
        return this;
    }

    public void setA44(String a44) {
        this.a44 = a44;
    }

    public String getA54() {
        return a54;
    }

    public NHIExtendDisposal a54(String a54) {
        this.a54 = a54;
        return this;
    }

    public void setA54(String a54) {
        this.a54 = a54;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NHIExtendDisposal extendUser = (NHIExtendDisposal) o;
        if (extendUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), extendUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NHIExtendDisposal{" +
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
            "}";
    }
}
