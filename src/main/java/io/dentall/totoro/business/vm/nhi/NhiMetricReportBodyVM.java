package io.dentall.totoro.business.vm.nhi;

import io.dentall.totoro.domain.enumeration.NhiMetricReportType;

import java.util.Set;

public class NhiMetricReportBodyVM {
    private Integer yyyymm;

    private Set<NhiMetricReportType> nhiMetricReportTypes;

    private Set<String> selectedTargets;

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

    public Set<String> getSelectedTargets() {
        return selectedTargets;
    }

    public void setSelectedTargets(Set<String> selectedTargets) {
        this.selectedTargets = selectedTargets;
    }
}
