package io.dentall.totoro.business.vm.nhi;

import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import org.apache.commons.lang3.StringUtils;

public class NhiRuleCheckTxSnapshot {

    // 用來指定要檢驗哪個 snapshot 的項目用
    private boolean isTargetTx;

    private Long id;

    private String nhiCode;

    private String teeth;

    private String surface;

    public boolean isTargetTx() {
        return isTargetTx;
    }

    public void setTargetTx(boolean targetTx) {
        isTargetTx = targetTx;
    }

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

        if (StringUtils.isBlank(this.nhiCode) &&
            StringUtils.isNotBlank(o.getA73())
        ) {
            return false;
        }

        if (StringUtils.isBlank(this.teeth) &&
            StringUtils.isNotBlank(o.getA74())
        ) {
            return false;
        }

        if (StringUtils.isBlank(this.surface) &&
            StringUtils.isNotBlank(o.getA75())
        ) {
            return false;
        }

        if (this.nhiCode != null &&
            !this.nhiCode.equals(o.getA73())
        ) {
            return false;
        }

        if (this.teeth != null &&
            !this.teeth.equals(o.getA74())
        ) {
            return false;
        }

        if (this.surface != null &&
            !this.surface.equals(o.getA75())
        ) {
            return false;
        }

        return true;
    }
}
