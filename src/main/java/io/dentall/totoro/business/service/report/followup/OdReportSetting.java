package io.dentall.totoro.business.service.report.followup;

import io.dentall.totoro.business.service.report.context.ReportSetting;

import java.time.LocalDate;
import java.util.Set;

public class OdReportSetting implements ReportSetting {

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
        return "已補牙名單";
    }

    @Override
    public String toString() {
        return "ODReportSetting{}";
    }
}
