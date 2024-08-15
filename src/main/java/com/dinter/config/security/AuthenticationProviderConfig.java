package com.dinter.config.security;

import com.dinter.config.data.DinterOauth2ResourceServerProperties;
import com.dinter.config.data.JwtConfig;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AuthenticationProviderConfig {

    private final JwtConfig jwtConfig;
    private final DinterOauth2ResourceServerProperties resourceServerProperties;

    public AuthenticationProviderConfig(JwtConfig jwtConfig, DinterOauth2ResourceServerProperties resourceServerProperties) {
        this.jwtConfig = jwtConfig;
        this.resourceServerProperties = resourceServerProperties;
    }


    @Bean
    public JwtAuthenticationProvider dinterJwtAuthenticationProvider(@Qualifier("dinterJwtDecoder") JwtDecoder dinterJwtDecoder) {
        return new JwtAuthenticationProvider(dinterJwtDecoder);
    }

    @Bean
    public JwtAuthenticationProvider oauth2JwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(oauth2JwtDecoders().get("google"));
    }

    @Bean
    public JwtDecoder dinterJwtDecoder()  {
        val secretKeySpec = new SecretKeySpec(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    public Map<String, JwtDecoder> oauth2JwtDecoders() {
        val jwtDecoders = new HashMap<String, JwtDecoder>();
        resourceServerProperties.getJwts().forEach((key, value) -> {
            val decoder = NimbusJwtDecoder.withJwkSetUri(value.getJwkSetUri())
                    .build();
            jwtDecoders.put(key, decoder);
        });
        return jwtDecoders;
    }
}
