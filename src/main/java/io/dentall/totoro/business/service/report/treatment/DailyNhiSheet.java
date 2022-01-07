package io.dentall.totoro.business.service.report.treatment;

import io.dentall.totoro.business.service.report.context.Counter;
import io.dentall.totoro.business.service.report.context.ExcelSheet;
import io.dentall.totoro.business.service.report.dto.NhiVo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.util.List;

import static io.dentall.totoro.business.service.report.context.ReportHelper.*;

public class DailyNhiSheet extends ExcelSheet {

    private final Counter rowCounter = new Counter();

    public DailyNhiSheet() {
    }

    @Override
    public void write() {
        Sheet sheet = getReport().getBook().getWorkbook().createSheet("健保");
        Font defaultFont = sheet.getWorkbook().createFont();
        defaultFont.setFontHeightInPoints((short) 11);
        defaultFont.setFontName("Calibri");
        createHeader(sheet, defaultFont);
        CellStyle dataCellStyle = sheet.getWorkbook().createCellStyle();
        dataCellStyle.setFont(defaultFont);
        List<NhiVo> data = getReport().getData();
        Cell cell;

        for (NhiVo vo : data) {
            Row row = sheet.createRow(rowCounter.get());
            Counter cellCounter = new Counter();
            cell = row.createCell(cellCounter.get());
            cell.setCellValue(displayDisposalDate(vo.getDisposalDate()));
            cell.setCellStyle(dataCellStyle);

            cell = row.createCell(cellCounter.get());
            cell.setCellValue(vo.getDoctorName());
            cell.setCellStyle(dataCellStyle);

            cell = row.createCell(cellCounter.get());
            cell.setCellValue(vo.getPatientName());
            cell.setCellStyle(dataCellStyle);

            cell = row.createCell(cellCounter.get());
            cell.setCellValue(displayBirthAge(vo.getPatientBirth(), displayAge(vo.getPatientAge())));
            cell.setCellStyle(dataCellStyle);

            cell = row.createCell(cellCounter.get());
            cell.setCellValue(vo.getProcedureCode());
            cell.setCellStyle(dataCellStyle);

            cell = row.createCell(cellCounter.get());
            cell.setCellValue(vo.getProcedureTooth());
            cell.setCellStyle(dataCellStyle);

            cell = row.createCell(cellCounter.get());
            cell.setCellValue(displayFutureAppointmentMemo(vo.getFutureAppointmentList()));
            cell.setCellStyle(dataCellStyle);

            cell = row.createCell(cellCounter.get());
            cell.setCellValue(vo.getPatientPhone());
            cell.setCellStyle(dataCellStyle);

            cell = row.createCell(cellCounter.get());
            cell.setCellValue(vo.getPatientNote());
            cell.setCellStyle(dataCellStyle);
        }
    }

    private void createHeader(Sheet sheet, Font font) {
        sheet.setColumnWidth(0, calculateColumnWith(7, 10));
        sheet.setColumnWidth(1, calculateColumnWith(7, 12));
        sheet.setColumnWidth(2, calculateColumnWith(7, 12));
        sheet.setColumnWidth(3, calculateColumnWith(7, 18));
        sheet.setColumnWidth(4, calculateColumnWith(7, 10));
        sheet.setColumnWidth(5, calculateColumnWith(7, 5));
        sheet.setColumnWidth(6, calculateColumnWith(7, 40));
        sheet.setColumnWidth(7, calculateColumnWith(7, 12));
        sheet.setColumnWidth(8, calculateColumnWith(7, 80));

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
        cell.setCellValue("治療日期");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("主治醫師");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("病患姓名");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("病患生日(年齡)");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("處置項目");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("牙位");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("未來預約_預約內容");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("聯絡電話");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("服務備註");
        cell.setCellStyle(cellStyle);
    }

}
