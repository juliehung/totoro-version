package io.dentall.totoro.business.vm.nhi;

import io.dentall.totoro.domain.enumeration.NhiMetricReportType;

import java.util.List;

public class NhiMetricReportBodyVM {
    private Integer yyyymm;

    private List<NhiMetricReportType> nhiMetricReportTypes;

    private List<String> selectedTargets;

    public Integer getYyyymm() {
        return yyyymm;
    }

    public void setYyyymm(Integer yyyymm) {
        this.yyyymm = yyyymm;
    }

    public List<NhiMetricReportType> getNhiMetricReportTypes() {
        return nhiMetricReportTypes;
    }

    public void setNhiMetricReportTypes(List<NhiMetricReportType> nhiMetricReportTypes) {
        this.nhiMetricReportTypes = nhiMetricReportTypes;
    }

    public List<String> getSelectedTargets() {
        return selectedTargets;
    }

    public void setSelectedTargets(List<String> selectedTargets) {
        this.selectedTargets = selectedTargets;
    }
}
