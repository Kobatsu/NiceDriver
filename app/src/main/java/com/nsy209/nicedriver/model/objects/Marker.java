package com.nsy209.nicedriver.model.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Sébastien on 21/07/2017.
 *
 * Check https://stackoverflow.com/questions/34588905/how-to-load-image-through-byte-array-using-glide
 */
@Entity(tableName = "Markers")
public class Marker {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "image")
    private byte[] mImage;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public byte[] getImage() {
        return mImage;
    }

    public void setImage(byte[] image) {
        mImage = image;
    }
}
