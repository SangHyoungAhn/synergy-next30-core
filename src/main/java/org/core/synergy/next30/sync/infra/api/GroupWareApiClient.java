package org.core.synergy.next30.sync.infra.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GroupWareApiClient {

    private final RestTemplate restTemplate;
    private final GroupWareAuthService groupWareAuthService;

    @Value("${gw.base-url}")
    private String baseUrl;

    public <T> T execute(String url, String accessToken, Object requestBody, Class<T> responseType) {
        String tId = UUID.randomUUID().toString().replaceAll("-", "");
        String ts = String.valueOf(System.currentTimeMillis());
        String sign = groupWareAuthService.generateSign(accessToken,tId,ts,url);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("wehago-sign",sign);
        headers.set("transaction-id",tId);
        headers.set("timestamp",ts);
        headers.set("callerName","API_gcmsAmaranth29108");

        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForObject(baseUrl + url, entity, responseType);
    }
}
