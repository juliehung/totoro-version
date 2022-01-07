package io.dentall.totoro.business.service.report.treatment;

import io.dentall.totoro.business.service.report.context.ReportSetting;

import java.time.LocalDate;
import java.util.Set;

public class DailyNhiReportSetting implements ReportSetting {

    private LocalDate beginDate;

    private LocalDate endDate;

    private Set<Long> includeNhiProcedureIds;

    private Set<Long> includeDoctorIds;

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Long> getIncludeNhiProcedureIds() {
        return includeNhiProcedureIds;
    }

    public void setIncludeNhiProcedureIds(Set<Long> includeNhiProcedureIds) {
        this.includeNhiProcedureIds = includeNhiProcedureIds;
    }

    public Set<Long> getIncludeDoctorIds() {
        return includeDoctorIds;
    }

    public void setIncludeDoctorIds(Set<Long> includeDoctorIds) {
        this.includeDoctorIds = includeDoctorIds;
    }

    @Override
    public String toString() {
        return "DailyNhiReportSetting{" +
            "beginDate=" + beginDate +
            ", endDate=" + endDate +
            ", includeNhiProcedureIds=" + includeNhiProcedureIds +
            ", includeDoctorIds=" + includeDoctorIds +
            '}';
    }
}
