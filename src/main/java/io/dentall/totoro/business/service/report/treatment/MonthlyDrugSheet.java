package io.dentall.totoro.business.service.report.treatment;

import io.dentall.totoro.business.service.report.context.Counter;
import io.dentall.totoro.business.service.report.context.ExcelSheet;
import io.dentall.totoro.business.service.report.dto.SubjectMonthlyDrugVo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.util.List;

import static io.dentall.totoro.business.service.report.context.ReportHelper.displayDisposalDate;

public class MonthlyDrugSheet extends ExcelSheet {

    public MonthlyDrugSheet() {
    }

    @Override
    public void write() {
        List<SubjectMonthlyDrugVo> data = getReport().getData();

        for (SubjectMonthlyDrugVo vo : data) {
            Counter rowCounter = new Counter();
            Sheet sheet = getReport().getBook().getWorkbook().createSheet(vo.getSubjectName());
            createHeader(sheet, rowCounter);

            vo.getSummaryList().forEach(summary -> {
                Row row = sheet.createRow(rowCounter.get());
                Counter cellCounter = new Counter();
                row.createCell(cellCounter.get()).setCellValue(displayDisposalDate(summary.getDisposalMonth()));
                row.createCell(cellCounter.get()).setCellValue(summary.getDrugName());
                row.createCell(cellCounter.get()).setCellValue(summary.getDrugNhiCode());
                row.createCell(cellCounter.get()).setCellValue(summary.getProcedureCount());
                row.createCell(cellCounter.get()).setCellValue(summary.getPatientCount());
            });
        }
    }

    private void createHeader(Sheet sheet, Counter rowCounter) {
        Row header = sheet.createRow(rowCounter.get());
        sheet.getWorkbook().createCellStyle();
        XSSFColor cellColor = new XSSFColor(new byte[]{(byte) 243, (byte) 243, (byte) 243}, new DefaultIndexedColorMap());
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        ((XSSFCellStyle) cellStyle).setFillForegroundColor(cellColor);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFColor borderColor = new XSSFColor(new byte[]{(byte) 222, (byte) 223, (byte) 223}, new DefaultIndexedColorMap());
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        ((XSSFCellStyle) cellStyle).setTopBorderColor(borderColor);
        ((XSSFCellStyle) cellStyle).setBottomBorderColor(borderColor);
        ((XSSFCellStyle) cellStyle).setLeftBorderColor(borderColor);
        ((XSSFCellStyle) cellStyle).setRightBorderColor(borderColor);

        Counter cellCounter = new Counter();
        Cell cell = header.createCell(cellCounter.get());
        cell.setCellValue("治療月份");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("藥品");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("健保代碼");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("件數");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("不重複病患");
        cell.setCellStyle(cellStyle);
    }

}
