package io.dentall.totoro.business.service.report.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelReport implements Report {

    private static final Logger log = LoggerFactory.getLogger(ExcelReport.class);

    private final ReportSetting setting;

    private ExcelBook book;

    private ExcelSheet sheet;

    private ReportDataProvider<ReportSetting, ?> dataProvider;

    public ExcelReport(ReportSetting setting) {
        this.setting = setting;
    }

    @Override
    public void output() {
        sheet.write();
    }

    public ExcelReport bind(ExcelBook book) {
        this.book = book;
        this.book.addReport(this);
        return this;
    }

    public ExcelReport bind(ExcelSheet sheet) {
        this.sheet = sheet;
        this.sheet.setReport(this);
        return this;
    }

    public ExcelBook getBook() {
        return book;
    }

    public <T> T getData() {
        return (T) dataProvider.get(setting);
    }

    public void setDataProvider(ReportDataProvider<? extends ReportSetting, ?> dataProvider) {
        this.dataProvider = (ReportDataProvider<ReportSetting, ?>) dataProvider;
    }

}
