package io.dentall.totoro.business.service;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import io.dentall.totoro.thumbnails.Thumbnails;
import io.dentall.totoro.thumbnails.ThumbnailsHelper;
import io.dentall.totoro.thumbnails.ThumbnailsParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static io.dentall.totoro.thumbnails.ThumbnailsHelper.getUrl;
import static io.dentall.totoro.thumbnails.ThumbnailsHelper.thumbnailsName;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static net.logstash.logback.encoder.org.apache.commons.lang.StringUtils.isEmpty;

@Service
public class ThumbnailsService {

    private final Optional<ImageGcsBusinessService> imageGcsBusinessServiceOptional;

    private final String CLINIC_NAME;

    @Value("${gcp.bucket-name}")
    private String bucketName;

    private final String THUMBNAILS_NAME = "thumbnails";

    public ThumbnailsService(Optional<ImageGcsBusinessService> imageGcsBusinessServiceOptional) {
        this.imageGcsBusinessServiceOptional = imageGcsBusinessServiceOptional;
        this.CLINIC_NAME = imageGcsBusinessServiceOptional.isPresent() ? imageGcsBusinessServiceOptional.get().getClinicName() : "ClinicNameNotPresent";
    }

    public List<Thumbnails> createThumbnailsList(String path, String name, String format, byte[] content, String contentType, List<ThumbnailsParam> params) {
        if (isEmpty(path) || isEmpty(name) || isNull(content) || content.length == 0) {
            return emptyList();
        }
        return params.stream().map(param -> {
                try {
                    return createThumbnails(path, name, format, content, contentType, param);
                } catch (Exception e) {
                    return Optional.<Thumbnails>empty();
                }
            })
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    public Optional<Thumbnails> createThumbnails(String path, String name, String format, byte[] content, String contentType, ThumbnailsParam param) throws IOException {
        if (isEmpty(path) || isEmpty(name) || isNull(content) || content.length == 0) {
            return Optional.empty();
        }

        byte[] thumbnailsBytes;

        if (content.length > 9000000) {
            // 因檔案大概超10M時，java在讀入資料轉成BuffedImage會產生「 Invalid scanline stride」Exception
            // 所以保險點，超過9M的檔案，先用Sampling的方式讀檔，犧牲一些解析度，可換取成功產生縮圖
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(format);
            ImageReader reader = it.next();
            reader.setInput(new MemoryCacheImageInputStream(new ByteArrayInputStream(content)));
            ImageReadParam imageReadParam = reader.getDefaultReadParam();
            imageReadParam.setSourceSubsampling(4, 4, 0, 0);
            BufferedImage bufferedImage = reader.read(0, imageReadParam);
            thumbnailsBytes = ThumbnailsHelper.doThumbnails(bufferedImage, param.getWidth(), param.getHeight(), format);
        } else {
            thumbnailsBytes = ThumbnailsHelper.doThumbnails(content, param.getWidth(), param.getHeight());
        }

        return uploadThumbnails(path, name, thumbnailsBytes, contentType, param);
    }

    public Optional<Thumbnails> uploadThumbnails(String path, String name, byte[] content, String contentType, ThumbnailsParam param) throws IOException {
        if (isEmpty(path) || isEmpty(name) || isNull(content) || content.length == 0) {
            return Optional.empty();
        }
        String fullThumbnailName = fullThumbnailsName(path, thumbnailsName(name, param));
        if (imageGcsBusinessServiceOptional.isPresent()) {
            Blob blob = imageGcsBusinessServiceOptional.get().uploadFile(fullThumbnailName, content, contentType);
            Thumbnails thumbnails = new Thumbnails();
            thumbnails.setWidth(param.getWidth());
            thumbnails.setHeight(param.getHeight());
            thumbnails.setUrl(getUrl(bucketName, blob));
            return Optional.of(thumbnails);
        } else {
            return Optional.empty();
        }
    }

    public String fullThumbnailsName(String path, String name) {
        return String.format("%s/%s/%s/%s", CLINIC_NAME, path, THUMBNAILS_NAME, name);
    }

    public List<Blob> listThumbnails(String path) {
        if (!imageGcsBusinessServiceOptional.isPresent()) {
            return emptyList();
        }
        Page<Blob> blobs = imageGcsBusinessServiceOptional.get().listFileByPrefix(String.format("%s/%s/%s", CLINIC_NAME, path, THUMBNAILS_NAME));
        List<Blob> list = new ArrayList<>();
        for (Blob blob : blobs.iterateAll()) {
            list.add(blob);
        }
        return list;
    }

    public List<Blob> listThumbnails(String path, String name) {
        if (!imageGcsBusinessServiceOptional.isPresent()) {
            return emptyList();
        }
        Page<Blob> blobs = imageGcsBusinessServiceOptional.get().listFileByPrefix(String.format("%s/%s/%s/%s", CLINIC_NAME, path, THUMBNAILS_NAME, name));
        List<Blob> list = new ArrayList<>();
        for (Blob blob : blobs.iterateAll()) {
            list.add(blob);
        }
        return list;
    }

    public List<Blob> listThumbnails(String path, List<ThumbnailsParam> params) {
        Set<String> appendixes = params.stream().map(ThumbnailsParam::toAppendix).filter(Objects::nonNull).collect(Collectors.toSet());
        List<Blob> list = listThumbnails(path);
        return list.stream().filter(blob -> appendixes.stream().anyMatch(blob.getName()::endsWith)).collect(toList());
    }

    public void deleteThumbnails(String path, String name) {
        if (isEmpty(path) || isEmpty(name)) {
            return;
        }
        if (!imageGcsBusinessServiceOptional.isPresent()) {
            return;
        }
        listThumbnails(path, name).forEach(Blob::delete);
    }

}
