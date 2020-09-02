package io.dentall.totoro.business.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.http.AuthHttpConstants;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import io.dentall.totoro.business.dto.SmsEventsPagination;
import io.dentall.totoro.service.dto.SmsChargeDTO;
import io.dentall.totoro.service.dto.SmsEventDTO;
import io.dentall.totoro.web.rest.vm.SmsInfoVM;
import io.github.jhipster.config.JHipsterConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.zalando.problem.ThrowableProblem;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.dentall.totoro.business.service.GcpConstants.CLOUD_FUNCTION_BASE_URL;
import static io.dentall.totoro.business.service.GcpConstants.SERVICE_ACCOUNT;

@Profile("saas")
@Service
public class CloudFunctionService {

    private final Logger log = LoggerFactory.getLogger(CloudFunctionService.class);

    private final ObjectMapper mapper;

    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    private final RestTemplate restTemplate;

    private final String BUCKET_NAME;

    private String smsFunction = CLOUD_FUNCTION_BASE_URL + "smsDev";

    public CloudFunctionService(
        ObjectMapper mapper,
        RestTemplateBuilder restTemplateBuilder,
        Environment env,
        @Value("${gcp.bucket-name}") String bucketName
    ) {
        this.mapper = mapper;
        restTemplate = restTemplateBuilder
            .errorHandler(new RestTemplateResponseErrorHandler())
            .build();
        BUCKET_NAME = bucketName;
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            smsFunction = CLOUD_FUNCTION_BASE_URL + "sms";
        }
    }

    public String executeSmsEvent(String clinic, String eventId) throws IOException, ThrowableProblem {
        String api = smsFunction + GcpConstants.EXECUTE_SMS_EVENT_API;
        String token = getIdTokenWithAudience(smsFunction);
        Map<String, String> map = Stream.of(
            new AbstractMap.SimpleEntry<>("clinic", clinic),
            new AbstractMap.SimpleEntry<>("eventId", eventId))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(map), getRequestHeaderBearer(token));
        return restTemplate.postForObject(api, request, String.class);
    }

    public String chargeSms(SmsChargeDTO dto) throws IOException, ThrowableProblem {
        String api = smsFunction + GcpConstants.CHARGE_SMS_API;
        String token = getIdTokenWithAudience(smsFunction);
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(dto), getRequestHeaderBearer(token));
        return restTemplate.postForObject(api, request, String.class);
    }

    public SmsEventDTO upsertSmsEvent(SmsEventDTO dto) throws IOException, ThrowableProblem {
        String api = smsFunction + GcpConstants.UPSERT_SMS_EVENT_API;
        String token = getIdTokenWithAudience(smsFunction);
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(dto), getRequestHeaderBearer(token));
        return restTemplate.postForObject(api, request, SmsEventDTO.class);
    }

    public Page<SmsEventDTO> getSmsEvents(String clinic, Pageable pageable) throws IOException, ThrowableProblem {
        int size = pageable.getPageSize();
        int offset = pageable.getPageNumber() * pageable.getPageSize();

        String api = smsFunction + GcpConstants.GET_SMS_EVENTS_API;
        String token = getIdTokenWithAudience(smsFunction);
        Map<String, Object> map = Stream.of(
            new AbstractMap.SimpleEntry<>("clinic", clinic),
            new AbstractMap.SimpleEntry<>("size", size),
            new AbstractMap.SimpleEntry<>("offset", offset))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(map), getRequestHeaderBearer(token));
        SmsEventsPagination response = restTemplate.postForObject(api, request, SmsEventsPagination.class);

        if (response == null ||
            response.getEvents() == null ||
            response.getEvents().size() == 0
        ) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }

        return new PageImpl<>(response.getEvents(), pageable, response.getTotal());
    }

    public SmsInfoVM getSmsInfo(String clinic) throws IOException, ThrowableProblem {
        String api = smsFunction + GcpConstants.GET_SMS_INFO_API;
        String token = getIdTokenWithAudience(smsFunction);
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(Collections.singletonMap("clinic", clinic)), getRequestHeaderBearer(token));
        return restTemplate.postForObject(api, request, SmsInfoVM.class);
    }

    public String deleteSmsEvent(String clinic, String eventId) throws IOException, ThrowableProblem {
        String api = smsFunction + GcpConstants.DELETE_SMS_EVENT_API;
        String token = getIdTokenWithAudience(smsFunction);
        Map<String, String> map = Stream.of(
            new AbstractMap.SimpleEntry<>("clinic", clinic),
            new AbstractMap.SimpleEntry<>("eventId", eventId))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(map), getRequestHeaderBearer(token));
        return restTemplate.postForObject(api, request, String.class);
    }

    private HttpHeaders getRequestHeaderBearer(String token) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AuthHttpConstants.AUTHORIZATION, AuthHttpConstants.BEARER.concat(" ").concat(token));

        return headers;
    }

    private String getIdTokenWithAudience(String audience) throws IOException {
        BlobId blobId = BlobId.of(BUCKET_NAME, SERVICE_ACCOUNT);
        ServiceAccountCredentials credentials = ServiceAccountCredentials.fromStream(
            new ByteArrayInputStream(storage.readAllBytes(blobId))
        );

        return credentials.idTokenWithAudience(audience, null).getTokenValue();
    }
}
