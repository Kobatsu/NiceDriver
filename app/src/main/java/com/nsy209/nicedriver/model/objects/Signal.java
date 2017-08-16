package com.nsy209.nicedriver.model.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sébastien on 16/08/2017.
 */
@Entity(tableName = "Signals", primaryKeys = {"date", "value", "name"})
public class Signal {
    @ColumnInfo(name = "date")
    private Date mDate;
    @ColumnInfo(name = "value")
    private double mValue;
    @ColumnInfo(name = "name")
    private String mName;

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public double getValue() {
        return mValue;
    }

    public void setValue(double value) {
        mValue = value;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public static List<Signal> convertFromXee(List<com.xee.api.entity.Signal> items) {
        List<Signal> list = new ArrayList<>();
        for (com.xee.api.entity.Signal s : items) {
            Signal signal = new Signal();
            signal.setDate(s.getDate());
            signal.setValue(Double.parseDouble(s.getValue().toString()));
            signal.setName(s.getName());
            list.add(signal);
        }
        return list;
    }
}
