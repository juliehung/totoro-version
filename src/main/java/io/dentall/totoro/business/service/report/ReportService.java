package io.dentall.totoro.business.service.report;

import io.dentall.totoro.business.service.report.context.BookSetting;

import java.util.Map;

public interface ReportService {

    void completeSetting(BookSetting setting);

    String getBookFileName(BookSetting setting);

    Map<String, String> getReportAttribute(BookSetting setting);
}
