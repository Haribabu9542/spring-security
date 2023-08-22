package com.java.normalsecurity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.mapping.List;

public class Demo {
    public static void main(String[] args) {
        System.out.println(customResponse(600,
                "Data Inserted Succesfully", "Not found"));
    }

    public static Map<String, Object> customResponse(Integer errorCode, String reponseMessage, Object errorMessage) {
        Map<String, Object> response = new HashMap<>();

        Map<String, Integer> headers = new HashMap<>();
        headers.put("code", errorCode);

        java.util.List<Object> responses = new ArrayList<>();
        Map<String, java.util.List<Object>> body = new HashMap<>();
        body.put("value", responses.add(reponseMessage));
        body.put("error", );

        response.put("header", headers);
        response.put("body", body);
        return response;

    }

}
