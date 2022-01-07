package io.dentall.totoro.business.service.report.treatment;

import io.dentall.totoro.business.service.report.context.ReportSetting;

import java.time.YearMonth;
import java.util.Set;

public class MonthlyOwnExpenseReportSetting implements ReportSetting {

    private YearMonth beginMonth;

    private YearMonth endMonth;

    private Set<Long> includeOwnExpenseIds;

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

    public Set<Long> getIncludeOwnExpenseIds() {
        return includeOwnExpenseIds;
    }

    public void setIncludeOwnExpenseIds(Set<Long> includeOwnExpenseIds) {
        this.includeOwnExpenseIds = includeOwnExpenseIds;
    }

    public Set<Long> getIncludeDoctorIds() {
        return includeDoctorIds;
    }

    public void setIncludeDoctorIds(Set<Long> includeDoctorIds) {
        this.includeDoctorIds = includeDoctorIds;
    }

    @Override
    public String toString() {
        return "MonthlyOwnExpenseReportSetting{" +
            "beginMonth=" + beginMonth +
            ", endMonth=" + endMonth +
            ", includeOwnExpenseIds=" + includeOwnExpenseIds +
            ", includeDoctorIds=" + includeDoctorIds +
            '}';
    }
}
