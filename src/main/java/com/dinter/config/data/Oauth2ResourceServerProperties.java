package com.dinter.config.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver")
public class Oauth2ResourceServerProperties {
    private Map<String, Jwt> providers;

    @Data
    public static class Jwt {

        /**
         * JSON Web Key URI to use to verify the JWT token.
         */
        private String jwkSetUri;

        /**
         * JSON Web Algorithms used for verifying the digital signatures.
         */
        private List<String> jwsAlgorithms = Arrays.asList("RS256");

        /**
         * URI that can either be an OpenID Connect discovery endpoint or an OAuth 2.0
         * Authorization Server Metadata endpoint defined by RFC 8414.
         */
        private String issuerUri;

        /**
         * Location of the file containing the public key used to verify a JWT.
         */
        private Resource publicKeyLocation;

        /**
         * Identifies the recipients that the JWT is intended for.
         */
        private List<String> audiences = new ArrayList<>();

        /**
         * Prefix to use for authorities mapped from JWT.
         */
        private String authorityPrefix;

        /**
         * Regex to use for splitting the value of the authorities claim into authorities.
         */
        private String authoritiesClaimDelimiter;

        /**
         * Name of token claim to use for mapping authorities from JWT.
         */
        private String authoritiesClaimName;

        /**
         * JWT principal claim name.
         */
        private String principalClaimName;

        public String readPublicKey() throws IOException {
            String key = "spring.security.oauth2.resourceserver.public-key-location";
            Assert.notNull(this.publicKeyLocation, "PublicKeyLocation must not be null");
            if (!this.publicKeyLocation.exists()) {
                throw new InvalidConfigurationPropertyValueException(key, this.publicKeyLocation,
                        "Public key location does not exist");
            }
            try (InputStream inputStream = this.publicKeyLocation.getInputStream()) {
                return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            }
        }

    }
}
