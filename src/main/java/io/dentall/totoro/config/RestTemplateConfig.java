package io.dentall.totoro.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(CloseableHttpClient httpClient) {
        return new RestTemplateBuilder()
            .requestFactory(() -> {
                HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
                clientHttpRequestFactory.setHttpClient(httpClient);
                return clientHttpRequestFactory;
            })
            .errorHandler(new CustomClientErrorHandler())
            .interceptors(new CustomClientHttpRequestInterceptor())
            .build();
    }


    public static class CustomClientErrorHandler implements ResponseErrorHandler {

        private final Logger log = LoggerFactory.getLogger(CustomClientErrorHandler.class);

        @Override
        public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
            return clientHttpResponse.getStatusCode().is4xxClientError();
        }

        @Override
        public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
            log.error("HTTP Status Code: " + clientHttpResponse.getStatusCode().value());
        }
    }

    public static class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

        private final Logger log = LoggerFactory.getLogger(CustomClientHttpRequestInterceptor.class);

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {
            log.debug("URI: {}", request.getURI());
            log.debug("HTTP Method: {}", request.getMethodValue());
            log.debug("HTTP Headers: {}", request.getHeaders());
            return execution.execute(request, bytes);
        }
    }

}
