package io.dentall.totoro.web.rest;

import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.domain.enumeration.BackupFileCatalog;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ForkJoinPool;

/**
 * REST controller for managing Version.
 */
@Profile("img-gcs")
@RestController
@RequestMapping("/api")
public class BackupResource {

    private final Logger log = LoggerFactory.getLogger(BackupResource.class);

    private final ImageGcsBusinessService imageBusinessService;

    private final String backupRemotePath;

    private final Long timeoutDuration;

    public BackupResource(
        ImageGcsBusinessService imageBusinessService,
        @Value("#{environment.IMAGE_BASIC_FOLDER_PATH}") String backupRemotePath,
        @Value("${gcp.timeout}") Long timeoutDuration
    ) {
        this.imageBusinessService = imageBusinessService;
        this.backupRemotePath = backupRemotePath;
        this.timeoutDuration = timeoutDuration;
    }

    public class BackupResponse {
        private String message;

        public BackupResponse message(String message) {
            this.message = message;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @PostMapping("/backup/{backupFileCatalog}")
    public DeferredResult<ResponseEntity<BackupResponse>> uploadBackupFile(
        @PathVariable("backupFileCatalog") BackupFileCatalog backupFileCatalog,
        @RequestParam("file") MultipartFile file
    ) {
        log.debug("Upload native file to backup GCS {}, MIME {}", file.getOriginalFilename(), file.getContentType());

        // Validation
        if (file == null || file.isEmpty() || file.getSize() <= 0) {
            throw new BadRequestAlertException("Upload not include file or it is empty", "BACKUP", "nofile");
        }

        DeferredResult<ResponseEntity<BackupResponse>> result = new DeferredResult<>(timeoutDuration);

        result.onTimeout(() -> {
            result.setResult(
                ResponseEntity
                    .status(HttpStatus.REQUEST_TIMEOUT)
                    .body(new BackupResponse()
                        .message("Timeout, duation ".concat(timeoutDuration.toString()).concat(" millisecond."))
                    )
            );
        });

        ForkJoinPool.commonPool().submit(() -> {
            try {
                imageBusinessService.uploadFile(
                    backupRemotePath
                        .concat("/")
                        .concat(backupFileCatalog.getRemotePath())
                        .concat("/"),
                    file.getOriginalFilename(),
                    file.getInputStream(),
                    file.getContentType()
                );
                result.setResult(
                    ResponseEntity
                        .accepted()
                        .body(new BackupResponse().message("Long term operation. You have to wait for a while"))
                );
            } catch (Exception e) {
                log.error("Post file to GCS fail: ".concat(e.getMessage()));
                result.setErrorResult(
                    ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new BackupResponse().message("Post file to GCS fail with exception message: ".concat(e.getMessage())))
                );
            }
        });

        return result;
    }
}
