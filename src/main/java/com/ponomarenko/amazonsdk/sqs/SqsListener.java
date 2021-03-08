package com.ponomarenko.amazonsdk.sqs;

import com.ponomarenko.amazonsdk.config.aws.properties.AwsProperties;
import com.ponomarenko.amazonsdk.domain.Content;
import com.ponomarenko.amazonsdk.writer.Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class SqsListener {

    private final List<Writer> writerList;
    private final QueueMessagingTemplate queueMessagingTemplate;
    private final AwsProperties properties;

    @Scheduled(fixedDelay = 200)
    public void scheduleFixedDelayTask() {
        List<Content> contents = queueMessagingTemplate
                .receiveAndConvert(properties.getSqs().getQueueName(), Content.class);
        if (!contents.isEmpty()) {
            contents.forEach(content -> writerList.forEach(writer -> writer.write(content)));
        }
    }
}