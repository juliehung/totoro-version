package io.dentall.totoro.business.service.report;

import io.dentall.totoro.business.service.report.context.Report;
import io.dentall.totoro.business.service.report.context.ReportSetting;

public interface ReportBuilderService {

    Report build(ReportSetting setting);

}
