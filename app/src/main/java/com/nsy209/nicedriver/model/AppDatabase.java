package com.nsy209.nicedriver.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by Sébastien on 21/07/2017.
 */

@Database(entities = {Car.class, Marker.class, Point.class, Referentiel.class, Trip.class, User.class}, version = 1)
@android.arch.persistence.room.TypeConverters({TypeConverters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "nicedriver-database";
    private static final String TAG = AppDatabase.class.getName();
    private static AppDatabase INSTANCE;

    public abstract MarkerDao markerDao();

    public abstract PointDao pointDao();

    public abstract ReferentielDao referentielDao();

    public abstract UserDao userDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME).build();
        }
        return INSTANCE;
    }

    public static boolean exportDatabase(Context context) {
        try {
            File sd = Environment.getExternalStorageDirectory();
//            File data = context.getDatabasePath(DATABASE_NAME);

            if (sd.canWrite()) {
//                String currentDBPath = "//data//" + "com.nsy209.nicedriver"
//                        + "//databases//" + DATABASE_NAME;
                String backupDBPath = DATABASE_NAME + ".db";
//                File currentDB = new File(data, currentDBPath);
                File currentDB = context.getDatabasePath(DATABASE_NAME);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Log.d(TAG, "Export database success");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}