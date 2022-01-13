package io.dentall.totoro.thumbnails;

import com.google.api.client.util.Strings;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;

public final class ThumbnailsHelper {

    private final static Set<String> ThumbnailSupportedFormat = Arrays.stream(new String[]{"jpg", "jpeg", "png", "gif", "bmp", "wbmp"}).collect(Collectors.toSet());

    private ThumbnailsHelper() {
    }

    public static boolean isSupported(String extension) {
        if (Strings.isNullOrEmpty(extension)) {
            return false;
        }

        return ThumbnailSupportedFormat.stream().anyMatch(format -> extension.toLowerCase().equals(format));
    }

    public static String thumbnailsName(String name, ThumbnailsParam param) {
        return String.format("%s.%s", normailizeName(name), param.toAppendix());
    }

    public static String normailizeName(String name) {
        return name.replace("/", "-").replace("\\", "-");
    }

    public static List<ThumbnailsParam> parseParamListStr(String paramListStr) {
        // example: 350x350,50x50
        return Arrays.stream(paramListStr.split(","))
            .map(ThumbnailsHelper::parseParamStr)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(toList());
    }

    public static Optional<ThumbnailsParam> parseParamStr(String paramStr) {
        if (isBlank(paramStr)) {
            return Optional.empty();
        }

        try {
            // example 350x350 (第一個數字為寬度、第二個數字為長度)
            String[] params = paramStr.split("x");
            ThumbnailsParam thumbnailsParam = new ThumbnailsParam();

            Field fieldWidth = ThumbnailsParam.class.getDeclaredField("width");
            Field fieldHeight = ThumbnailsParam.class.getDeclaredField("height");

            fieldWidth.setAccessible(true);
            fieldWidth.set(thumbnailsParam, Integer.parseInt(params[0]));
            fieldHeight.setAccessible(true);
            fieldHeight.set(thumbnailsParam, Integer.parseInt(params[1]));

            return Optional.of(thumbnailsParam);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static byte[] doThumbnails(String url, int width, int height) throws IOException {
        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Thumbnails.of(new URL(url)).size(width, height).toOutputStream(output);
            return output.toByteArray();
        }
    }

    public static byte[] doThumbnails(byte[] bytes, int width, int height) throws IOException {
        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Thumbnails.of(new ByteArrayInputStream(bytes)).size(width, height).toOutputStream(output);
            return output.toByteArray();
        }
    }

    public static String getUrl(Blob blob) {
        return blob.signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withVirtualHostedStyle()).toString();
    }
}
