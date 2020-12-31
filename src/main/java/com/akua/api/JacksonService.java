package com.akua.api;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JacksonService {
    private final ObjectMapper mapper;

    public JacksonService(){
        this.mapper = new ObjectMapper()
                            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                            .enable(SerializationFeature.INDENT_OUTPUT); // for pretty print
    }

    /* >> Serialize << */
    public <T> String objectToJson(T object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public <T> void objectToJsonFile(T object, String path) throws IOException {
        mapper.writeValue(new File(path), object);
    }

    // List<T> to Json
    public <T> String objectListToJson(List<T> objectList) throws JsonProcessingException {
        return mapper.writeValueAsString(objectList);
    }

    // Map<K,V> to Json
    public <K,V> String mapToJson(Map<K,V> map) throws JsonProcessingException {
        return mapper.writeValueAsString(map);
    }

    /* >> Deserialize << */
    public <T> T jsonToObject(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }

    public <T> T jsonFileToObject(String path, Class<T> clazz) throws IOException {
        return mapper.readValue(new File(path), clazz);
    }
}
