package com.ponomarenko.amazonsdk.config.aws;

import com.ponomarenko.amazonsdk.config.aws.properties.AwsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

@Configuration
@RequiredArgsConstructor
public class AwsConfig {

    private final AwsProperties properties;

    @Bean
    public StaticCredentialsProvider credentialsProvider() {
        return StaticCredentialsProvider.create(new AwsCredentials() {
            @Override
            public String accessKeyId() {
                return properties.getKeyId();
            }

            @Override
            public String secretAccessKey() {
                return properties.getAccessKey();
            }
        });
    }
}
