package io.dentall.totoro.thumbnails;

import com.google.cloud.storage.Blob;
import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.business.service.ThumbnailsService;
import io.dentall.totoro.domain.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static io.dentall.totoro.thumbnails.ThumbnailsHelper.*;
import static java.lang.String.valueOf;
import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.*;
import static org.apache.commons.io.IOUtils.toByteArray;

public class PatientDocumentThumbnailsGenerator {

    private final static Logger log = LoggerFactory.getLogger(PatientDocumentThumbnailsGenerator.class);

    private final long patientId;

    private final ThumbnailsService thumbnailsService;

    private final Optional<ImageGcsBusinessService> imageGcsBusinessServiceOptional;

    private final List<Integer> defaultThumbnailsWidth = Arrays.asList(350, 50);

    private List<Blob> thumbnailsExist;

    public PatientDocumentThumbnailsGenerator(long patientId, ThumbnailsService thumbnailsService, Optional<ImageGcsBusinessService> imageGcsBusinessServiceOptional) {
        this.patientId = patientId;
        this.thumbnailsService = thumbnailsService;
        this.imageGcsBusinessServiceOptional = imageGcsBusinessServiceOptional;
    }

    /**
     * 產生 350*350、50*50 的縮圖
     */
    public List<Thumbnails> generateDefaultThumbnails(Document document, MultipartFile file) throws IOException {
        if (isNull(document) || !isSupported(document.getFileExtension())) {
            return emptyList();
        }

        final byte[] bytes = toByteArray(file.getInputStream());
        List<ThumbnailsParam> thumbnailsParamList = defaultThumbnailsWidth.stream().map(width -> new ThumbnailsParam(width, width)).collect(toList());
        return thumbnailsService.createThumbnailsList(
            valueOf(patientId),
            valueOf(document.getId()),
            bytes,
            file.getContentType(),
            thumbnailsParamList);
    }

    /**
     * 針對有支援縮圖格式的圖片，檢查是否已經有產生符合條件的縮圖，沒有的話就直接產生縮圖
     */
    public List<Thumbnails> generateThumbnails(Document document, List<ThumbnailsParam> thumbnailsParams) {
        if (isNull(document) || !isSupported(document.getFileExtension()) || thumbnailsParams.size() == 0 || !imageGcsBusinessServiceOptional.isPresent()) {
            return emptyList();
        }

        // 找出符合條件的縮圖
        List<Blob> thumbnailsExist = findThumbnails(thumbnailsParams);
        // 拿檔名當做map的key  /bucket/clinic/patientId/thumbnails/documentId.350x350 -> documentId.350x350
        Map<String, Optional<Blob>> thumbnailsExistNamesMap =
            thumbnailsExist.stream().collect(groupingBy(blob -> blob.getName().substring(blob.getName().lastIndexOf("/") + 1), maxBy(comparing(Blob::getGeneration))));

        List<Thumbnails> thumbnailsList = new ArrayList<>();
        ImageGcsBusinessService service = imageGcsBusinessServiceOptional.get();
        String fileName = document.getFilePath() + document.getFileName();
        Optional<Blob> blobOriginOptional = Optional.empty();

        for (ThumbnailsParam param : thumbnailsParams) {
            String thumbnailsName = thumbnailsName(valueOf(document.getId()), param);
            Optional<Thumbnails> thumbnailsOptional = Optional.empty();

            if (thumbnailsExistNamesMap.containsKey(thumbnailsName)) {
                Optional<Blob> blobThumbnailsOptional = thumbnailsExistNamesMap.get(thumbnailsName);
                if (blobThumbnailsOptional.isPresent()) {
                    Blob blob = blobThumbnailsOptional.get();
                    Thumbnails thumbnails = new Thumbnails();
                    thumbnails.setWidth(param.getWidth());
                    thumbnails.setHeight(param.getHeight());
                    thumbnails.setUrl(getUrl(blob));
                    thumbnailsOptional = Optional.of(thumbnails);
                }
            } else {
                try {
                    // 取得原圖的GCS資訊
                    blobOriginOptional = blobOriginOptional.isPresent() ? blobOriginOptional : Optional.ofNullable(service.getFile(fileName));
                    if (!blobOriginOptional.isPresent()) {
                        log.warn("Unable to create thumbnails, because " + fileName + " not found. patientId=" + patientId);
                        break;
                    } else {
                        Blob blob = blobOriginOptional.get();
                        byte[] blobBytes =  blob.getContent();
                        thumbnailsOptional = thumbnailsService.createThumbnails(valueOf(patientId), valueOf(document.getId()), blobBytes, blob.getContentType(), param);
                    }
                } catch (IOException e) {
                    log.error("create thumbnails fails. patientId=" + patientId, e);
                    break;
                }
            }

            thumbnailsOptional.ifPresent(thumbnailsList::add);
        }

        return thumbnailsList;
    }

    private List<Blob> findThumbnails(List<ThumbnailsParam> thumbnailsParams) {
        return thumbnailsExist == null ? thumbnailsExist = thumbnailsService.listThumbnails(valueOf(patientId), thumbnailsParams) : thumbnailsExist;
    }

}
