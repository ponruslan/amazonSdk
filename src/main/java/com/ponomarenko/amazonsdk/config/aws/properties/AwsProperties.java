package com.ponomarenko.amazonsdk.config.aws.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("aws")
public class AwsProperties {

    private String keyId;
    private String accessKey;
    private S3Properties s3;
    private SqsProperties sqs;
}
