package io.dentall.totoro.business.service.report.followup;

import io.dentall.totoro.business.service.report.context.ReportSetting;

import java.time.LocalDate;
import java.util.Set;

public class OwnExpenseReportSetting implements ReportSetting {

    private FollowupBookSetting bookSetting;

    private int followupGapMonths;

    private Long procedureId;

    private String procedureName;

    public FollowupBookSetting getBookSetting() {
        return bookSetting;
    }

    public void setBookSetting(FollowupBookSetting bookSetting) {
        this.bookSetting = bookSetting;
    }

    public int getFollowupGapMonths() {
        return followupGapMonths;
    }

    public void setFollowupGapMonths(int followupGapMonths) {
        this.followupGapMonths = followupGapMonths;
    }

    public Long getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(Long procedureId) {
        this.procedureId = procedureId;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public LocalDate getBeginDate() {
        return getBookSetting().getBeginDate();
    }

    public LocalDate getEndDate() {
        return getBookSetting().getEndDate();
    }

    public Set<Long> getIncludeDoctorIds() {
        return getBookSetting().getIncludeDoctorIds();
    }

    public String getSheetName() {
        return getProcedureName() + getFollowupGapMonths() + "個月未回診";
    }

    @Override
    public String toString() {
        return "OwnExpenseReportSetting{" +
            "followupGapMonths=" + followupGapMonths +
            ", procedureId=" + procedureId +
            ", procedureName='" + procedureName + '\'' +
            '}';
    }
}
