package com.ponomarenko.amazonsdk.sqs;

import com.ponomarenko.amazonsdk.config.aws.properties.AwsProperties;
import com.ponomarenko.amazonsdk.domain.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SqsPublisher {

    private final QueueMessagingTemplate queueMessagingTemplate;
    private final AwsProperties properties;

    public void send(Content message) {
        queueMessagingTemplate.convertAndSend(properties.getSqs().getQueueName(), message);
    }
}
