package com.ponomarenko.amazonsdk.sqs;

import com.ponomarenko.amazonsdk.config.aws.properties.AwsProperties;
import com.ponomarenko.amazonsdk.domain.Content;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueryCreator {

    private final SqsClient sqsClient;
    private final SqsPublisher sqsPublisher;
    private final AwsProperties properties;

    @PostConstruct
    private void initialiseQueues() {
        log.info("Initialising queues...");
        log.info("Creating Queue {}", properties.getSqs().getQueueName());
        sqsClient.createQueue(CreateQueueRequest.builder()
                .queueName(properties.getSqs().getQueueName())
                .build());
        addDemoDataToQueue();
    }

    private void addDemoDataToQueue() {
        List.of(
                new Content("some name1", "some text1"),
                new Content("some name2", "some text2"),
                new Content("some name3", "some text3"),
                new Content("some name4", "some text4"),
                new Content("some name5", "some text5"),
                new Content("some name6", "some text6"),
                new Content("some name7", "some text7"),
                new Content("some name8", "some text8"),
                new Content("some name9", "some text9"),
                new Content("some name10", "some text10"),
                new Content("some name11", "some text11"),
                new Content("some name12", "some text12")
        ).forEach(sqsPublisher::send);
    }
}
