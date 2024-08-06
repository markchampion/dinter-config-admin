package com.dinter.config.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.client")
public class OAuth2ClientProperties {
    private Map<String, ClientProperties> registration;

    @Data
    public static class ClientProperties {
        String clientId;
        String clientSecret;
        String scope;
        String authorizationGrantType;
        String tokenUri;
        String authorizationUri;
        String authenticationMethod;
    }
}
