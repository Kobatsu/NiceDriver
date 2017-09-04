package com.nsy209.nicedriver.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nsy209.nicedriver.model.objects.PointCalcul;

import java.util.List;

/**
 * Created by Sébastien on 04/09/2017.
 */
@Dao
public interface PointCalculDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<PointCalcul> trips);

    @Query("SELECT * FROM PointsCalcul")
    public List<PointCalcul> getAll();

    @Query("SELECT * FROM PointsCalcul WHERE type = :name")
    List<PointCalcul> getPoints(String name);
}
