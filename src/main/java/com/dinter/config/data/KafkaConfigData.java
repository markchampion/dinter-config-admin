package com.dinter.config.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-config")
public class KafkaConfigData {
    private String bootstrapServers;
    private String schemaRegistryUrlKey;
    private String schemaRegistryUrl;
    private String topicName;
    private Map<String, KafkaTopicDetails> topicNames;
    private Integer numOfPartitions;
    private Short replicationFactor;

    @Getter
    @Setter
    public static class KafkaTopicDetails {
        private String topicName;
        private Integer numOfPartitions;
        private Short replicationFactor;
    }

}
