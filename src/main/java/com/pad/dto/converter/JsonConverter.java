package com.pad.dto.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonConverter {

    public static <T> String convertToJson(T object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

    public static <T> T convertToDto(String json, Class<T> classType) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = new Gson();
        return gson.fromJson(json, classType);
    }
}
