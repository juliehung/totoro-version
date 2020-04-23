package io.dentall.totoro.business.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.http.AuthHttpConstants;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import io.dentall.totoro.config.ImageRepositoryConfiguration;
import io.dentall.totoro.service.dto.SmsChargeDTO;
import io.dentall.totoro.service.dto.SmsEventDTO;
import io.dentall.totoro.web.rest.vm.SmsInfoVM;
import io.github.jhipster.config.JHipsterConstants;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.zalando.problem.ThrowableProblem;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Profile("saas")
@Service
public class CloudFunctionService {

    private final ObjectMapper mapper;

    private Storage storage = StorageOptions.getDefaultInstance().getService();

    private RestTemplate restTemplate;

    private String functionSuffix = "";

    public CloudFunctionService(ObjectMapper mapper, RestTemplateBuilder restTemplateBuilder, Environment env) {
        this.mapper = mapper;
        restTemplate = restTemplateBuilder
            .errorHandler(new RestTemplateResponseErrorHandler())
            .build();
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)) {
            functionSuffix = "Dev";
        }
    }

    public String executeSmsEvent(String clinic, String eventId) throws IOException, ThrowableProblem {
        String function = GcpConstants.EXECUTE_SMS_EVENT_FUNCTION + functionSuffix;
        String token = getIdTokenWithAudience(function);
        Map<String, String> map = Stream.of(
            new AbstractMap.SimpleEntry<>("clinic", clinic),
            new AbstractMap.SimpleEntry<>("eventId", eventId))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(map), getRequestHeaderBearer(token));
        return restTemplate.postForObject(function, request, String.class);
    }

    public String chargeSms(SmsChargeDTO dto) throws IOException, ThrowableProblem {
        String function = GcpConstants.CHARGE_SMS_FUNCTION + functionSuffix;
        String token = getIdTokenWithAudience(function);
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(dto), getRequestHeaderBearer(token));
        return restTemplate.postForObject(function, request, String.class);
    }

    public SmsEventDTO upsertSmsEvent(SmsEventDTO dto) throws IOException, ThrowableProblem {
        String function = GcpConstants.UPSERT_SMS_EVENT_FUNCTION + functionSuffix;
        String token = getIdTokenWithAudience(function);
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(dto), getRequestHeaderBearer(token));
        return restTemplate.postForObject(function, request, SmsEventDTO.class);
    }

    public List<SmsEventDTO> getSmsEvents(String clinic) throws IOException, ThrowableProblem {
        String function = GcpConstants.GET_SMS_EVENTS_FUNCTION + functionSuffix;
        String token = getIdTokenWithAudience(function);
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(Collections.singletonMap("clinic", clinic)), getRequestHeaderBearer(token));
        ResponseEntity<List<SmsEventDTO>> response = restTemplate.exchange(
            function,
            HttpMethod.POST,
            request,
            new ParameterizedTypeReference<List<SmsEventDTO>>() {}
            );

        return response.getBody();
    }

    public SmsInfoVM getSmsInfo(String clinic) throws IOException, ThrowableProblem {
        String function = GcpConstants.GET_SMS_INFO_FUNCTION + functionSuffix;
        String token = getIdTokenWithAudience(function);
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(Collections.singletonMap("clinic", clinic)), getRequestHeaderBearer(token));
        return restTemplate.postForObject(function, request, SmsInfoVM.class);
    }

    public String deleteSmsEvent(String clinic, String eventId) throws IOException, ThrowableProblem {
        String function = GcpConstants.DELETE_SMS_EVENT_FUNCTION + functionSuffix;
        String token = getIdTokenWithAudience(function);
        Map<String, String> map = Stream.of(
            new AbstractMap.SimpleEntry<>("clinic", clinic),
            new AbstractMap.SimpleEntry<>("eventId", eventId))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(map), getRequestHeaderBearer(token));
        return restTemplate.postForObject(function, request, String.class);
    }

    private HttpHeaders getRequestHeaderBearer(String token) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AuthHttpConstants.AUTHORIZATION, AuthHttpConstants.BEARER.concat(" ").concat(token));

        return headers;
    }

    private String getIdTokenWithAudience(String audience) throws IOException {
        String clinic = ImageRepositoryConfiguration.BASIC_FOLDER_PATH;
        BlobId blobId = BlobId.of(
            GcpConstants.BUCKET_NAME,
            // ex: rakumi/dentall-saas-rakumi.json
            clinic.concat("/").concat(GcpConstants.SERVICE_ACCOUNT_PREFIX).concat(clinic).concat(".json")
        );
        ServiceAccountCredentials credentials = ServiceAccountCredentials.fromStream(
            new ByteArrayInputStream(storage.readAllBytes(blobId))
        );

        return credentials.idTokenWithAudience(audience, null).getTokenValue();
    }
}
