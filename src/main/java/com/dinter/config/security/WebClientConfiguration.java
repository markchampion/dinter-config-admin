package com.dinter.config.security;

import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Bean
    @ConditionalOnProperty(name = "spring.security.oauth2.client")
    public WebClient keyCloakWebClient(ReactiveOAuth2AuthorizedClientManager reactiveOAuth2AuthorizedClientManager) {
        val serverOAuth2AuthorizedClientExchangeFilterFunction = new ServerOAuth2AuthorizedClientExchangeFilterFunction(reactiveOAuth2AuthorizedClientManager);
        serverOAuth2AuthorizedClientExchangeFilterFunction.setDefaultClientRegistrationId("keycloak");

        return WebClient.builder()
                .filter(serverOAuth2AuthorizedClientExchangeFilterFunction)
                .build();
    }
    @Bean
    @ConditionalOnProperty(name = "spring.security.oauth2.client", havingValue = "true", matchIfMissing = true)
    public WebClient webClient() {
        return WebClient.builder()
                .build();
    }
}
