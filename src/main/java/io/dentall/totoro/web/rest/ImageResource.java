package io.dentall.totoro.web.rest;

import io.dentall.totoro.business.service.ImageBusinessService;
import io.dentall.totoro.business.service.ImageHostBusinessService;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.domain.Image;
import io.dentall.totoro.service.ImageQueryService;
import io.dentall.totoro.service.dto.ImageCriteria;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.web.rest.vm.ImageVM;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

/**
 * 2021.12.02 改使用 {@link PatientDocumentResource} 進行檔案操作
 */
@Deprecated
@Profile({"img-host", "img-gcs"})
@RestController
@RequestMapping("/api")
public class ImageResource {

    private final Logger logger = LoggerFactory.getLogger(ImageResource.class);

    private static final String ENTITY_NAME = "image";

    @Value("classpath:chrome.png")
    private Resource testImageFile;

    private Instant syncNow;

    private final ImageBusinessService imageBusinessService;

    private final ImageQueryService imageQueryService;

    public ImageResource(
        ImageBusinessService imageBusinessService,
        ImageQueryService imageQueryService
    ) {
        this.imageBusinessService = imageBusinessService;
        this.imageQueryService = imageQueryService;
    }

    /**
     * 2021.12.02 改使用 {@link PatientDocumentResource#createDocument(Long, Long, MultipartFile)} 進行檔案上傳
     *
     * @param patientId
     * @param file
     * @return
     */
    @Deprecated
    @PostMapping("/images/patients/{patientId}")
    public DeferredResult<ResponseEntity<Image>> uploadImage(
        @PathVariable("patientId") Long patientId,
        @RequestParam("image") MultipartFile file
    ) {
        // Validation
        if (file == null || file.isEmpty() || file.getSize() <= 0) {
            throw new BadRequestAlertException("Upload not include image file or it is empty", ENTITY_NAME, "noimagefile");
        }

        DeferredResult<ResponseEntity<Image>> result = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(() -> {
            // Business logic
            try(InputStream inputStream = file.getInputStream()) {
                logger.debug("patientId: {}", patientId);
                String remotePath = imageBusinessService.createImagePath(patientId);

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").withZone(TimeConfig.ZONE_OFF_SET);
                String remoteFileName = dateTimeFormatter.format(getSyncNow());
                logger.debug("remoteFileName: {}", remoteFileName);
                remoteFileName = file.getOriginalFilename() == null ? remoteFileName : remoteFileName.concat("_").concat(file.getOriginalFilename().replace(" ", "+"));

                // upload origin
                imageBusinessService.uploadFile(remotePath, remoteFileName, inputStream, file.getContentType());
                Image image = imageBusinessService.createImage(patientId, remotePath, remoteFileName);
                result.setResult(ResponseEntity.ok(image));
            } catch (IOException e) {
                logger.error("Upload image get exception:\n {}", e.getMessage());
                imageBusinessService.disconnect();
                result.setErrorResult(e);
            }
        });

        return result;
    }

    /**
     * 查詢圖片，並取得資料來源 url，以及其他訊息
     * @param host local 模式下，方可取得 server 的 ip 位址；gcs 模式下是否有值並不影響
     * @param imageCriteria query by image id, patient id, etc,.
     * @param pageable pagination using size and page.
     */
    @GetMapping("/images")
    public ResponseEntity<List<ImageVM>> getImagesByCriteria(
        @RequestHeader(name = "Host", required = false) String host,
        ImageCriteria imageCriteria,
        Pageable pageable
    ) {
        Page<Image> page = imageQueryService.findByCriteria(imageCriteria, pageable);
        List<ImageVM> content = page.map(image -> {
                ImageVM vm = new ImageVM();
                Map<String, String> urls = imageBusinessService.getImageThumbnailsBySize(host, image.getId(), "original");

                vm.setImage(image);
                vm.setUrl(urls.getOrDefault("original", ""));

                return vm;
            })
            .stream().collect(Collectors.toList());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/images");
        return ResponseEntity.ok().headers(headers).body(content);
    }

    @Deprecated
    @GetMapping("/images/{id}")
    public ResponseEntity<ImageVM> getImagesById(@PathVariable("id") Long id) {
        Image image = imageBusinessService.getImageById(id);
        Map<String, String> urls = imageBusinessService.getImageThumbnailsBySize(null, id, "original");

        ImageVM vm = new ImageVM();
        vm.setImage(image);
        vm.setUrl(urls.getOrDefault("original", ""));

        return ResponseEntity.ok(vm);
    }

    @Deprecated
    @GetMapping("/images/{id}/thumbnails")
    public ResponseEntity<Map<String, String>> getImageThumbnailsBySize(
        @RequestHeader(name = "Host", required = false) String host,
        @PathVariable("id") Long id,
        @RequestParam(value = "size", required = false) String size
    ) {
        return ResponseEntity.ok(imageBusinessService.getImageThumbnailsBySize(host, id, size));
    }

    @Deprecated
    @GetMapping("/images/sizes")
    public ResponseEntity<List<String>> getImageSizes() {
        return ResponseEntity.ok(imageBusinessService.getImageSizes());
    }

    @Deprecated
    @GetMapping("/images/test")
    public ResponseEntity<String> getTestImage() throws IOException {
        String remotePath = imageBusinessService.createImagePath(-1L);
        imageBusinessService.uploadFile(remotePath, "abc.png", testImageFile.getInputStream(), "image/png");

        return ResponseEntity.ok("test upload file to ftp");
    }

    @Deprecated
    @GetMapping("/images/thumbnail-url")
    public ResponseEntity<String> getImageThumbnailUrl(@RequestHeader(name = "Host", required = false) String host) {
        logger.info("Host of request header: {}", host);

        return ResponseEntity.ok(imageBusinessService.getImageThumbnailUrl(host));
    }

    /**
     * 當 local 模式下，用來傳送 image byte 用的 api，有風險可直接存取其他檔案
     * @param path file path + file name
     * @param size original or median
     */
    @Profile("img-host")
    @GetMapping("/images/host")
    public DeferredResult<ResponseEntity<byte[]>> getImageFromHost(
        @RequestParam("path") String path,
        @RequestParam(value = "size", required = false) String size
    ) {
        DeferredResult<ResponseEntity<byte[]>> result = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(() -> {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Disposition", "inline; filename=" + Paths.get(path).getFileName());

                String ext = FilenameUtils.getExtension(path).toLowerCase();
                if (ext.equals("jpeg") || ext.equals("jpg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (ext.equals("png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                }

                ImageHostBusinessService imageHostBusinessService = (ImageHostBusinessService) imageBusinessService;
                result.setResult(ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(imageHostBusinessService.getImageByPathAndSize(path, size))
                );
            } catch (IOException e) {
                String message = String.format("unable to get image from host path[%s] and size[%s]: error-mssage:[%s]", path, size, e.getMessage());
                logger.error(message);
                result.setErrorResult(new BadRequestAlertException(message, ENTITY_NAME, null));
            }
        });

        return result;
    }

    private synchronized Instant getSyncNow() {
        syncNow = Instant.now();

        return syncNow;
    }
}
