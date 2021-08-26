package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.NhiMetricReportType;

import java.util.List;

public class NhiMetricReportComment {

     private List<NhiMetricReportType> nhiMetricReportTypes;

     private List<String> selectedTargets;

     private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
