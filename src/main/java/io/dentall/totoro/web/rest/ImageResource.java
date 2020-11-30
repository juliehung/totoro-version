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

    @GetMapping("/images")
    public ResponseEntity<List<ImageVM>> getImagesByCriteria(ImageCriteria imageCriteria, Pageable pageable) {
        Page<Image> page = imageQueryService.findByCriteria(imageCriteria, pageable);
        List<ImageVM> content = page.map(image -> {
            ImageVM vm = new ImageVM();

            vm.setImage(image);
            Map<String, String> urls = imageBusinessService.getImageThumbnailsBySize(null, image.getId(), "original");
            if (urls.containsKey("original")) {
                vm.setUrl(urls.get("original"));
            }

            return vm;
        })
            .stream().collect(Collectors.toList());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/images");
        return ResponseEntity.ok().headers(headers).body(content);
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<Image> getImagesById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(imageBusinessService.getImageById(id));
    }

    @GetMapping("/images/{id}/thumbnails")
    public ResponseEntity<Map<String, String>> getImageThumbnailsBySize(
        @RequestHeader(name = "Host", required = false) String host,
        @PathVariable("id") Long id,
        @RequestParam(value = "size", required = false) String size
    ) {
        return ResponseEntity.ok(imageBusinessService.getImageThumbnailsBySize(host, id, size));
    }

    @GetMapping("/images/sizes")
    public ResponseEntity<List<String>> getImageSizes() {
        return ResponseEntity.ok(imageBusinessService.getImageSizes());
    }

    @GetMapping("/images/test")
    public ResponseEntity<String> getTestImage() throws IOException {
        String remotePath = imageBusinessService.createImagePath(-1L);
        imageBusinessService.uploadFile(remotePath, "abc.png", testImageFile.getInputStream(), "image/png");

        return ResponseEntity.ok("test upload file to ftp");
    }

    @GetMapping("/images/thumbnail-url")
    public ResponseEntity<String> getImageThumbnailUrl(@RequestHeader(name = "Host", required = false) String host) {
        logger.info("Host of request header: {}", host);

        return ResponseEntity.ok(imageBusinessService.getImageThumbnailUrl(host));
    }

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
                logger.error("unable to get image from host path[{}] and size[{}]: {}", path, size, e.getMessage());

                throw new BadRequestAlertException("unable to get image from host", ENTITY_NAME, null);
            }
        });

        return result;
    }

    private synchronized Instant getSyncNow() {
        syncNow = Instant.now();

        return syncNow;
    }
}
