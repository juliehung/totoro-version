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
    private Integer yyyymm;

    @NotNull
    private Set<NhiMetricReportType> nhiMetricReportTypes;

    @NotNull
    private Set<Long> selectedTargets;

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
