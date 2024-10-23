package com.example.band_activity.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class JsonUtil {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object fromJson(String json, String typeName) {
        try {
            return objectMapper.readValue(json, Class.forName(typeName));
        } catch (JsonProcessingException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
