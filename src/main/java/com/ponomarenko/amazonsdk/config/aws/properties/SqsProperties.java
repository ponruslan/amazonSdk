package com.ponomarenko.amazonsdk.config.aws.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "aws.sqs")
public class SqsProperties {

    private String url;
    private String queueName;
    private Integer maxNumberOfMessages;
}
