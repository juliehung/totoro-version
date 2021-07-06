package io.dentall.totoro.service.util;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CsvUtil {

    public static String convertDataToCsvRecord(List<String> data) {
        return String.join(",", data);
    }

    public static InputStream convertCsvStringToInputStream(List<String> csvString) throws Exception {
        Reader reader = new StringReader(
            String.join(
                "\n",
                csvString
            )
        );

        InputStream is =
            IOUtils.toInputStream(
                IOUtils.toString(reader),
                StandardCharsets.UTF_8
            );

        reader.close();
        is.close();

        return is;
    }
}
