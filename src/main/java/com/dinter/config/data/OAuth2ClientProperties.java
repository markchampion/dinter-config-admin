package com.dinter.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.client")
public class OAuth2ClientProperties {
    private Map<String, ClientProperties> registration;

    @Data
    public class ClientProperties {
        String clientId;
        String clientSecret;
        String scope;
        String authorizationGrantType;
        String tokenUri;
    }
}
