package io.dentall.totoro.business.service;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import io.dentall.totoro.thumbnails.Thumbnails;
import io.dentall.totoro.thumbnails.ThumbnailsHelper;
import io.dentall.totoro.thumbnails.ThumbnailsParam;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static io.dentall.totoro.thumbnails.ThumbnailsHelper.getUrl;
import static io.dentall.totoro.thumbnails.ThumbnailsHelper.thumbnailsName;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
public class ThumbnailsService {

    private final Optional<ImageGcsBusinessService> imageGcsBusinessServiceOptional;

    private final String CLINIC_NAME;

    private final String THUMBNAILS_NAME = "thumbnails";

    public ThumbnailsService(Optional<ImageGcsBusinessService> imageGcsBusinessServiceOptional) {
        this.imageGcsBusinessServiceOptional = imageGcsBusinessServiceOptional;
        this.CLINIC_NAME = imageGcsBusinessServiceOptional.isPresent() ? imageGcsBusinessServiceOptional.get().getClinicName() : "";
    }

    public List<Thumbnails> createThumbnailsList(String path, String name, byte[] content, String contentType, List<ThumbnailsParam> params) {
        return params.stream().map(param -> {
                try {
                    byte[] thumbnailsBytes = ThumbnailsHelper.doThumbnails(content, param.getWidth(), param.getHeight());
                    return createThumbnails(path, name, thumbnailsBytes, contentType, param);
                } catch (Exception e) {
                    return Optional.<Thumbnails>empty();
                }
            })
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    public Optional<Thumbnails> createThumbnails(String path, String name, String url, String contentType, ThumbnailsParam param) throws IOException {
        byte[] thumbnailsBytes = ThumbnailsHelper.doThumbnails(url, param.getWidth(), param.getHeight());
        return uploadThumbnails(path, name, thumbnailsBytes, contentType, param);
    }

    public Optional<Thumbnails> createThumbnails(String path, String name, byte[] content, String contentType, ThumbnailsParam param) throws IOException {
        byte[] thumbnailsBytes = ThumbnailsHelper.doThumbnails(content, param.getWidth(), param.getHeight());
        return uploadThumbnails(path, name, thumbnailsBytes, contentType, param);
    }

    public Optional<Thumbnails> uploadThumbnails(String path, String name, byte[] content, String contentType, ThumbnailsParam param) throws IOException {
        String fullThumbnailName = fullThumbnailsName(path, thumbnailsName(name, param));
        if (imageGcsBusinessServiceOptional.isPresent()) {
            Blob blob = imageGcsBusinessServiceOptional.get().uploadFile("", fullThumbnailName, content, contentType);
            Thumbnails thumbnails = new Thumbnails();
            thumbnails.setWidth(param.getWidth());
            thumbnails.setHeight(param.getHeight());
            thumbnails.setUrl(getUrl(blob));
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
        if (!imageGcsBusinessServiceOptional.isPresent()) {
            return;
        }
        listThumbnails(path, name).forEach(Blob::delete);
    }

}
