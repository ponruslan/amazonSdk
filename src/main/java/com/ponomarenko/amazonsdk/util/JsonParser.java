package com.ponomarenko.amazonsdk.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String parseToJson(Object o) {
        try {
            return OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error mapping to Json", e);
        }
    }

    public static <T> T jsonToObject(String json, Class<T> targetClass) {
        try {
            return OBJECT_MAPPER.readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error mapping to Object", e);
        }
    }
}
