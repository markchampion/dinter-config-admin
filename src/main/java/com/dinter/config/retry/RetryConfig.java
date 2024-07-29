package com.dinter.config.retry;

import com.dinter.config.data.RetryConfigData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@ComponentScan("com.dinter.config")
@Configuration
public class RetryConfig {

    private final RetryConfigData retryConfigData;

    public RetryConfig(RetryConfigData retryConfigData) {
        this.retryConfigData = retryConfigData;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
        policy.setInitialInterval(retryConfigData.getInitialIntervalMs());
        policy.setMaxInterval(retryConfigData.getMaxIntervalMs());
        policy.setMultiplier(retryConfigData.getMultiplier());

        retryTemplate.setBackOffPolicy(policy);

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(retryConfigData.getMaxAttempts());

        retryTemplate.setRetryPolicy(simpleRetryPolicy);

        return retryTemplate;
    }
}
