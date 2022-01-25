package io.dentall.totoro.service.util;

import io.dentall.totoro.config.TimeConfig;
import org.apache.commons.io.FilenameUtils;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class FileNameUtil {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").withZone(TimeConfig.ZONE_OFF_SET);

    private FileNameUtil() {
    }

    public static String normalizeFileName(String extension) {
        String name = dateTimeFormatter.format(Instant.now()).concat("-").concat(randomUUID().toString().replace("-", ""));
        if (isNotBlank(extension)) {
            name = name.concat(".").concat(extension);
        }
        return name;
    }

    public static String getExtension(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }
}
