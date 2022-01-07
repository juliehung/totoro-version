package io.dentall.totoro.business.service.report.treatment;

import io.dentall.totoro.business.service.report.context.ReportSetting;

import java.time.YearMonth;
import java.util.Set;

public class MonthlyDrugReportSetting implements ReportSetting {

    private YearMonth beginMonth;

    private YearMonth endMonth;

    private Set<Long> includeDrugIds;

    private Set<Long> includeDoctorIds;

    public YearMonth getBeginMonth() {
        return beginMonth;
    }

    public void setBeginMonth(YearMonth beginMonth) {
        this.beginMonth = beginMonth;
    }

    public YearMonth getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(YearMonth endMonth) {
        this.endMonth = endMonth;
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
        return "MonthlyDrugReportSetting{" +
            "beginMonth=" + beginMonth +
            ", endMonth=" + endMonth +
            ", includeDrugIds=" + includeDrugIds +
            ", includeDoctorIds=" + includeDoctorIds +
            '}';
    }
}
