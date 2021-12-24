package io.dentall.totoro.business.service.report.treatment;

import io.dentall.totoro.business.service.report.context.Counter;
import io.dentall.totoro.business.service.report.context.ExcelSheet;
import io.dentall.totoro.business.service.report.dto.SubjectMonthlyNhiVo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.util.List;

import static io.dentall.totoro.business.service.report.context.ReportHelper.calculateColumnWith;
import static io.dentall.totoro.business.service.report.context.ReportHelper.displayDisposalDate;

public class MonthlyNhiSheet extends ExcelSheet {

    public MonthlyNhiSheet() {
    }

    @Override
    public void write() {
        List<SubjectMonthlyNhiVo> data = getReport().getData();
        Font defaultFont = getReport().getBook().getWorkbook().createFont();
        defaultFont.setFontHeightInPoints((short) 11);
        defaultFont.setFontName("Calibri");
        CellStyle dataCellStyle = getReport().getBook().getWorkbook().createCellStyle();
        dataCellStyle.setFont(defaultFont);

        for (SubjectMonthlyNhiVo vo : data) {
            Counter rowCounter = new Counter();
            Sheet sheet = getReport().getBook().getWorkbook().createSheet(vo.getSubjectName());
            long subjectId = vo.getSubjectId();
            createHeader(sheet, rowCounter, defaultFont, vo);

            vo.getSummaryList().forEach(summary -> {
                Row row = sheet.createRow(rowCounter.get());
                Counter cellCounter = new Counter();
                Cell cell;

                cell = row.createCell(cellCounter.get());
                cell.setCellValue(displayDisposalDate(summary.getDisposalMonth()));
                cell.setCellStyle(dataCellStyle);

                if (subjectId != Long.MIN_VALUE) {
                    cell = row.createCell(cellCounter.get());
                    cell.setCellValue(summary.getDoctorName());
                    cell.setCellStyle(dataCellStyle);
                }

                cell = row.createCell(cellCounter.get());
                cell.setCellValue(summary.getProcedureCode());
                cell.setCellStyle(dataCellStyle);

                cell = row.createCell(cellCounter.get());
                cell.setCellValue(summary.getProcedureCount());
                cell.setCellStyle(dataCellStyle);

                cell = row.createCell(cellCounter.get());
                cell.setCellValue(summary.getProcedurePoint());
                cell.setCellStyle(dataCellStyle);

                cell = row.createCell(cellCounter.get());
                cell.setCellValue(summary.getPatientCount());
                cell.setCellStyle(dataCellStyle);
            });
        }
    }

    private void createHeader(Sheet sheet, Counter rowCounter, Font font, SubjectMonthlyNhiVo vo) {
        sheet.setColumnWidth(0, calculateColumnWith(7, 8));
        if (vo.getSubjectId() != Long.MIN_VALUE) {
            sheet.setColumnWidth(1, calculateColumnWith(7, 12));
            sheet.setColumnWidth(2, calculateColumnWith(7, 8));
            sheet.setColumnWidth(3, calculateColumnWith(7, 5));
            sheet.setColumnWidth(4, calculateColumnWith(7, 8));
            sheet.setColumnWidth(5, calculateColumnWith(7, 12));
        } else {
            sheet.setColumnWidth(1, calculateColumnWith(7, 8));
            sheet.setColumnWidth(2, calculateColumnWith(7, 5));
            sheet.setColumnWidth(3, calculateColumnWith(7, 8));
            sheet.setColumnWidth(4, calculateColumnWith(7, 12));
        }

        Row header = sheet.createRow(rowCounter.get());
        XSSFColor cellColor = new XSSFColor(new byte[]{(byte) 243, (byte) 243, (byte) 243}, new DefaultIndexedColorMap());
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        ((XSSFCellStyle) cellStyle).setFillForegroundColor(cellColor);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFont(font);

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
        if (vo.getSubjectId() != Long.MIN_VALUE) {
            cell = header.createCell(cellCounter.get());
            cell.setCellValue("醫師名稱");
            cell.setCellStyle(cellStyle);
        }
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("健保處置");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("件數");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("點數");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("不重複病患");
        cell.setCellStyle(cellStyle);
    }

}
