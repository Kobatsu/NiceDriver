package com.nsy209.nicedriver.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nsy209.nicedriver.model.objects.Signal;

import java.util.List;

/**
 * Created by Sébastien on 16/08/2017.
 */
@Dao
public interface SignalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Signal> trips);


    @Query("SELECT * FROM Signals")
    public List<Signal> getAll();
}
