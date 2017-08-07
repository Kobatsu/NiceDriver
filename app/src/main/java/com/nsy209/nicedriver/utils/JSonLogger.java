package com.nsy209.nicedriver.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Print a pretty json formatted
 */
public class JSonLogger {
    private static final int JSON_INDENT = 4;

    /**
     * Get a json to format
     * @param message the json to format
     * @return the pretty json
     */
    public static String getPrettyLog(String message) {
        String json;
        try {
            if (message.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(message);
                json = jsonObject.toString(JSON_INDENT);
            } else if (message.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(message);
                json = jsonArray.toString(JSON_INDENT);
            } else {
                json = message;
            }
        } catch (JSONException e) {
            json = message;
        }

        return json;
    }
}