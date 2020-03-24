package io.dentall.totoro.business.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.http.AuthHttpConstants;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import io.dentall.totoro.config.ImageRepositoryConfiguration;
import io.dentall.totoro.service.dto.SmsChargeDTO;
import io.dentall.totoro.service.dto.SmsSendDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Profile("saas")
@Service
public class CloudFunctionService {

    private final ObjectMapper mapper;

    private Storage storage = StorageOptions.getDefaultInstance().getService();

    public CloudFunctionService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String sendSms(SmsSendDTO dto) throws IOException {
        String token = getIdTokenWithAudience(GcpConstants.SEND_SMS_FUNCTION);
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(dto), getRequestHeaderBearer(token));
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(GcpConstants.SEND_SMS_FUNCTION, request, String.class);
        SmsStatus status = SmsStatus.valueOf(result);

        // check status do something...

        return status.toString();
    }

    public String chargeSms(SmsChargeDTO dto) throws IOException {
        String token = getIdTokenWithAudience(GcpConstants.CHARGE_SMS_FUNCTION);
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(dto), getRequestHeaderBearer(token));
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(GcpConstants.CHARGE_SMS_FUNCTION, request, String.class);
        SmsStatus status = SmsStatus.valueOf(result);

        // check status do something...

        return status.toString();
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

    private enum SmsStatus {
        ok, notFound, charge
    }
}
