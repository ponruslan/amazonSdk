package com.ponomarenko.amazonsdk.config.aws;

import com.ponomarenko.amazonsdk.config.aws.properties.AwsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class S3ConfigLocal {

    private final StaticCredentialsProvider credentialsProvider;
    private final AwsProperties properties;

    @Bean
    public S3Client s3Client() {
        log.info("Connecting to locally running S3 server: {}", properties.getS3().getUrl());
        return S3Client.builder()
                .endpointOverride(URI.create(properties.getS3().getUrl()))
                .region(Region.EU_WEST_1)
                .credentialsProvider(credentialsProvider)
                .build();
    }
}
