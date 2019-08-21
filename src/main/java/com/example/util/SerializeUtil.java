package com.example.util;

import com.example.config.WebMvcConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author wangbin
 */
public class SerializeUtil {

    public static <T> String serialize(T object) {
        ObjectMapper om = new WebMvcConfig.CustomMapper();
        try {
            return om.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static <T> T deserialize(String params, Class<T> clazz) {
        ObjectMapper om = new WebMvcConfig.CustomMapper();
        try {
            return om.readValue(params, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
