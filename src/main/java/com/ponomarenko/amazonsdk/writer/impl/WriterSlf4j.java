package com.ponomarenko.amazonsdk.writer.impl;

import com.ponomarenko.amazonsdk.domain.Content;
import com.ponomarenko.amazonsdk.writer.Writer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
@Slf4j
public class WriterSlf4j implements Writer {

    @Override
    public void write(Content content) {
        log.info("{} : {}", content.getName(), content.getText());
    }
}
