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
        return Arrays.stream(paramListStr.split("(?<=\\));(?=\\()"))
            .map(ThumbnailsHelper::parseParamStr)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(toList());
    }

    public static Optional<ThumbnailsParam> parseParamStr(String paramStr) {
        if (isBlank(paramStr)) {
            return Optional.empty();
        }

        if (!paramStr.startsWith("(") || !paramStr.endsWith(")")) {
            return Optional.empty();
        }

        try {
            // (width:300;height:3000)
            // remove "(" and ")"
            paramStr = paramStr.substring(1, paramStr.length() - 1);
            String[] params = paramStr.split(";");
            ThumbnailsParam thumbnailsParam = new ThumbnailsParam();

            for (String param : params) {
                String[] keyAndValue = param.split(":");
                String key = keyAndValue[0];
                String value = keyAndValue[1];

                Field field = ThumbnailsParam.class.getDeclaredField(key);

                if (Integer.class.isAssignableFrom(field.getType()) || int.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    field.set(thumbnailsParam, Integer.parseInt(value));
                } else {
                    throw new UnsupportedOperationException();
                }
            }

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
