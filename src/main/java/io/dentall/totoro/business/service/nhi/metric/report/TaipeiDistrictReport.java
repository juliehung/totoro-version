package io.dentall.totoro.business.service.nhi.metric.report;

import io.dentall.totoro.business.service.nhi.metric.dto.TaipeiDistrictDto;
import io.dentall.totoro.business.service.nhi.metric.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;

import java.util.List;

public class TaipeiDistrictReport {
    private static final String SHEET_NAME = "台北區-服務品質控管";

    public void generateReport(
        Workbook wb,
        List<TaipeiDistrictDto> contents
    ) throws Exception {
        if (wb == null) {
            throw new Exception("Must new a workbook first before create sheet");
        }
        Font f = wb.createFont();
        f.setColor(Font.COLOR_RED);

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
        row.createCell(1).setCellValue("合計點數");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("當季就醫病患平均耗用值");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("申報點數");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("新開業/特約院所總額指標控管不得超過70萬點");
        // 根管相關
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("根管相關");
        row.createCell(1).setCellValue("根管治療未完成率（季）");
        // 補牙相關
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("補牙相關");
        row.createCell(1).setCellValue("當季OD耗用值");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("恆牙兩年自家重補率");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("平均每位病人填補顆樹");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("三年自家重補率");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("每季平均填補面數");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("平均每位 OD 患者填補顆數");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("OD 點數比率");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("三年恆牙自家重補率");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("乳牙一年半自家重補率");
        // 備註
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("※其餘牽涉他願資料無法計算之指標，請參照健保署最新公告");
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("※指標數值係依系統累積資料量進行統計。");

        // Assign data
        for (int colIdx = 0; colIdx < contents.size(); colIdx++) {
            TaipeiDistrictDto content = contents.get(colIdx);
            int rowIdx = 0;
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getDoctor().getDoctorName());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getL22().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getF1h2().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getF3h1().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getF4h3().toString());
//            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.get().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getF1h3().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getF2h4().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getF3h2().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getF5h3().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getF5h4().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getF5h5().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getF5h6().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getF5h7().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getF5h8().toString());
        }

        // Styles
        applyTitleStyle(sheet);
        applySheetMergeSection(sheet);
        applySheetTemplate(sheet, 0);
    }

    private void applyTitleStyle(Sheet sheet) {
        int fromRow = 0;
        int toRow = 14;
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
        sheet.addMergedRegion(new CellRangeAddress(1, 4, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(6, 14, 0, 0));
        sheet.setColumnWidth(0, ExcelUtil.columnWidth(5, 12));
        sheet.setColumnWidth(1, ExcelUtil.columnWidth(15, 12));
    }

    private void applySheetTemplate(
        Sheet sheet,
        int numberOfDocker
    ) {
        PropertyTemplate pt = new PropertyTemplate();

        pt.drawBorders(
            new CellRangeAddress(0, 0, 0, 1 + numberOfDocker),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );
        pt.drawBorders(
            new CellRangeAddress(4, 4, 0, 1 + numberOfDocker),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );
        pt.drawBorders(
            new CellRangeAddress(5, 5, 0, 1 + numberOfDocker),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );
        pt.drawBorders(
            new CellRangeAddress(14, 14, 0, 1 + numberOfDocker),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );

        pt.applyBorders(sheet);
    }
}
