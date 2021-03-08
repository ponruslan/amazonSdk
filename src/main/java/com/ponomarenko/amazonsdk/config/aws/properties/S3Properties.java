package com.ponomarenko.amazonsdk.config.aws.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "aws.s3")
public class S3Properties {

    private String url;
    private String bucketName;
}
