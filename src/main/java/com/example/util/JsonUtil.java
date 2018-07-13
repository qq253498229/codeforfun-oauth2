package com.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangbin
 */
public class JsonUtil {

  public static Map<String, Object> parse(String json) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(json, HashMap.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> T parse(String json, Class<T> clazz) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(json, clazz);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String toJson(Object object) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }
}
