package com.pad.dto.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonConverter {

    public static <T> String convertToJson(T object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        return gson.toJson(object);
    }

    public static <T> T convertToDto(String gson, Class<T> classType) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gsonToObj = new Gson();
        return gsonToObj.fromJson(gson, classType);
    }
}
