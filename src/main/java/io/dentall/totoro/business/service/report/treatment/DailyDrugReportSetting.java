package io.dentall.totoro.business.service.report.treatment;

import io.dentall.totoro.business.service.report.context.ReportSetting;

import java.time.LocalDate;
import java.util.Set;

public class DailyDrugReportSetting implements ReportSetting {

    private LocalDate beginDate;

    private LocalDate endDate;

    private Set<Long> includeDrugIds;

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

    public Set<Long> getIncludeDrugIds() {
        return includeDrugIds;
    }

    public void setIncludeDrugIds(Set<Long> includeDrugIds) {
        this.includeDrugIds = includeDrugIds;
    }

    public Set<Long> getIncludeDoctorIds() {
        return includeDoctorIds;
    }

    public void setIncludeDoctorIds(Set<Long> includeDoctorIds) {
        this.includeDoctorIds = includeDoctorIds;
    }

    @Override
    public String toString() {
        return "DailyDrugReportSetting{" +
            "beginDate=" + beginDate +
            ", endDate=" + endDate +
            ", includeDrugIds=" + includeDrugIds +
            ", includeDoctorIds=" + includeDoctorIds +
            '}';
    }
}
