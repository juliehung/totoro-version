package io.dentall.totoro.business.service.report.followup;

import io.dentall.totoro.business.service.report.context.ReportSetting;

import java.time.LocalDate;
import java.util.Set;

public class TeethCleaningReportSetting implements ReportSetting {

    private FollowupBookSetting bookSetting;

    public FollowupBookSetting getBookSetting() {
        return bookSetting;
    }

    public void setBookSetting(FollowupBookSetting bookSetting) {
        this.bookSetting = bookSetting;
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
        return "可洗牙名單";
    }

    @Override
    public String toString() {
        return "TeethCleaningReportSetting{}";
    }
}
