package com.dinter.config.security;

import com.dinter.config.data.OAuth2ClientProperties;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.DefaultReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.stream.Collectors;

@Configuration
public class Oauth2ClientRegistrationConfig {

    private final OAuth2ClientProperties properties;

    public Oauth2ClientRegistrationConfig(OAuth2ClientProperties oAuth2ClientProperties) {
        this.properties = oAuth2ClientProperties;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        val registrations = properties.getRegistration().entrySet().stream()
                .map(entry -> {
                    val clientProperty = entry.getValue();
                    return ClientRegistration.withRegistrationId(entry.getKey())
                            .clientId(clientProperty.getClientId())
                            .clientSecret(clientProperty.getClientSecret())
                            .tokenUri(clientProperty.getTokenUri())
                            .authorizationGrantType(new AuthorizationGrantType(clientProperty.getAuthorizationGrantType()))
                            .scope(clientProperty.getScope().split(","))
                            .build();
                }).collect(Collectors.toList());
        return new InMemoryClientRegistrationRepository(registrations);
    }


    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository
    ) {
        val authorizedClientProvider = ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();

        val authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, oAuth2AuthorizedClientRepository
        );
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        return authorizedClientManager;
    }

    @Bean
    public ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2AuthorizedClientExchangeFilterFunction(OAuth2AuthorizedClientManager authorizedClientManager) {
        return new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
    }
}
