package io.dentall.totoro.business.service.report.followup;

import io.dentall.totoro.business.service.report.context.BookSetting;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

public class FollowupBookSetting implements BookSetting {

    private LocalDate beginDate;

    private LocalDate endDate;

    private Set<Long> includeDoctorIds;

    private TeethCleaningReportSetting teethCleaningReportSetting;

    private FluoridationReportSetting fluoridationReportSetting;

    private OdReportSetting odReportSetting;

    private PeriodontalReportSetting periodontalReportSetting;

    private ExtractTeethReportSetting extractTeethReportSetting;

    private EndoReportSetting endoReportSetting;

    private OwnExpenseReportSetting ownExpenseReportSetting;

    private Instant exportTime;

    public TeethCleaningReportSetting getTeethCleaningReportSetting() {
        return teethCleaningReportSetting;
    }

    public void setTeethCleaningReportSetting(TeethCleaningReportSetting teethCleaningReportSetting) {
        this.teethCleaningReportSetting = teethCleaningReportSetting;
        this.teethCleaningReportSetting.setBookSetting(this);
    }

    public OdReportSetting getOdReportSetting() {
        return odReportSetting;
    }

    public void setOdReportSetting(OdReportSetting odReportSetting) {
        this.odReportSetting = odReportSetting;
        this.odReportSetting.setBookSetting(this);
    }

    public FluoridationReportSetting getFluoridationReportSetting() {
        return fluoridationReportSetting;
    }

    public void setFluoridationReportSetting(FluoridationReportSetting fluoridationReportSetting) {
        this.fluoridationReportSetting = fluoridationReportSetting;
        this.fluoridationReportSetting.setBookSetting(this);
    }

    public PeriodontalReportSetting getPeriodontalReportSetting() {
        return periodontalReportSetting;
    }

    public void setPeriodontalReportSetting(PeriodontalReportSetting periodontalReportSetting) {
        this.periodontalReportSetting = periodontalReportSetting;
        this.periodontalReportSetting.setBookSetting(this);
    }

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

    public Set<Long> getIncludeDoctorIds() {
        return includeDoctorIds;
    }

    public void setIncludeDoctorIds(Set<Long> includeDoctorIds) {
        this.includeDoctorIds = includeDoctorIds;
    }

    public ExtractTeethReportSetting getExtractTeethReportSetting() {
        return extractTeethReportSetting;
    }

    public void setExtractTeethReportSetting(ExtractTeethReportSetting extractTeethReportSetting) {
        this.extractTeethReportSetting = extractTeethReportSetting;
        this.extractTeethReportSetting.setBookSetting(this);
    }

    public EndoReportSetting getEndoReportSetting() {
        return endoReportSetting;
    }

    public void setEndoReportSetting(EndoReportSetting endoReportSetting) {
        this.endoReportSetting = endoReportSetting;
        this.endoReportSetting.setBookSetting(this);
    }

    public OwnExpenseReportSetting getOwnExpenseReportSetting() {
        return ownExpenseReportSetting;
    }

    public void setOwnExpenseReportSetting(OwnExpenseReportSetting ownExpenseReportSetting) {
        this.ownExpenseReportSetting = ownExpenseReportSetting;
        this.ownExpenseReportSetting.setBookSetting(this);
    }

    public Instant getExportTime() {
        if (exportTime == null) {
            exportTime = Instant.now();
        }
        return exportTime;
    }

    @Override
    public String toString() {
        return "FollowupBookSetting{" +
            "beginDate=" + beginDate +
            ", endDate=" + endDate +
            ", includeDoctorIds=" + includeDoctorIds +
            ", teethCleaningReportSetting=" + teethCleaningReportSetting +
            ", fluoridationReportSetting=" + fluoridationReportSetting +
            ", odReportSetting=" + odReportSetting +
            ", periodontalReportSetting=" + periodontalReportSetting +
            ", extractTeethReportSetting=" + extractTeethReportSetting +
            ", endoReportSetting=" + endoReportSetting +
            ", ownExpenseReportSetting=" + ownExpenseReportSetting +
            '}';
    }
}
