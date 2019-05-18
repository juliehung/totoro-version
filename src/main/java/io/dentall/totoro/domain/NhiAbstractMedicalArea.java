package io.dentall.totoro.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

// 醫療專區
@MappedSuperclass
public abstract class NhiAbstractMedicalArea<ENTITY extends NhiAbstractMedicalArea<ENTITY>> implements Serializable, NhiMedicalArea {

    private static final long serialVersionUID = 1L;

    // 就診日期時間
    @Column(name = "a71")
    private String a71;

    // 醫令類別
    @Column(name = "a72")
    private String a72;

    // 診療項目代號
    @Column(name = "a73")
    private String a73;

    // 診療部位
    @Column(name = "a74")
    private String a74;

    // 用法
    @Column(name = "a75")
    private String a75;

    // 天數
    @Column(name = "a76")
    private String a76;

    // 總量
    @Column(name = "a77")
    private String a77;

    // 交付處方註記
    @Column(name = "a78")
    private String a78;

    // 處方簽章
    @Column(name = "a79")
    private String a79;

    @Column(name = "check")
    private String check;

    @Override
    public String getA71() {
        return a71;
    }

    public ENTITY a71(String a71) {
        this.a71 = a71;
        return (ENTITY) this;
    }

    @Override
    public void setA71(String a71) {
        this.a71 = a71;
    }

    @Override
    public String getA72() {
        return a72;
    }

    public ENTITY a72(String a72) {
        this.a72 = a72;
        return (ENTITY) this;
    }

    @Override
    public void setA72(String a72) {
        this.a72 = a72;
    }

    @Override
    public String getA73() {
        return a73;
    }

    public ENTITY a73(String a73) {
        this.a73 = a73;
        return (ENTITY) this;
    }

    @Override
    public void setA73(String a73) {
        this.a73 = a73;
    }

    @Override
    public String getA74() {
        return a74;
    }

    public ENTITY a74(String a74) {
        this.a74 = a74;
        return (ENTITY) this;
    }

    @Override
    public void setA74(String a74) {
        this.a74 = a74;
    }

    @Override
    public String getA75() {
        return a75;
    }

    public ENTITY a75(String a75) {
        this.a75 = a75;
        return (ENTITY) this;
    }

    @Override
    public void setA75(String a75) {
        this.a75 = a75;
    }

    @Override
    public String getA76() {
        return a76;
    }

    public ENTITY a76(String a76) {
        this.a76 = a76;
        return (ENTITY) this;
    }

    @Override
    public void setA76(String a76) {
        this.a76 = a76;
    }

    @Override
    public String getA77() {
        return a77;
    }

    public ENTITY a77(String a77) {
        this.a77 = a77;
        return (ENTITY) this;
    }

    @Override
    public void setA77(String a77) {
        this.a77 = a77;
    }

    @Override
    public String getA78() {
        return a78;
    }

    public ENTITY a78(String a78) {
        this.a78 = a78;
        return (ENTITY) this;
    }

    @Override
    public void setA78(String a78) {
        this.a78 = a78;
    }

    @Override
    public String getA79() {
        return a79;
    }

    public ENTITY a79(String a79) {
        this.a79 = a79;
        return (ENTITY) this;
    }

    @Override
    public void setA79(String a79) {
        this.a79 = a79;
    }

    @Override
    public String getCheck() {
        return check;
    }

    public ENTITY check(String check) {
        this.check = check;
        return (ENTITY) this;
    }

    @Override
    public void setCheck(String check) {
        this.check = check;
    }
}
