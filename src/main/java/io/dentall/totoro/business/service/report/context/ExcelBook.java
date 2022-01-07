package io.dentall.totoro.business.service.report.context;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelBook implements ReportBook {

    private final Workbook wb;

    private final OutputStream outputStream;

    private final List<ExcelReport> reportList = new ArrayList<>();

    public ExcelBook(OutputStream outputStream) {
        this.outputStream = outputStream;
        wb = new XSSFWorkbook();
    }

    public Workbook getWorkbook() {
        return wb;
    }

    public void addReport(ExcelReport report) {
        reportList.add(report);
    }

    @Override
    public void publish() throws Exception {
        reportList.forEach(ExcelReport::output);
        wb.write(this.outputStream);
    }
}
