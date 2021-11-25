package io.dentall.totoro.service.util;

import io.dentall.totoro.config.TimeConfig;
import org.apache.commons.io.FilenameUtils;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.isNull;

public class FileNameUtil {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").withZone(TimeConfig.ZONE_OFF_SET);

    private FileNameUtil() {
    }

    public static String normalizeFileName(String fileName) {
        String remoteFileName = dateTimeFormatter.format(Instant.now());
        return isNull(fileName) ? remoteFileName : remoteFileName.concat("_").concat(fileName.replace(" ", "+"));
    }

    public static String getExtension(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }
}
