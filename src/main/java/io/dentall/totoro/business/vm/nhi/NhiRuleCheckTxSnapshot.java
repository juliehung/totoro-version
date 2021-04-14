package io.dentall.totoro.business.vm.nhi;

import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;

public class NhiRuleCheckTxSnapshot {
    private Long id;

    private String nhiCode;

    private String teeth;

    private String surface;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean equalsNhiExtendTreatmentProcedure(NhiExtendTreatmentProcedure o) {
        if (o == null) {
            return false;
        }
        return this.nhiCode.equals(o.getA73()) &&
            this.teeth.equals(o.getA74()) &&
            this.surface.equals(o.getA75());
    }
}
