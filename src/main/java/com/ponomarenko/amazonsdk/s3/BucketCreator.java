package com.ponomarenko.amazonsdk.s3;

import com.ponomarenko.amazonsdk.config.aws.properties.AwsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import javax.annotation.PostConstruct;

@Component
@Slf4j
@RequiredArgsConstructor
public class BucketCreator {

    private final S3Client s3Client;
    private final AwsProperties properties;

    @PostConstruct
    private void initialiseBuckets() {
        log.info("Initialising buckets...");
        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3Client.listBuckets(listBucketsRequest);
        if (listBucketsResponse.buckets().isEmpty()) {
            createBucket();
        }
    }

    private void createBucket() {
        log.info("Creating Bucket \"{}\"", properties.getS3().getBucketName());
        try {
            S3Waiter s3Waiter = s3Client.waiter();
            CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                    .bucket(properties.getS3().getBucketName())
                    .createBucketConfiguration(
                            CreateBucketConfiguration.builder()
                                    .build())
                    .build();

            s3Client.createBucket(bucketRequest);
            HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
                    .bucket(properties.getS3().getBucketName())
                    .build();

            WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(bucketRequestWait);
            if (waiterResponse.matched().response().isPresent()) {
                log.info(properties.getS3().getBucketName() + " is ready");
            }
        } catch (S3Exception e) {
            log.error(e.awsErrorDetails().errorMessage());
        }
    }
}
