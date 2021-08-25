package io.dentall.totoro.business.service.nhi.metric.report;

import io.dentall.totoro.business.service.nhi.metric.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;

import java.util.List;
import java.util.Map;

public class SouthAreaReport {
    private static final String SHEET_NAME = "南區-全聯會20項指標";

    public void generateReport(
        Workbook wb,
        Map<String, List<String>> rowContents
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
        row.createCell(1).setCellValue("申報點數\n（抽審以點數最高的醫師計）");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("平均每位患者之醫療耗用值");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("平均取卡格數");
        // 根管相關
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("根管相關");
        row.createCell(1).setCellValue("每季非根管治療點數比率");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("每季根管未完成率");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("半年內自家根管治療再治療率");
        // 補牙相關
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("補牙相關");
        row.createCell(1).setCellValue("每季O.D.點數佔比");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("每季O.D.病患之O.D.耗用點數");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("每季就醫病患之平均O.D.顆數");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("每季O.D.患者之平均填補顆數");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("每季O.D.平均面數");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("第二年自家重補率");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("第三年自家重補率");
        // 備註
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("※其餘牽涉他願資料無法計算之指標，請參照健保署最新公告");
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("※指標數值係依系統累積資料量進行統計。");

        // Styles
        applyTitleStyle(sheet);
        applySheetMergeSection(sheet);
        applySheetTemplate(sheet, 0);
    }

    private void applyTitleStyle(Sheet sheet) {
        int fromRow = 0;
        int toRow = 13;
        int fromCol = 0;
        int toCol = 1;
        for (int i = fromRow; i <= toRow; i++) {
            for (int j = fromCol; j <= toCol; j++) {
                try {
                    ExcelUtil.applyTitleCellStyle(sheet.getRow(i).getCell(j));
                } catch(Exception e) {
                    // ignore exception
                }
            }
        }
    }

    private void applySheetMergeSection(
        Sheet sheet
    ) {
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(7, 13, 0, 0));
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
            new CellRangeAddress(3, 3, 0, 1 + numberOfDoctor),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );
        pt.drawBorders(
            new CellRangeAddress(6, 6, 0, 1 + numberOfDoctor),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );
        pt.drawBorders(
            new CellRangeAddress(13, 13, 0, 1 + numberOfDoctor),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );

        pt.applyBorders(sheet);
    }
}
