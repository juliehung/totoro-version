package io.dentall.totoro.web.rest;

import io.dentall.totoro.business.service.CloudFunctionService;
import io.dentall.totoro.config.ImageRepositoryConfiguration;
import io.dentall.totoro.domain.SmsView;
import io.dentall.totoro.security.SecurityUtils;
import io.dentall.totoro.service.SmsViewService;
import io.dentall.totoro.service.dto.SmsChargeDTO;
import io.dentall.totoro.service.dto.SmsEventDTO;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.errors.InternalServerErrorException;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.web.rest.vm.SmsInfoVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.zalando.problem.ThrowableProblem;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;

@Profile("saas")
@RestController
@RequestMapping("/api")
public class MessageResource {

    private final Logger log = LoggerFactory.getLogger(MessageResource.class);

    private static final String ENTITY_NAME = "message";

    private final CloudFunctionService cloudFunctionService;

    private final SmsViewService smsViewService;

    public MessageResource(CloudFunctionService cloudFunctionService, SmsViewService smsViewService) {
        this.cloudFunctionService = cloudFunctionService;
        this.smsViewService = smsViewService;
    }

    @PostMapping("/messages/sms/events/{eventId}/execute")
    public DeferredResult<ResponseEntity<String>> executeSmsEvent(@PathVariable String eventId) {
        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(() -> {
            try {
                result.setResult(ResponseEntity.ok(cloudFunctionService.executeSmsEvent(ImageRepositoryConfiguration.BASIC_FOLDER_PATH, eventId)));
            } catch (IOException | ThrowableProblem e) {
                result.setErrorResult(e);
            }
        });

        return result;
    }

    @PostMapping("/messages/sms/charge")
    public DeferredResult<ResponseEntity<String>> chargeSms(@Valid @RequestBody SmsChargeDTO dto) {
        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(() -> {
            try {
                result.setResult(ResponseEntity.ok(cloudFunctionService.chargeSms(dto)));
            } catch (IOException | ThrowableProblem e) {
                result.setErrorResult(e);
            }
        });

        return result;
    }

    @PostMapping("/messages/sms/events")
    public DeferredResult<ResponseEntity<SmsEventDTO>> createSmsEvent(@Valid @RequestBody SmsEventDTO dto) {
        final String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        dto.setCreatedBy(userLogin);
        dto.setModifiedBy(userLogin);
        dto.setClinic(ImageRepositoryConfiguration.BASIC_FOLDER_PATH);

        DeferredResult<ResponseEntity<SmsEventDTO>> result = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(() -> {
            try {
                result.setResult(ResponseEntity.ok(cloudFunctionService.upsertSmsEvent(dto)));
            } catch (IOException | ThrowableProblem e) {
                result.setErrorResult(e);
            }
        });

        return result;
    }

    @PutMapping("/messages/sms/events")
    public DeferredResult<ResponseEntity<SmsEventDTO>> updateSmsEvent(@Valid @RequestBody SmsEventDTO dto) {
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        final String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        dto.setModifiedBy(userLogin);
        dto.setClinic(ImageRepositoryConfiguration.BASIC_FOLDER_PATH);

        DeferredResult<ResponseEntity<SmsEventDTO>> result = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(() -> {
            try {
                result.setResult(ResponseEntity.ok(cloudFunctionService.upsertSmsEvent(dto)));
            } catch (IOException | ThrowableProblem e) {
                result.setErrorResult(e);
            }
        });

        return result;
    }

    @GetMapping("/messages/sms/events")
    public DeferredResult<ResponseEntity<List<SmsEventDTO>>> getSmsEvents(
        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
        @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        DeferredResult<ResponseEntity<List<SmsEventDTO>>> result = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(() -> {
            try {
                Page<SmsEventDTO> events = cloudFunctionService.getSmsEvents(ImageRepositoryConfiguration.BASIC_FOLDER_PATH, PageRequest.of(page, size));
                HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                    events, "/messages/sms/events"
                );

                result.setResult(ResponseEntity.ok().headers(headers).body(events.getContent()));
            } catch (IOException | ThrowableProblem e) {
                result.setErrorResult(e);
            }
        });

        return result;
    }

    @GetMapping("/messages/sms")
    public DeferredResult<ResponseEntity<SmsInfoVM>> getSmsInfo() {
        DeferredResult<ResponseEntity<SmsInfoVM>> result = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(() -> {
            try {
                result.setResult(ResponseEntity.ok(cloudFunctionService.getSmsInfo(ImageRepositoryConfiguration.BASIC_FOLDER_PATH)));
            } catch (IOException | ThrowableProblem e) {
                result.setErrorResult(e);
            }
        });

        return result;
    }

    @DeleteMapping("/messages/sms/events/{eventId}")
    public DeferredResult<ResponseEntity<String>> deleteSmsEvent(@PathVariable String eventId) {
        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(() -> {
            try {
                result.setResult(ResponseEntity.ok(cloudFunctionService.deleteSmsEvent(ImageRepositoryConfiguration.BASIC_FOLDER_PATH, eventId)));
            } catch (IOException | ThrowableProblem e) {
                result.setErrorResult(e);
            }
        });

        return result;
    }

    @PostMapping("/messages/sms/views/{appointmentId}")
    public ResponseEntity<SmsView> createSmsView(@PathVariable Long appointmentId) {
        return ResponseEntity.ok().body(smsViewService.save(appointmentId));
    }

    @GetMapping("/messages/sms/views/{appointmentId}")
    public ResponseEntity<SmsView> getSmsView(@PathVariable Long appointmentId) {
        Optional<SmsView> smsView = smsViewService.findOne(appointmentId);
        return ResponseUtil.wrapOrNotFound(smsView);
    }
}
