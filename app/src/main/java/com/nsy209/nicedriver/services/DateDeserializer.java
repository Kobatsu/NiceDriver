package com.nsy209.nicedriver.services;

/**
 * Created by Sébastien on 05/09/2017.
 */

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;


public class DateDeserializer implements JsonDeserializer<Date> {
    public static final String[] DATE_FORMATS = new String[]{
            "MMM dd, yyyy HH:mm:ss",
            "MMM dd, yyyy",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            "yyyy-MM-dd HH:mm:ss.SSSSSS"
    };

    @Override
    public Date deserialize(JsonElement jsonElement, Type typeOF,
                            JsonDeserializationContext context) throws JsonParseException {
        for (String format : DATE_FORMATS) {
            try {
                return new SimpleDateFormat(format, Locale.US).parse(jsonElement.getAsString());
            } catch (ParseException e) {
            }
        }
        throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
                + "\". Supported formats: " + Arrays.toString(DATE_FORMATS));
    }
}