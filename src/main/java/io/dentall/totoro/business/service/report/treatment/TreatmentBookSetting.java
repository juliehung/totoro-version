package io.dentall.totoro.business.service.report.treatment;

import io.dentall.totoro.business.service.report.context.BookSetting;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

public class TreatmentBookSetting implements BookSetting {

    private LocalDate beginDate;

    private LocalDate endDate;

    private Set<Long> includeTreatmentIds;

    private Set<String> includeTreatmentCodes;

    private DailyNhiReportSetting dailyNhiReportSetting;

    private DailyOwnExpenseReportSetting dailyOwnExpenseReportSetting;

    private DailyDrugReportSetting dailyDrugReportSetting;

    private MonthlyNhiReportSetting monthlyNhiReportSetting;

    private MonthlyOwnExpenseReportSetting monthlyOwnExpenseReportSetting;

    private MonthlyDrugReportSetting monthlyDrugReportSetting;

    private Instant exportTime;

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Set<Long> getIncludeTreatmentIds() {
        return includeTreatmentIds;
    }

    public Set<String> getIncludeTreatmentCodes() {
        return includeTreatmentCodes;
    }

    public void setIncludeTreatmentCodes(Set<String> includeTreatmentCodes) {
        this.includeTreatmentCodes = includeTreatmentCodes;
    }

    public DailyNhiReportSetting getDailyNhiReportSetting() {
        return dailyNhiReportSetting;
    }

    public void setDailyNhiReportSetting(DailyNhiReportSetting dailyNhiReportSetting) {
        this.dailyNhiReportSetting = dailyNhiReportSetting;
        this.includeTreatmentIds = dailyNhiReportSetting.getIncludeNhiProcedureIds();
        this.beginDate = dailyNhiReportSetting.getBeginDate();
        this.endDate = dailyNhiReportSetting.getEndDate();
    }

    public DailyOwnExpenseReportSetting getDailyOwnExpenseReportSetting() {
        return dailyOwnExpenseReportSetting;
    }

    public void setDailyOwnExpenseReportSetting(DailyOwnExpenseReportSetting dailyOwnExpenseReportSetting) {
        this.dailyOwnExpenseReportSetting = dailyOwnExpenseReportSetting;
        this.includeTreatmentIds = dailyOwnExpenseReportSetting.getIncludeOwnExpenseIds();
        this.beginDate = dailyOwnExpenseReportSetting.getBeginDate();
        this.endDate = dailyOwnExpenseReportSetting.getEndDate();
    }

    public DailyDrugReportSetting getDailyDrugReportSetting() {
        return dailyDrugReportSetting;
    }

    public void setDailyDrugReportSetting(DailyDrugReportSetting dailyDrugReportSetting) {
        this.dailyDrugReportSetting = dailyDrugReportSetting;
        this.includeTreatmentIds = dailyDrugReportSetting.getIncludeDrugIds();
        this.beginDate = dailyDrugReportSetting.getBeginDate();
        this.endDate = dailyDrugReportSetting.getEndDate();
    }

    public MonthlyNhiReportSetting getMonthlyNhiReportSetting() {
        return monthlyNhiReportSetting;
    }

    public void setMonthlyNhiReportSetting(MonthlyNhiReportSetting monthlyNhiReportSetting) {
        this.monthlyNhiReportSetting = monthlyNhiReportSetting;
        this.includeTreatmentIds = monthlyNhiReportSetting.getIncludeNhiProcedureIds();
        this.beginDate = monthlyNhiReportSetting.getBeginMonth().atDay(1);
        this.endDate = monthlyNhiReportSetting.getEndMonth().atDay(1);
    }

    public MonthlyOwnExpenseReportSetting getMonthlyOwnExpenseReportSetting() {
        return monthlyOwnExpenseReportSetting;
    }

    public void setMonthlyOwnExpenseReportSetting(MonthlyOwnExpenseReportSetting monthlyOwnExpenseReportSetting) {
        this.monthlyOwnExpenseReportSetting = monthlyOwnExpenseReportSetting;
        this.includeTreatmentIds = monthlyOwnExpenseReportSetting.getIncludeOwnExpenseIds();
        this.beginDate = monthlyOwnExpenseReportSetting.getBeginMonth().atDay(1);
        this.endDate = monthlyOwnExpenseReportSetting.getEndMonth().atDay(1);
    }

    public MonthlyDrugReportSetting getMonthlyDrugReportSetting() {
        return monthlyDrugReportSetting;
    }

    public void setMonthlyDrugReportSetting(MonthlyDrugReportSetting monthlyDrugReportSetting) {
        this.monthlyDrugReportSetting = monthlyDrugReportSetting;
        this.includeTreatmentIds = monthlyDrugReportSetting.getIncludeDrugIds();
        this.beginDate = monthlyDrugReportSetting.getBeginMonth().atDay(1);
        this.endDate = monthlyDrugReportSetting.getEndMonth().atDay(1);
    }

    public Instant getExportTime() {
        if (exportTime == null) {
            exportTime = Instant.now();
        }
        return exportTime;
    }

    @Override
    public String toString() {
        return "TreatmentBookSetting{" +
            "beginDate=" + beginDate +
            ", endDate=" + endDate +
            ", includeTreatmentIds=" + includeTreatmentIds +
            ", includeTreatmentCodes=" + includeTreatmentCodes +
            ", dailyNhiReportSetting=" + dailyNhiReportSetting +
            ", dailyOwnExpenseReportSetting=" + dailyOwnExpenseReportSetting +
            ", dailyDrugReportSetting=" + dailyDrugReportSetting +
            ", monthlyNhiReportSetting=" + monthlyNhiReportSetting +
            ", monthlyOwnExpenseReportSetting=" + monthlyOwnExpenseReportSetting +
            ", monthlyDrugReportSetting=" + monthlyDrugReportSetting +
            '}';
    }
}
