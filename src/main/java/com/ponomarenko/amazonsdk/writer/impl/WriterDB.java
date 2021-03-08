package com.ponomarenko.amazonsdk.writer.impl;

import com.ponomarenko.amazonsdk.domain.Content;
import com.ponomarenko.amazonsdk.repository.ContentRepository;
import com.ponomarenko.amazonsdk.writer.Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WriterDB implements Writer {

    private final ContentRepository contentRepository;

    @Override
    public void write(Content content) {
        contentRepository.save(content);
    }
}
