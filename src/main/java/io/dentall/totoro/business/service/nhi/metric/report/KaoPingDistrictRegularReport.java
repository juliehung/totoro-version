package io.dentall.totoro.business.service.nhi.metric.report;

import io.dentall.totoro.business.service.nhi.metric.dto.KaoPingDistrictRegularDto;
import io.dentall.totoro.business.service.nhi.metric.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KaoPingDistrictRegularReport {
    private static final String SHEET_NAME = "高屏區-牙醫門診總額抽審原則";

    public void generateReport(
        Workbook wb,
        Map<ExcelUtil.SupportedCellStyle, CellStyle> csm,
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
        for (int contentIdx = 0; contentIdx < contents.size(); contentIdx++) {
            KaoPingDistrictRegularDto content = contents.get(contentIdx);
            int rowIdx = 0;
            int colIdx = contentIdx + 2;
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.USER), content.getDoctor().getDoctorName());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getK1().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getK2().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getK11().doubleValue());

            if (content.getJ2h5().doubleValue() >= 30) {
                ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.RED_PERCENTAGE_NUMBER), content.getJ2h5().doubleValue());
            } else {
                ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.PERCENTAGE_NUMBER), content.getJ2h5().doubleValue());
            }

            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.PERCENTAGE_NUMBER), content.getK10().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.PERCENTAGE_NUMBER), content.getK3().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.PERCENTAGE_NUMBER), content.getK4().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getK5().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.PERCENTAGE_NUMBER), content.getK6().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.PERCENTAGE_NUMBER), content.getK7().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.PERCENTAGE_NUMBER), content.getK12().doubleValue());
            ExcelUtil.createCellAndApplyStyle(sheet, rowIdx++, colIdx, csm.get(ExcelUtil.SupportedCellStyle.REAL_NUMBER), content.getK14().doubleValue());
        }

        // Styles
        applyTitleStyle(sheet);
        applySheetMergeSection(sheet);
        applySheetTemplate(sheet, contents.size());
    }

    private void applyTitleStyle(Sheet sheet) {
        int fromRow = 0;
        int toRow = 12;
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
