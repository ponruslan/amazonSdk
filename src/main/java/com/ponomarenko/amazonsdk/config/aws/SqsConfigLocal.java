package com.ponomarenko.amazonsdk.config.aws;

import com.ponomarenko.amazonsdk.config.aws.properties.AwsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SqsConfigLocal {

    private final StaticCredentialsProvider credentials;
    private final AwsProperties properties;

    @Bean
    public SqsClient sqsClient() {
        log.info("Connecting to locally running SQS server: {}", properties.getSqs().getUrl());
        return SqsClient.builder()
                .endpointOverride(URI.create(properties.getSqs().getUrl()))
                .region(Region.EU_WEST_1)
                .credentialsProvider(credentials)
                .build();
    }
}
