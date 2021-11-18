package io.dentall.totoro.business.vm.nhi;

import io.dentall.totoro.domain.enumeration.NhiMetricReportType;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class NhiMetricReportBodyVM {

    @NotNull
    LocalDate begin;

    @NotNull
    List<Long> excludeDisposalId;

    @NotNull
    List<Long> doctorIds;

    @NotNull
    private Set<NhiMetricReportType> nhiMetricReportTypes;

    public LocalDate getBegin() {
        return begin;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    public List<Long> getExcludeDisposalId() {
        return excludeDisposalId;
    }

    public void setExcludeDisposalId(List<Long> excludeDisposalId) {
        this.excludeDisposalId = excludeDisposalId;
    }

    public List<Long> getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(List<Long> doctorIds) {
        this.doctorIds = doctorIds;
    }

    public Set<NhiMetricReportType> getNhiMetricReportTypes() {
        return nhiMetricReportTypes;
    }

    public void setNhiMetricReportTypes(Set<NhiMetricReportType> nhiMetricReportTypes) {
        this.nhiMetricReportTypes = nhiMetricReportTypes;
    }

}