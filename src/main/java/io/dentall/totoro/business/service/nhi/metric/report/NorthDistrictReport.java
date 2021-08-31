package io.dentall.totoro.business.service.nhi.metric.report;

import io.dentall.totoro.business.service.nhi.metric.dto.NorthDistrictDto;
import io.dentall.totoro.business.service.nhi.metric.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NorthDistrictReport {
    private static final String SHEET_NAME = "北區-牙醫門診總額快速通關免審專業審查篩選指標";

    public void generateReport(
        Workbook wb,
        List<NorthDistrictDto> contents
    ) throws Exception {
        if (wb == null) {
            throw new Exception("Must new a workbook first before create sheet");
        }

        Sheet sheet = wb.createSheet(SHEET_NAME);
        Row row = null;
        int rowCounter  = 0;
        // Header
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("類型");
        row.createCell(1).setCellValue("指標項目");
        // 醫療費用
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("醫療費用");
        row.createCell(1).setCellValue("申報點數");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("每件平均申請費用點數");
        // 根管相關
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("根管相關");
        row.createCell(1).setCellValue("一年內根管為完成率(需<28.74)");
        // 補牙相關
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("補牙相關");
        row.createCell(1).setCellValue("兩年內重補率(需≦4.5%)");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("乳牙一年半重補率(需≦10%)");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("89013C（複合體填充）3 個月申報醫令件數且申報病患年齡小於50之一另佔率(需<40%)");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("牙體復形(OD)合計申報點數佔牙科處置申報點數比率(需<65.38%)");
        // 牙周相關
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("牙周相關");
        row.createCell(1).setCellValue("申報91018C醫令件數");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("申報91023C醫令件數");
        // 備註
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("※其餘牽涉他願資料無法計算之指標，請參照健保署最新公告");
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("※指標數值係依系統累積資料量進行統計。");

        // Assign data
        for (int colIdx = 2; colIdx < contents.size(); colIdx++){
            NorthDistrictDto content = contents.get(colIdx);
            int rowIdx = 0;
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getDoctor().getDoctorName());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getA10().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getA7().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getA8().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getA14().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getA15h1().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getA17h2().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getA9().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getA18h1().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getA18h2().toString());
        }

        // Styles
        applyTitleStyle(sheet);
        applySheetMergeSection(sheet);
        applySheetTemplate(sheet, 0);
    }

    private void applyTitleStyle(Sheet sheet) {
        int fromRow = 0;
        int toRow = 9;
        int fromCol = 0;
        int toCol = 1;
        for (int i = fromRow; i <= toRow; i++) {
            for (int j = fromCol; j <= toCol; j++) {
                try {
                    // ExcelUtil.applyTitleCellStyle(sheet.getRow(i).getCell(j));
                } catch(Exception e) {
                    // ignore exception
                }
            }
        }
    }

    private void applySheetMergeSection(
        Sheet sheet
    ) {
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(4, 7, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(8, 9, 0, 0));
        sheet.setColumnWidth(0, ExcelUtil.columnWidth(5, 12));
        sheet.setColumnWidth(1, ExcelUtil.columnWidth(15, 12));
    }

    private void applySheetTemplate(
        Sheet sheet,
        int numberOfDoctor
    ) {
        PropertyTemplate pt = new PropertyTemplate();

        pt.drawBorders(
            new CellRangeAddress(0, 0, 0, 1 + numberOfDoctor),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );
        pt.drawBorders(
            new CellRangeAddress(2, 2, 0, 1 + numberOfDoctor),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );
        pt.drawBorders(
            new CellRangeAddress(3, 3, 0, 1 + numberOfDoctor),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );
        pt.drawBorders(
            new CellRangeAddress(7, 7, 0, 1 + numberOfDoctor),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );
        pt.drawBorders(
            new CellRangeAddress(9, 9, 0, 1 + numberOfDoctor),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );

        pt.applyBorders(sheet);
    }
}
