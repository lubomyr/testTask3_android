package com.testtaskapp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {

    static Gson prettyGson;
    static Gson gson;

    public static Gson getGson()
    {
        return getGson( true );
    }

    private static Gson getGson(boolean pretty) {
        if( pretty ) {
            if( prettyGson == null ) {
                synchronized(GsonUtils.class) {
                    GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
                    prettyGson = builder.create();
                }
            }
            return prettyGson;
        } else {
            if( gson == null ) {
                synchronized(GsonUtils.class) {
                    gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                }
            }
            return gson;
        }
    }
}
