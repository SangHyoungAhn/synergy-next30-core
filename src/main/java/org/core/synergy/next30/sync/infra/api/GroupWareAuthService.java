package org.core.synergy.next30.sync.infra.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class GroupWareAuthService {

    @Value("${gw.hash-key}")
    private String hashKey;

    public String generateSign(String accessToken, String tId, String ts, String url){

        String data = accessToken + tId + ts + url;

        try{

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(hashKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);

        }catch(Exception e){
            throw new RuntimeException("HMAC 생성 실패", e);

        }
    }


}
