package com.nsy209.nicedriver.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.nsy209.nicedriver.model.objects.Location;

import java.util.List;

/**
 * Created by Sébastien on 16/08/2017.
 */
@Dao
public interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Location> trips);
}
