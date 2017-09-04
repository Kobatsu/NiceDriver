package com.nsy209.nicedriver.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.Environment;

import com.nsy209.nicedriver.model.dao.LocationDao;
import com.nsy209.nicedriver.model.dao.PointCalculDao;
import com.nsy209.nicedriver.model.dao.SignalDao;
import com.nsy209.nicedriver.model.dao.TripDao;
import com.nsy209.nicedriver.model.objects.Car;
import com.nsy209.nicedriver.model.objects.Location;
import com.nsy209.nicedriver.model.objects.Marker;
import com.nsy209.nicedriver.model.objects.PointCalcul;
import com.nsy209.nicedriver.model.objects.Signal;
import com.nsy209.nicedriver.model.objects.Trip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by Sébastien on 21/07/2017.
 */
@Database(entities = {Car.class, Marker.class, Trip.class, Location.class,
        Signal.class, PointCalcul.class}, version = 2)
@android.arch.persistence.room.TypeConverters({TypeConverters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "nicedriver-database";
    private static final String TAG = AppDatabase.class.getName();
    private static AppDatabase INSTANCE;

    public abstract TripDao tripDao();

    public abstract SignalDao signalDao();

    public abstract LocationDao locationDao();

    public abstract PointCalculDao pointCalculDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return INSTANCE;
    }


    public static void exportDatabase(Context context, String name) {
        try {
            File sd = Environment.getExternalStorageDirectory();

            if (sd.canWrite()) {
                String currentDBPath = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();
                String backupDBPath = name;
                //previous wrong  code
                // **File currentDB = new File(data,currentDBPath);**
                // correct code
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}