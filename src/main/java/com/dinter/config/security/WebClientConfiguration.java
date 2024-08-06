package com.dinter.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientConfiguration {

    @Bean
    public WebClient webClient(ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2AuthorizedClientExchangeFilterFunction) {
        return WebClient.builder()
                .apply(oauth2AuthorizedClientExchangeFilterFunction.oauth2Configuration())
                .build();
    }
}
