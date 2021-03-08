package com.ponomarenko.amazonsdk.sqs;

import com.ponomarenko.amazonsdk.config.aws.properties.AwsProperties;
import com.ponomarenko.amazonsdk.util.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
public class QueueMessagingTemplate {

    private final SqsClient sqsClient;
    private final AwsProperties properties;

    public <T> void convertAndSend(String queueName, T message) {
        String json = JsonParser.parseToJson(message);
        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(getQueueUrl(queueName))
                .messageBody(json)
                .build());
    }

    public <T> List<T> receiveAndConvert(String queueName, Class<T> targetClass) {
        List<Message> messages = receiveMessage(queueName);
        List<T> collect = messages.stream()
                .map(message -> JsonParser.jsonToObject(message.body(), targetClass))
                .collect(Collectors.toList());
        deleteMessagesAfterReceipt(queueName, messages);
        return collect;
    }

    public void deleteMessagesAfterReceipt(String queueName, List<Message> messages) {
        String queueUrl = getQueueUrl(queueName);
        try {
            for (Message message : messages) {
                DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .receiptHandle(message.receiptHandle())
                        .build();
                sqsClient.deleteMessage(deleteMessageRequest);
            }
        } catch (SqsException e) {
            log.error(e.awsErrorDetails().errorMessage());
        }
    }

    private String getQueueUrl(String queueName) {
        GetQueueUrlRequest queueUrlRequest = GetQueueUrlRequest.builder()
                .queueName(queueName)
                .build();
        return sqsClient
                .getQueueUrl(queueUrlRequest)
                .queueUrl();
    }

    private List<Message> receiveMessage(String queueName) {
        return sqsClient.receiveMessage(ReceiveMessageRequest.builder()
                .queueUrl(getQueueUrl(queueName))
                .maxNumberOfMessages(properties.getSqs().getMaxNumberOfMessages())
                .build()).messages();
    }
}
