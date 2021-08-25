package io.dentall.totoro.business.service.nhi.metric.util;

import io.dentall.totoro.business.service.nhi.metric.report.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    public enum SupportedFont {
        RED
        ;
    }

    public static void main(String[] args) throws Exception {
        FileOutputStream fos = new FileOutputStream(
            new File(
                "test.xls"
            )
        );
        Workbook wb = new HSSFWorkbook();

        Map<String, List<String>> rowContents = new HashMap<>();
        TaipeiAreaReport rp1 = new TaipeiAreaReport();
        NorthAreaReport rp2 = new NorthAreaReport();
        SouthAreaReport rp3 = new SouthAreaReport();
        CenterAreaReport rp4 = new CenterAreaReport();
        KaoPinAreaReport1 rp5 = new KaoPinAreaReport1();
        KaoPinAreaReport2 rp6 = new KaoPinAreaReport2();
        rp1.generateReport(
            wb,
            rowContents
        );
        rp2.generateReport(
            wb,
            rowContents
        );
        rp3.generateReport(
            wb,
            rowContents
        );
        rp4.generateReport(
            wb,
            rowContents
        );
        rp5.generateReport(
            wb,
            rowContents
        );
        rp6.generateReport(
            wb,
            rowContents
        );
        wb.write(fos);
        fos.close();
        wb.close();
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
