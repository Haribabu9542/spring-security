package com.java.normalsecurity.service;

import java.util.HashMap;
import java.util.Map;

public class Demo {
    public static Map<String, Object> customResponses(Integer errorCode, String responseMessage, String erroMessage) {
        Map<String, Object> customResponses = new HashMap<>();
        Map<String, Object> header = new HashMap<>();
        header.put("code", errorCode);

        Map<String, Object> body = new HashMap<>();
        body.put("value", responseMessage);
        body.put("error", erroMessage);

        customResponses.put("header", header);
        customResponses.put("body", body);
        return customResponses;
    }

}
