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

    public String sendSms(SmsSendDTO sms) throws IOException {
        HttpEntity<String> request = getRequest(sms);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(GcpConstants.SEND_SMS_FUNCTION, request, String.class);
        SmsStatus status = SmsStatus.valueOf(result);

        // check status do something...

        return status.toString();
    }

    public String chargeSms(SmsChargeDTO sms) throws IOException {
        HttpEntity<String> request = getRequest(sms);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(GcpConstants.CHARGE_SMS_FUNCTION, request, String.class);
        SmsStatus status = SmsStatus.valueOf(result);

        // check status do something...

        return status.toString();
    }

    private HttpEntity<String> getRequest(Object value) throws IOException {
        return new HttpEntity<>(mapper.writeValueAsString(value), getRequestHeaderBearer());
    }

    private HttpHeaders getRequestHeaderBearer() throws IOException {
        String token = getIdToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AuthHttpConstants.AUTHORIZATION, AuthHttpConstants.BEARER.concat(" ").concat(token));

        return headers;
    }

    private String getIdToken() throws IOException {
        String clinic = ImageRepositoryConfiguration.BASIC_FOLDER_PATH;
        BlobId blobId = BlobId.of(
            GcpConstants.BUCKET_NAME,
            // ex: rakumi/dentall-saas-rakumi.json
            clinic.concat("/").concat(GcpConstants.SERVICE_ACCOUNT_PREFIX).concat(clinic).concat(".json")
        );
        ServiceAccountCredentials credentials = ServiceAccountCredentials.fromStream(
            new ByteArrayInputStream(storage.readAllBytes(blobId))
        );

        return credentials.idTokenWithAudience(GcpConstants.SEND_SMS_FUNCTION, null).getTokenValue();
    }

    private enum SmsStatus {
        ok, notFound, charge
    }
}
