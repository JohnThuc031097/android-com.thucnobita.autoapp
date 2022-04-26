package com.thucnobita.autoapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Util {
    private Util(){

    }
    public static String object2String(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    public static JsonNode string2Json(String data) throws JsonProcessingException {
        return new ObjectMapper().readTree(data);
    }

    public static JsonNode string2Json(File file) throws IOException {
        return new ObjectMapper().readTree(file);
    }
}
