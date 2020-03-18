package io.dentall.totoro.web.rest;

import io.dentall.totoro.business.service.CloudFunctionService;
import io.dentall.totoro.config.ImageRepositoryConfiguration;
import io.dentall.totoro.service.dto.SmsDTO;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.errors.InternalServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

@Profile("saas")
@RestController
@RequestMapping("/api")
public class MessageResource {

    private final Logger log = LoggerFactory.getLogger(MessageResource.class);

    private static final String ENTITY_NAME = "message";

    private final CloudFunctionService cloudFunctionService;

    public MessageResource(CloudFunctionService cloudFunctionService) {
        this.cloudFunctionService = cloudFunctionService;
    }

    @PostMapping("/messages/sms")
    public DeferredResult<ResponseEntity<String>> sendSms(@Valid @RequestBody SmsDTO sms) {
        if (!ImageRepositoryConfiguration.BASIC_FOLDER_PATH.equals(sms.getClinic())) {
            throw new BadRequestAlertException("Invalid clinic", ENTITY_NAME, "clinicwrong");
        }

        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(() -> {
            try {
                result.setResult(ResponseEntity.ok(cloudFunctionService.sendSms(sms)));
            } catch (IOException e) {
                throw new InternalServerErrorException("Send sms get exception: " + e);
            }
        });

        return result;
    }
}
