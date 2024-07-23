package com.dinter.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("config.jwt")
public class JwtConfig {
    private String secret;
    private String expirationTime;
}
