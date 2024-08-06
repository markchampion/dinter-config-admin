package com.dinter.config.security;

import com.dinter.config.data.OAuth2ClientProperties;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    private final OAuth2ClientProperties properties;

    public WebClientConfiguration(OAuth2ClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    public WebClient webClient(ReactiveOAuth2AuthorizedClientManager reactiveOAuth2AuthorizedClientManager) {
        val serverOAuth2AuthorizedClientExchangeFilterFunction = new ServerOAuth2AuthorizedClientExchangeFilterFunction(reactiveOAuth2AuthorizedClientManager);
        serverOAuth2AuthorizedClientExchangeFilterFunction.setDefaultClientRegistrationId("github");
        return WebClient.builder()
                .filter(serverOAuth2AuthorizedClientExchangeFilterFunction)
                .build();
    }
}
