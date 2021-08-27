package io.dentall.totoro.business.vm.nhi;

import io.dentall.totoro.domain.enumeration.NhiMetricReportType;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class NhiMetricReportBodyVM {

    @NotNull
    private Integer yyyymm;

    @NotNull
    private Set<NhiMetricReportType> nhiMetricReportTypes;

    @NotNull
    private Set<Long> selectedTargets;

    public Integer getYyyymm() {
        return yyyymm;
    }

    public void setYyyymm(Integer yyyymm) {
        this.yyyymm = yyyymm;
    }

    public Set<NhiMetricReportType> getNhiMetricReportTypes() {
        return nhiMetricReportTypes;
    }

    public void setNhiMetricReportTypes(Set<NhiMetricReportType> nhiMetricReportTypes) {
        this.nhiMetricReportTypes = nhiMetricReportTypes;
    }

    public Set<Long> getSelectedTargets() {
        return selectedTargets;
    }

    public void setSelectedTargets(Set<Long> selectedTargets) {
        this.selectedTargets = selectedTargets;
    }
}
