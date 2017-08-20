package com.nsy209.nicedriver.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sébastien on 20/08/2017.
 */

public class DateUtils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static String formatDate(Date d) {
        if (d.getTime() == 0) {
            return "";
        } else {
            return sdf.format(d);
        }
    }

    public static String formatDifference(long time) {
        int seconds = (int) (time / 1000) % 60;
        int minutes = (int) ((time / (1000 * 60)) % 60);
        int hours = (int) ((time / (1000 * 60 * 60)) % 24);
        DecimalFormat df = new DecimalFormat("#00");

        StringBuilder stringBuilder = new StringBuilder();
        if (hours > 0) {
            stringBuilder.append(df.format(hours) + ":");
        }
        if (minutes > 0) {
            stringBuilder.append(df.format(minutes) + ":");
        }
        if (seconds > 0) {
            stringBuilder.append(df.format(seconds));
        }
        return stringBuilder.toString();
    }
}
