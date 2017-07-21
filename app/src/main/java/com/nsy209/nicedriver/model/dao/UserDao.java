package com.nsy209.nicedriver.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

import com.nsy209.nicedriver.model.objects.User;

/**
 * Created by Sébastien on 21/07/2017.
 */

@Dao
public interface UserDao {

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
