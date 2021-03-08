package com.ponomarenko.amazonsdk.writer.impl;

import com.ponomarenko.amazonsdk.config.aws.properties.AwsProperties;
import com.ponomarenko.amazonsdk.domain.Content;
import com.ponomarenko.amazonsdk.writer.Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class WriterS3 implements Writer {

    private final S3Client s3Client;
    private final AwsProperties properties;


    @Override
    public void write(Content content) {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(properties.getS3().getBucketName())
                        .key(content.getName() + ".txt")
                        .build(),
                RequestBody.fromString(content.getText()));
    }
}
