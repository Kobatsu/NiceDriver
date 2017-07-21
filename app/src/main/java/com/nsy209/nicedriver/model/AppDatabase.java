package com.nsy209.nicedriver.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.nsy209.nicedriver.model.dao.MarkerDao;
import com.nsy209.nicedriver.model.dao.PointDao;
import com.nsy209.nicedriver.model.dao.ReferentielDao;
import com.nsy209.nicedriver.model.dao.UserDao;
import com.nsy209.nicedriver.model.objects.Car;
import com.nsy209.nicedriver.model.objects.Marker;
import com.nsy209.nicedriver.model.objects.Point;
import com.nsy209.nicedriver.model.objects.Referentiel;
import com.nsy209.nicedriver.model.objects.Trip;
import com.nsy209.nicedriver.model.objects.User;

/**
 * Created by Sébastien on 21/07/2017.
 */

@Database(entities = {Car.class, Marker.class, Point.class, Referentiel.class, Trip.class, User.class}, version = 1)
@android.arch.persistence.room.TypeConverters({TypeConverters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract MarkerDao markerDao();

    public abstract PointDao pointDao();

    public abstract ReferentielDao referentielDao();

    public abstract UserDao userDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "nicedriver-database").build();
        }
        return INSTANCE;
    }
}