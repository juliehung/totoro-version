package io.dentall.totoro.web.rest;

import io.dentall.totoro.business.service.ImageBusinessService;
import io.dentall.totoro.domain.Image;
import io.dentall.totoro.service.ImageQueryService;
import io.dentall.totoro.service.dto.ImageCriteria;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Profile({"ftp", "synoNas"})
@RestController
@RequestMapping("/api")
public class ImageResource {

    private final Logger logger = LoggerFactory.getLogger(ImageResource.class);

    private static final String ENTITY_NAME = "image";

    @Value("classpath:chrome.png")
    private Resource testImageFile;

    private final ImageBusinessService imageBusinessService;

    private final ImageQueryService imageQueryService;

    public ImageResource(
        @Qualifier("synoNasService") ImageBusinessService imageBusinessService,
        ImageQueryService imageQueryService
    ) {
        this.imageBusinessService = imageBusinessService;
        this.imageQueryService = imageQueryService;
    }

    @PostMapping("/images")
    public DeferredResult<ResponseEntity<Image>> uploadImage(
        @RequestParam("patientId") Long patientId,
        @RequestParam("image") MultipartFile file
    ) {
        // Validation
        if (file == null || file.isEmpty() || file.getSize() <= 0) {
            throw new BadRequestAlertException("Upload not include image file or it is empty", ENTITY_NAME, "noimagefile");
        }

        DeferredResult<ResponseEntity<Image>> result = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(() -> {
            Image image;

            // Business logic
            try {
                String remotePath = imageBusinessService.createImagePath(patientId);

                // upload origin
                String remoteFileName = imageBusinessService.createOriginImageName(file.getOriginalFilename());
                imageBusinessService.uploadFile(remotePath, remoteFileName, file.getInputStream());
                image = imageBusinessService.createImage(
                    patientId, remotePath, remoteFileName, ImageBusinessService.Size.ORIGIN.toString(), null
                );

                // create and upload medium
                remoteFileName = imageBusinessService.createMediumImageName(file.getOriginalFilename());
                InputStream inputStream = imageBusinessService.getMediumImageInputStream(file.getInputStream());
                imageBusinessService.uploadFile(remotePath, remoteFileName, inputStream);
                imageBusinessService.createImage(
                    patientId, remotePath, remoteFileName, ImageBusinessService.Size.MEDIUM.toString(), image.getId()
                );
            } catch (IOException e) {
                logger.error("Upload image get exception:\n {}", e.getMessage());
                imageBusinessService.disconnect();
                throw new BadRequestAlertException("Some", "Shit", "Happened");
            }

            result.setResult(ResponseEntity.ok(image));
        });

        return result;
    }

    @GetMapping("/images")
    public ResponseEntity<List<String>> getImagesByCriteria(ImageCriteria imageCriteria) {
        String sid = imageBusinessService.getSession();

        List<String> imgUrls = imageQueryService.findByCriteria(imageCriteria).stream()
            .map(image -> image.getFetchUrl().concat("&_sid=").concat(sid))
            .collect(Collectors.toList());

        imageBusinessService.releaseSession();

        return ResponseEntity.ok(imgUrls);
    }

    @GetMapping("/images/test")
    public ResponseEntity getTestImage() throws IOException {
        String remotePath = imageBusinessService.createImagePath(-1L);
        String remoteFileName = imageBusinessService.createOriginImageName("abc.png");
        imageBusinessService.uploadFile(remotePath, remoteFileName, testImageFile.getInputStream());

        return ResponseEntity.ok().build();
    }
}
