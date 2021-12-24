package io.dentall.totoro.business.service.report.followup;

import io.dentall.totoro.business.service.report.context.Counter;
import io.dentall.totoro.business.service.report.context.ExcelSheet;
import io.dentall.totoro.business.service.report.dto.PeriodontalVo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.util.List;

import static io.dentall.totoro.business.service.report.context.ReportHelper.*;

public class PeriodontalSheet extends ExcelSheet {

    private final Counter rowCounter = new Counter();

    private final PeriodontalReportSetting setting;

    public PeriodontalSheet(PeriodontalReportSetting setting) {
        this.setting = setting;
    }

    @Override
    public void write() {
        Sheet sheet = getReport().getBook().getWorkbook().createSheet(setting.getSheetName());
        Font defaultFont = sheet.getWorkbook().createFont();
        defaultFont.setFontHeightInPoints((short) 11);
        defaultFont.setFontName("Calibri");
        CellStyle dataCellStyle = sheet.getWorkbook().createCellStyle();
        dataCellStyle.setFont(defaultFont);
        createNote(sheet);
        createHeader(sheet, defaultFont);
        List<PeriodontalVo> data = getReport().getData();
        Cell cell;

        for (PeriodontalVo vo : data) {
            Row row = sheet.createRow(rowCounter.get());
            Counter cellCounter = new Counter();
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
            cell.setCellValue(displayDisposalDate(vo.getP4001Date()));
            cell.setCellStyle(dataCellStyle);

            cell = row.createCell(cellCounter.get());
            cell.setCellValue(displayDisposalDate(vo.getP4002Date()));
            cell.setCellStyle(dataCellStyle);

            cell = row.createCell(cellCounter.get());
            cell.setCellValue(displayDisposalDate(vo.getP4003Date()));
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

    private void createNote(Sheet sheet) {
        String note1 = "追蹤日期：" + displayDisposalDate(setting.getBeginDate()) + "~" + displayDisposalDate(setting.getEndDate()) + "\n";
        String note2 = "註1. P4002(91022C)與P4003(91023C)需隔間28天(含)以上\n 2. P4001(91021C)~P4003(91023C)療程需在180天內完成";
        Font normalFont = sheet.getWorkbook().createFont();
        normalFont.setColor(Font.COLOR_NORMAL);
        normalFont.setFontName("Calibri");
        Font redFont = sheet.getWorkbook().createFont();
        redFont.setColor(Font.COLOR_RED);
        redFont.setFontName("Calibri");
        XSSFRichTextString richTextString = new XSSFRichTextString();
        richTextString.setString(note1 + note2);
        richTextString.applyFont(0, note1.length(), normalFont);
        richTextString.applyFont(note1.length(), note1.length() + note2.length(), redFont);

        Row row = sheet.createRow(rowCounter.get());
        Cell cell = row.createCell(0);
        cell.setCellValue(richTextString);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
    }

    private void createHeader(Sheet sheet, Font font) {
        sheet.setColumnWidth(0, calculateColumnWith(7, 12));
        sheet.setColumnWidth(1, calculateColumnWith(7, 12));
        sheet.setColumnWidth(2, calculateColumnWith(7, 18));
        sheet.setColumnWidth(3, calculateColumnWith(7, 15));
        sheet.setColumnWidth(4, calculateColumnWith(7, 15));
        sheet.setColumnWidth(5, calculateColumnWith(7, 15));
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
        cell.setCellValue("主治醫師");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("病患姓名");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("病患生日(年齡)");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("P4001(91021)日");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("P4002(91022)日");
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellCounter.get());
        cell.setCellValue("P4003(91023)日");
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
