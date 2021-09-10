package io.dentall.totoro.business.service.nhi.metric.report;

import io.dentall.totoro.business.service.nhi.metric.dto.MiddleDistrictDto;
import io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType;
import io.dentall.totoro.business.service.nhi.metric.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MiddleDistrictReport {
    private static final String SHEET_NAME = "中區-輔導管控辦法";

    public void generateReport(
        Workbook wb,
        Map<ExcelUtil.SupportedCellStyle, CellStyle> csm,
        List<MiddleDistrictDto> contents
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
        row.createCell(1).setCellValue("病人耗用點數");
        // 根管相關
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("根管相關");
        row.createCell(1).setCellValue("(3個月)ENDO未完成率");
        // 補牙相關
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("補牙相關");
        row.createCell(1).setCellValue("OD點數比率");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("兩年垂直重補率");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("就醫患者平均OD填補顆數");
        // 備註
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("※其餘牽涉他願資料無法計算之指標，請參照健保署最新公告");
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("※指標數值係依系統累積資料量進行統計。");
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("※指標項目限制數值，如係針對個別醫師限制者，均已特別標註，其餘皆係指全院所的限制數值");

        // Assign data
        for (int contentIdx = 0; contentIdx < contents.size(); contentIdx++) {
            MiddleDistrictDto content = contents.get(contentIdx);
            int rowIdx = 0;
            int colIdx = contentIdx + 2;
            if (content.getType().equals(MetricSubjectType.doctor)) {
                ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.USER), content.getDoctor().getDoctorName());
            } else {
                ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.USER), "全院所");
            }

            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getH1().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getH2().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.PERCENTAGE_NUMBER), content.getH4().doubleValue() / 100);
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.PERCENTAGE_NUMBER), content.getH3().doubleValue() / 100);
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.PERCENTAGE_NUMBER), content.getH5().doubleValue() / 100);
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getH7().doubleValue());
        }

        // Styles
        applyTitleStyle(sheet, csm.get(ExcelUtil.SupportedCellStyle.TITLE));
        applyWidth(sheet, contents.size());
        applySheetMergeSection(sheet);
        applySheetTemplate(sheet, contents.size());
    }

    private void applyTitleStyle(Sheet sheet, CellStyle cellStyle) {
        int fromRow = 0;
        int toRow = 6;
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
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 0, 0));
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
            new CellRangeAddress(6, 6, 0, 1 + numberOfDoctor),
            BorderStyle.THICK,
            BorderExtent.BOTTOM
        );

        pt.applyBorders(sheet);
    }
}
