package io.dentall.totoro.business.service.nhi.metric.report;

import io.dentall.totoro.business.service.nhi.metric.dto.EastDistrictDto;
import io.dentall.totoro.business.service.nhi.metric.util.ExcelUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class EastDistrictReport {
    private static final String SHEET_NAME = "東區-牙醫門診總額抽審原則";

    public void generateReport(
        Workbook wb,
        Map<ExcelUtil.SupportedCellStyle, CellStyle> csm,
        List<EastDistrictDto> contents
    ) throws Exception {
        if (wb == null) {
            throw new Exception("Must new a workbook first before create sheet");
        }

        Sheet sheet = wb.createSheet(SHEET_NAME);
        Row row = null;
        int rowCounter  = 0;
        int endOfApplyPointSection = 1 + contents.size() + 1;
        int endOfMedicalServiceSection = endOfApplyPointSection + 6;

        // Header
        row = sheet.createRow(rowCounter);
        row.createCell(0).setCellValue("※採計分制，基層計分標記總和≧3 分進行抽審");
        rowCounter++;

        // 申報點數-標題
        sheet.createRow(rowCounter);
        sheet.addMergedRegion(new CellRangeAddress(1, endOfApplyPointSection, 0, 0));
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            0,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_TITLE
            ),
            "申報點數"
        );
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            1,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_TITLE
            ),
            "醫師姓名"
        );
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            2,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_TITLE
            ),
            "師申報點數"
        );
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            3,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_TITLE
            ),
            "標記分數"
        );
        rowCounter++;

        // 申報點數-醫師資料
        for (int i = 0; i < contents.size(); i++) {
            sheet.createRow(rowCounter);
            double g6 = contents.get(i).getG6().divideToIntegralValue(new BigDecimal(10000)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            ExcelUtil.createCellAndApplyStyle(
                sheet,
                rowCounter,
                1,
                csm.get(
                    ExcelUtil.SupportedCellStyle.AROUND_BORDER_TITLE
                ),
                contents.get(i).getDoctor().getDoctorName()
            );
            ExcelUtil.createCellAndApplyStyle(
                sheet,
                rowCounter,
                2,
                csm.get(
                    ExcelUtil.SupportedCellStyle.AROUND_BORDER_TITLE
                ),
                String.valueOf(g6).concat("萬")
            );
            int point = 0;
            if (g6 >= 600000) {
                point = 3;
            } else if (g6 >= 550000) {
                point = 2;
            } else if (g6 >= 450000) {
                point = 1;
            }

            ExcelUtil.createCellAndApplyStyle(
                sheet,
                rowCounter,
                3,
                csm.get(
                    ExcelUtil.SupportedCellStyle.AROUND_BORDER_CONTENT_REAL_NUMBER
                ),
                point
            );
            rowCounter++;
        }

        // 申報點數-備註
        sheet.createRow(rowCounter);
        sheet.addMergedRegion(new CellRangeAddress(endOfApplyPointSection + 1, endOfMedicalServiceSection, 0, 0));
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            0,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_TITLE
            ),
            "註：\n" +
                "申報點數≧45萬點，標記1分\n" +
                "申報點數≧55萬點，標記2分\n" +
                "申報點數≧60萬點，標記3分\n" +
                "以標記分數最高之醫師計(院所有 3 位醫師分別為 1、2、3 分，該院所則以 3 分計)"
        );
        rowCounter++;

        // 專業醫療服務品質項目-標題
        sheet.createRow(rowCounter);
        sheet.addMergedRegion(new CellRangeAddress(endOfApplyPointSection + 1, endOfMedicalServiceSection, 0, 0));
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            0,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_TITLE
            ),
            "專業醫療服務品質項目"
        );
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            1,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_TITLE
            ),
            "指標名稱"
        );
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            2,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_TITLE
            ),
            "全院所"
        );
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            3,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_TITLE
            ),
            "標記分數"
        );
        rowCounter++;

        // 專業醫療服務品質項目-資料
        EastDistrictDto content1 = contents.get(0);

        sheet.createRow(rowCounter);
        Double data1 = content1.getG8h1().doubleValue() / 100;
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            1,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_TITLE
            ),
            "OD 一年重補率＞3.13%"
        );
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            2,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_CONTENT_PERCENTAGE_NUMBER
            ),
            data1
        );
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            3,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_CONTENT_REAL_NUMBER
            ),
            data1 > 3.13
                ? 1
                : 0
        );
        rowCounter++;

        sheet.createRow(rowCounter);
        Double data2 = content1.getG8h2().doubleValue() / 100;
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            1,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_TITLE
            ),
            "OD 兩年重補率率＞5.80%"
        );
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            2,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_CONTENT_PERCENTAGE_NUMBER
            ),
            data2
        );
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            3,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_CONTENT_REAL_NUMBER
            ),
            data2 > 5.80
                ? 1
                : 0
        );
        rowCounter++;

        sheet.createRow(rowCounter);
        Double data3 = content1.getG8h3().doubleValue() / 100;
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            1,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_TITLE
            ),
            "OD申報點數佔率＞64.38%"
        );
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            2,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_CONTENT_PERCENTAGE_NUMBER
            ),
            data3
        );
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            3,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_CONTENT_REAL_NUMBER
            ),
            data3 > 64.38
                ? 1
                : 0
        );
        rowCounter++;

        sheet.createRow(rowCounter);
        Double data4 = content1.getG8h4().doubleValue() / 100;
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            1,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_TITLE
            ),
           "根管治療未完成率＞13.78%"
        );
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            2,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_CONTENT_PERCENTAGE_NUMBER
            ),
            data4
        );
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            3,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_CONTENT_REAL_NUMBER
            ),
            data4 > 13.78
                ? 1
                : 0
        );
        rowCounter++;

        // 專業醫療服務品質項目-備註
        sheet.createRow(rowCounter);
        sheet.addMergedRegion(new CellRangeAddress(endOfApplyPointSection, endOfApplyPointSection, 1, 3));
        ExcelUtil.createCellAndApplyStyle(
            sheet,
            rowCounter,
            0,
            csm.get(
                ExcelUtil.SupportedCellStyle.AROUND_BORDER_TITLE
            ),
            "註：\n大於每項品質指標值者各標記 1 分"
        );
        rowCounter++;

        // Styles
        applySheetMergeSection(sheet, contents.size());
    }

    private void applySheetMergeSection(
        Sheet sheet,
        int numberOfContent
    ) {
    }
}
