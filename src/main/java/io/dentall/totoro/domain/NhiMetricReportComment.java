package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.NhiMetricReportType;

import java.util.Set;

public class NhiMetricReportComment {

     private Set<NhiMetricReportType> nhiMetricReportTypes;

     private Set<Long> doctorIds;

     private String url;

    public Set<NhiMetricReportType> getNhiMetricReportTypes() {
        return nhiMetricReportTypes;
    }

    public void setNhiMetricReportTypes(Set<NhiMetricReportType> nhiMetricReportTypes) {
        this.nhiMetricReportTypes = nhiMetricReportTypes;
    }

    public Set<Long> getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(Set<Long> doctorIds) {
        this.doctorIds = doctorIds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
