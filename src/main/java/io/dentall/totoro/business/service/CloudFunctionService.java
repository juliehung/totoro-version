package io.dentall.totoro.business.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.http.AuthHttpConstants;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import io.dentall.totoro.config.ImageRepositoryConfiguration;
import io.dentall.totoro.service.dto.SmsDTO;
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

    public String sendSms(SmsDTO sms) throws IOException {
        String token = getIdToken();
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(sms), getRequestHeaderBearer(token));
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(GcpConstants.SMS_FUNCTION, request, String.class);
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

        return credentials.idTokenWithAudience(GcpConstants.SMS_FUNCTION, null).getTokenValue();
    }

    private enum SmsStatus {
        ok, notFound, charge
    }
}
