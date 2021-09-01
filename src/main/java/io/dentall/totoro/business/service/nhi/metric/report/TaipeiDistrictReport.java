package io.dentall.totoro.business.service.nhi.metric.report;

import io.dentall.totoro.business.service.nhi.metric.dto.TaipeiDistrictDto;
import io.dentall.totoro.business.service.nhi.metric.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaipeiDistrictReport {
    private static final String SHEET_NAME = "台北區-服務品質控管";

    public void generateReport(
        Workbook wb,
        Map<ExcelUtil.SupportedCellStyle, CellStyle> csm,
        List<TaipeiDistrictDto> contents
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
        row.createCell(1).setCellValue("平均每位病人填補顆數");
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
        for (int contentIdx = 0; contentIdx < contents.size(); contentIdx++) {
            TaipeiDistrictDto content = contents.get(contentIdx);
            int rowIdx = 0;
            int colIdx = contentIdx + 2;
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.USER), content.getDoctor().getDoctorName());

            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getL1().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getF1h2().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getF3h1().doubleValue());

            if (content.getF4h3().doubleValue() >= 700000) {
                ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.RED_REAL_NUMBER), content.getF4h3().doubleValue());
            } else {
                ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getF4h3().doubleValue());
            }

            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.PERCENTAGE_NUMBER), content.getI12().doubleValue() / 100);
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getF1h3().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.PERCENTAGE_NUMBER), content.getF2h4().doubleValue() / 100);
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getF3h2().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.PERCENTAGE_NUMBER), content.getF5h3().doubleValue() / 100);
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getF5h4().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getF5h5().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.PERCENTAGE_NUMBER), content.getF5h6().doubleValue() / 100);
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.PERCENTAGE_NUMBER), content.getF5h7().doubleValue() / 100);
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.PERCENTAGE_NUMBER), content.getF5h8().doubleValue() / 100);
        }

        // Styles
        applyTitleStyle(sheet, csm.get(ExcelUtil.SupportedCellStyle.TITLE));
        applyWidth(sheet, contents.size());
        applySheetMergeSection(sheet);
        applySheetTemplate(sheet, contents.size());
    }

    private void applyTitleStyle(Sheet sheet, CellStyle cellStyle) {
        int fromRow = 0;
        int toRow = 14;
        int fromCol = 0;
        int toCol = 1;
        for (int i = fromRow; i <= toRow; i++) {
            for (int j = fromCol; j <= toCol; j++) {
                try {
                    ExcelUtil.applyStyle(sheet, i, j, cellStyle);
                } catch(Exception e) {
                    // ignore exception
                }
            }
        }
    }

    private void applyWidth(Sheet sheet, int numberOfDoctor) {
        sheet.setColumnWidth(0, ExcelUtil.columnWidth(5, 12));
        sheet.setColumnWidth(1, ExcelUtil.columnWidth(15, 12));
        for (int i = 0; i < numberOfDoctor; i++) {
            sheet.setColumnWidth(i + 2, ExcelUtil.columnWidth(10, 12));
        }
    }

    private void applySheetMergeSection(
        Sheet sheet
    ) {
        sheet.addMergedRegion(new CellRangeAddress(1, 4, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(6, 14, 0, 0));
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
            new CellRangeAddress(4, 4, 0, 1 + numberOfDoctor),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );
        pt.drawBorders(
            new CellRangeAddress(5, 5, 0, 1 + numberOfDoctor),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );
        pt.drawBorders(
            new CellRangeAddress(14, 14, 0, 1 + numberOfDoctor),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );

        pt.applyBorders(sheet);
    }
}
