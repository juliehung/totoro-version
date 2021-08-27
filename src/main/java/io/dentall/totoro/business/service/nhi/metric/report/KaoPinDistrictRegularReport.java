package io.dentall.totoro.business.service.nhi.metric.report;

import io.dentall.totoro.business.service.nhi.metric.dto.KaoPingDistrictRegularDto;
import io.dentall.totoro.business.service.nhi.metric.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;

import java.util.List;

public class KaoPinDistrictRegularReport {
    private static final String SHEET_NAME = "高屏區-牙醫門診總額抽審原則";

    public void generateReport(
        Workbook wb,
        List<KaoPingDistrictRegularDto> contents
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
        row.createCell(1).setCellValue("當季醫師月平均醫療費用點數");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("當季就醫病患平均耗用值");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("平均耗格數");
        // 根管相關
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("根管相關");
        row.createCell(1).setCellValue("當季根管治療未完成率(需<30%)");
        // 補牙相關
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("補牙相關");
        row.createCell(1).setCellValue("當季O.D. 之平均面數");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("當季每位O.D.患者平均O.D.耗用值");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("當季O.D.點數佔比");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("當季就醫病患平均O.D.顆數");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("二年內自家重補率");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("第三年自家重補率");
        // 洗牙相關
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("洗牙相關");
        row.createCell(1).setCellValue("當季洗牙醫令項目佔比");
        // 拔牙相關
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("拔牙相關");
        row.createCell(1).setCellValue("拔牙前半年耗用值");
        // 備註
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("※其餘牽涉他願資料無法計算之指標，請參照健保署最新公告");
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("※指標數值係依系統累積資料量進行統計。");

        // Assign data
        for (int colIdx = 0; colIdx < contents.size(); colIdx++){
            KaoPingDistrictRegularDto content = contents.get(colIdx);
            int rowIdx = 0;
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getDoctor().getDoctorName());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getK1().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getK2().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getK11().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getJ2h5().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getK10().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getK3().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getK4().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getK5().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getK6().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getK7().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getK12().toString());
            sheet.getRow(rowIdx++).createCell(colIdx).setCellValue(content.getK14().toString());
        }

        // Styles
        applyTitleStyle(sheet);
        applySheetMergeSection(sheet);
        applySheetTemplate(sheet, 0);
    }

    private void applyTitleStyle(Sheet sheet) {
        int fromRow = 0;
        int toRow = 12;
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

    private void applySheetMergeSection(Sheet sheet) {
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(5, 10, 0, 0));
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
            new CellRangeAddress(4, 4, 0, 1 + numberOfDoctor),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );
        pt.drawBorders(
            new CellRangeAddress(10, 10, 0, 1 + numberOfDoctor),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );
        pt.drawBorders(
            new CellRangeAddress(11, 11, 0, 1 + numberOfDoctor),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );
        pt.drawBorders(
            new CellRangeAddress(12, 12, 0, 1 + numberOfDoctor),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );

        pt.applyBorders(sheet);
    }
}
