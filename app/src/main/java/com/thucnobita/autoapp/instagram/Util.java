package com.thucnobita.autoapp.instagram;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
    private Util() {

    }
    public static String object2String(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    public static JsonNode string2Json(String str) throws JsonProcessingException {
        return new ObjectMapper().readTree(str);
    }

}
