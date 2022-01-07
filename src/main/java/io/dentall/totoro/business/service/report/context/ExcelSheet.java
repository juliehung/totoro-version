package io.dentall.totoro.business.service.report.context;

public abstract class ExcelSheet implements ReportSheet {

    private ExcelReport report;

    public ExcelReport getReport() {
        return report;
    }

    public void setReport(ExcelReport report) {
        this.report = report;
    }
}
