package org.core.synergy.next30.sync.infra.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupWareAuthResponse {
    /**
     *  접근토큰: accessToken
     *  만료시간: expiresIn
     */
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private Long expiresIn;
}
