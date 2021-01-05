package io.dentall.totoro.service.dto;

import java.time.LocalDate;
import java.util.Objects;

public class NhiExtendTreatmentProcedureDTO {

    private Long id;

    // 就診日期時間
    private String a71;

    // 醫令類別
    private String a72;

    // 診療項目代號
    private String a73;

    // 診療部位
    private String a74;

    // 用法
    private String a75;

    // 天數
    private String a76;

    // 總量
    private String a77;

    // 交付處方註記
    private String a78;

    // 處方簽章
    private String a79;

    private String check;

    private String nhiExtendDisposalA14;

    private LocalDate nhiExtendDisposalDate;

    private LocalDate nhiExtendDisposalReplenishmentDate;

    public NhiExtendTreatmentProcedureDTO(
            Long id, 
            String a71, 
            String a72, 
            String a73, 
            String a74, 
            String a75, 
            String a76, 
            String a77, 
            String a78, 
            String a79, 
            String check,
            String nhiExtendDisposalA14,
            LocalDate nhiExtendDisposalDate,
            LocalDate nhiExtendDisposalReplenishmentDate
            ) {
        super();
        this.id = id;
        this.a71 = a71;
        this.a72 = a72;
        this.a73 = a73;
        this.a74 = a74;
        this.a75 = a75;
        this.a76 = a76;
        this.a77 = a77;
        this.a78 = a78;
        this.a79 = a79;
        this.check = check;
        this.nhiExtendDisposalA14 = nhiExtendDisposalA14;
        this.nhiExtendDisposalDate = nhiExtendDisposalDate;
        this.nhiExtendDisposalReplenishmentDate = nhiExtendDisposalReplenishmentDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getA71() {
        return a71;
    }

    public void setA71(String a71) {
        this.a71 = a71;
    }

    public String getA72() {
        return a72;
    }

    public void setA72(String a72) {
        this.a72 = a72;
    }

    public String getA73() {
        return a73;
    }

    public void setA73(String a73) {
        this.a73 = a73;
    }

    public String getA74() {
        return a74;
    }

    public void setA74(String a74) {
        this.a74 = a74;
    }

    public String getA75() {
        return a75;
    }

    public void setA75(String a75) {
        this.a75 = a75;
    }

    public String getA76() {
        return a76;
    }

    public void setA76(String a76) {
        this.a76 = a76;
    }

    public String getA77() {
        return a77;
    }

    public void setA77(String a77) {
        this.a77 = a77;
    }

    public String getA78() {
        return a78;
    }

    public void setA78(String a78) {
        this.a78 = a78;
    }

    public String getA79() {
        return a79;
    }

    public void setA79(String a79) {
        this.a79 = a79;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getNhiExtendDisposalA14() {
        return nhiExtendDisposalA14;
    }

    public void setNhiExtendDisposalA14(String nhiExtendDisposalA14) {
        this.nhiExtendDisposalA14 = nhiExtendDisposalA14;
    }

    public LocalDate getNhiExtendDisposalDate() {
        return nhiExtendDisposalDate;
    }

    public void setNhiExtendDisposalDate(LocalDate nhiExtendDisposalDate) {
        this.nhiExtendDisposalDate = nhiExtendDisposalDate;
    }

    public LocalDate getNhiExtendDisposalReplenishmentDate() {
        return nhiExtendDisposalReplenishmentDate;
    }

    public void setNhiExtendDisposalReplenishmentDate(LocalDate nhiExtendDisposalReplenishmentDate) {
        this.nhiExtendDisposalReplenishmentDate = nhiExtendDisposalReplenishmentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NhiExtendTreatmentProcedureDTO nhiExtendTreatmentProcedure = (NhiExtendTreatmentProcedureDTO) o;
        if (nhiExtendTreatmentProcedure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhiExtendTreatmentProcedure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhiExtendTreatmentProcedureDTO{" +
            "id=" + getId() +
            ", a71='" + getA71() + "'" +
            ", a72='" + getA72() + "'" +
            ", a73='" + getA73() + "'" +
            ", a74='" + getA74() + "'" +
            ", a75='" + getA75() + "'" +
            ", a76='" + getA76() + "'" +
            ", a77='" + getA77() + "'" +
            ", a78='" + getA78() + "'" +
            ", a79='" + getA79() + "'" +
            ", check='" + getCheck() + "'" +
            ", nhiExtendDisposalA14='" + getNhiExtendDisposalA14() + "'" +
            ", nhiExtendDisposalDate='" + getNhiExtendDisposalDate() + "'" +
            ", nhiExtendDisposalReplenishmentDate='" + getNhiExtendDisposalReplenishmentDate() + "'" +
            "}";
    }
}
