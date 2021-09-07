package io.dentall.totoro.business.service.nhi.metric.report;

import io.dentall.totoro.business.service.nhi.metric.dto.KaoPingDistrictReductionDto;
import io.dentall.totoro.business.service.nhi.metric.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KaoPingDistrictReductionReport {
    private static final String SHEET_NAME = "高屏區-減量抽審辦法";

    public void generateReport(
        Workbook wb,
        Map<ExcelUtil.SupportedCellStyle, CellStyle> csm,
        List<KaoPingDistrictReductionDto> contents
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
        row.createCell(1).setCellValue("醫療費用點數\n(個別醫師需<51 萬)");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("去年當季醫師平均醫療費用點數");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("當季平均醫療費用點數\n(需<36萬)");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("當季就醫病患平均耗用值");
        row = sheet.createRow(rowCounter++);
        row.createCell(1).setCellValue("當季每人平均就醫次數\n(需<2.0次/人)");
        // 根管相關
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("根管相關");
        row.createCell(1).setCellValue("當季根管治療未完成率(需<30%)");
        // 備註
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("※其餘牽涉他願資料無法計算之指標，請參照健保署最新公告");
        row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue("※指標數值係依系統累積資料量進行統計。");

        // Assign data
        for (int contentIdx = 0; contentIdx < contents.size(); contentIdx++) {
            KaoPingDistrictReductionDto content = contents.get(contentIdx);
            int rowIdx = 0;
            int colIdx = contentIdx + 2;
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.USER), content.getDoctor().getDoctorName());

            if (content.getJ1h1().doubleValue() >= 525000) {
                ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.RED_REAL_NUMBER), content.getJ1h1().doubleValue());
            } else {
                ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getJ1h1().doubleValue());
            }

            if (content.getJ1h2().doubleValue() >= 360000) {
                ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.RED_REAL_NUMBER), content.getJ1h2().doubleValue());
            } else {
                ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getJ1h2().doubleValue());
            }

            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getJ2h2().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getJ2h3().doubleValue());

            if (content.getJ2h4().doubleValue() >= 2) {
                ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.RED_REAL_NUMBER), content.getJ2h4().doubleValue());
            } else {
                ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getJ2h4().doubleValue());
            }

            if (content.getJ2h5().doubleValue() >= 30) {
                ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.RED_PERCENTAGE_NUMBER), content.getJ2h5().doubleValue() / 100);
            } else {
                ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.PERCENTAGE_NUMBER), content.getJ2h5().doubleValue() / 100);
            }
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

    private void applySheetMergeSection(Sheet sheet) {
        sheet.addMergedRegion(new CellRangeAddress(1, 5, 0, 0));
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
            new CellRangeAddress(5, 5, 0, 1 + numberOfDoctor),
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
