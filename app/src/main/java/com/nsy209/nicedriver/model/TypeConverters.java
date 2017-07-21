package com.nsy209.nicedriver.model;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Sébastien on 21/07/2017.
 */

public class TypeConverters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}