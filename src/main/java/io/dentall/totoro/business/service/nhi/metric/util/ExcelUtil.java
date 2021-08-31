package io.dentall.totoro.business.service.nhi.metric.util;

import org.apache.poi.ss.usermodel.*;

import java.util.HashMap;
import java.util.Map;

public class ExcelUtil {

    public enum SupportedFont {
        RED
        ;
    }

    public enum SupportedCellStyle {
        USER,
        TITLE,
        REAL_NUMBER,
        PERCENTAGE_NUMBER,
        RED_REAL_NUMBER,
        RED_PERCENTAGE_NUMBER,
        ;
    }

    private static Map<SupportedFont, Font> fonts = new HashMap<>();

    public static int columnWidth(int numberOfCharacterOfCell, int sizeOfCharacter) {
        return (int) Math.rint((numberOfCharacterOfCell * sizeOfCharacter + 5) / 7 * 256);
    }

    public static void applyStyle(
        Sheet sheet,
        int row,
        int col,
        CellStyle cellStyle
    ) {
        Cell cell = sheet.getRow(row).getCell(col);
        cell.setCellStyle(cellStyle);
    }

    public static void createCellAndApplyStyle(
        Sheet sheet,
        int row,
        int col,
        CellStyle cellStyle,
        String content
    ) {
        Cell cell = sheet.getRow(row).createCell(col);
        cell.setCellValue(content);
        cell.setCellStyle(cellStyle);
    }

    public static void createCellAndApplyStyle(
        Sheet sheet,
        int row,
        int col,
        CellStyle cellStyle,
        double content
    ) {
        Cell cell = sheet.getRow(row).createCell(col);
        cell.setCellValue(content);
        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
    }

    public static Map<SupportedCellStyle, CellStyle> createReportStyle(Workbook wb) {
        Map<SupportedCellStyle, CellStyle> m = new HashMap<>();

        // Font
        Font redFont = wb.createFont();
        redFont.setColor(Font.COLOR_RED);

        // Styles
        CellStyle titleCellStyle = wb.createCellStyle();
        titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleCellStyle.setAlignment(HorizontalAlignment.LEFT);
        titleCellStyle.setWrapText(true);

        CellStyle userCellStyle = wb.createCellStyle();
        userCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        userCellStyle.setAlignment(HorizontalAlignment.RIGHT);

        CellStyle realNumberCellStyle = wb.createCellStyle();
        realNumberCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        realNumberCellStyle.setAlignment(HorizontalAlignment.RIGHT);
        realNumberCellStyle.setDataFormat((short) 4);

        CellStyle percentageCellStyle = wb.createCellStyle();
        percentageCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        percentageCellStyle.setAlignment(HorizontalAlignment.RIGHT);
        percentageCellStyle.setDataFormat((short) 10);

        CellStyle redRealNumberCellStyle = wb.createCellStyle();
        redRealNumberCellStyle.setFont(redFont);
        redRealNumberCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        redRealNumberCellStyle.setAlignment(HorizontalAlignment.RIGHT);
        redRealNumberCellStyle.setDataFormat((short) 4);

        CellStyle redPercentageCellStyle = wb.createCellStyle();
        redPercentageCellStyle.setFont(redFont);
        redPercentageCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        redPercentageCellStyle.setAlignment(HorizontalAlignment.RIGHT);
        redPercentageCellStyle.setDataFormat((short) 10);

        // assign style to map
        m.put(SupportedCellStyle.USER, userCellStyle);
        m.put(SupportedCellStyle.TITLE, titleCellStyle);
        m.put(SupportedCellStyle.REAL_NUMBER, realNumberCellStyle);
        m.put(SupportedCellStyle.PERCENTAGE_NUMBER, percentageCellStyle);
        m.put(SupportedCellStyle.RED_REAL_NUMBER, redRealNumberCellStyle);
        m.put(SupportedCellStyle.RED_PERCENTAGE_NUMBER, redPercentageCellStyle);

        return m;
    }
}
