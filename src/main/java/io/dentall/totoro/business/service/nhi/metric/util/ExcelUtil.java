package io.dentall.totoro.business.service.nhi.metric.util;

import org.apache.poi.ss.usermodel.*;

import java.util.HashMap;
import java.util.Map;

public class ExcelUtil {

    public enum SupportedFont {
        RED
        ;
    }

    private static Map<SupportedFont, Font> fonts = new HashMap<>();

    public static int columnWidth(int numberOfCharacterOfCell, int sizeOfCharacter) {
        return (int) Math.rint((numberOfCharacterOfCell * sizeOfCharacter + 5) / 7 * 256);
    }

    private static void initFonts(Workbook wb) {
        Font redFont = wb.createFont();
        redFont.setColor(Font.COLOR_RED);
    }

    public static Font getFont(Workbook wb, SupportedFont sf) {
        if (!fonts.containsKey(sf)) {
           initFonts(wb);
        }

        return  fonts.get(sf);
    }

    public static void applyTitleCellStyle(Cell cell) {
        CellStyle cs = cell.getCellStyle();
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        cs.setAlignment(HorizontalAlignment.LEFT);
        cs.setWrapText(true);
    }

    public static void applyUserCellStyle(Cell cell) {
        CellStyle cs = cell.getCellStyle();
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        cs.setAlignment(HorizontalAlignment.RIGHT);
    }

    public static void applyRealNumberCellStyle(Cell cell) {
        CellStyle cs = cell.getCellStyle();
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        cs.setAlignment(HorizontalAlignment.RIGHT);
        cs.setDataFormat((short) 3);
    }

    public static void applyPercentageCellStyle(Cell cell) {
        CellStyle cs = cell.getCellStyle();
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        cs.setAlignment(HorizontalAlignment.RIGHT);
        cs.setDataFormat((short) 10);
    }

    public static void applyReadRealNumberCellStyle(Cell cell, Font font) {
        CellStyle cs = cell.getCellStyle();
        cs.setFont(font);
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        cs.setAlignment(HorizontalAlignment.RIGHT);
        cs.setDataFormat((short) 3);
    }

    public static void applyRedPercentageCellStyle(Cell cell, Font font) {
        CellStyle cs = cell.getCellStyle();
        cs.setFont(font);
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        cs.setAlignment(HorizontalAlignment.RIGHT);
        cs.setDataFormat((short) 10);
    }
}
