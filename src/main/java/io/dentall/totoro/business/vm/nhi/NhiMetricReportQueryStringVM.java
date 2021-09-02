package io.dentall.totoro.business.vm.nhi;

import javax.validation.constraints.NotNull;

public class NhiMetricReportQueryStringVM {

    @NotNull
    private Integer yyyymm;

    @NotNull
    private String createdBy;

    public Integer getYyyymm() {
        return yyyymm;
    }

    public void setYyyymm(Integer yyyymm) {
        this.yyyymm = yyyymm;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
