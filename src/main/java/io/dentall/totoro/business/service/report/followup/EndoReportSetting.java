package io.dentall.totoro.business.service.report.followup;

import io.dentall.totoro.business.service.report.context.ReportSetting;

import java.time.LocalDate;
import java.util.Set;

public class EndoReportSetting implements ReportSetting {

    private FollowupBookSetting bookSetting;

    private int followupGapMonths;

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
        return "根管後" + getFollowupGapMonths() + "個月未回診";
    }

    @Override
    public String toString() {
        return "EndoReportSetting{" +
            "followupGapMonths=" + followupGapMonths +
            '}';
    }
}
