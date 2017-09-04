package com.nsy209.nicedriver.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.nsy209.nicedriver.model.AppDatabase;
import com.nsy209.nicedriver.model.objects.BodyPointsAndSignal;
import com.nsy209.nicedriver.model.objects.PointCalcul;
import com.nsy209.nicedriver.model.objects.Signal;
import com.nsy209.nicedriver.services.ApiSingleton;
import com.nsy209.nicedriver.ui.adapters.ListPathAdapter;
import com.xee.api.entity.Location;
import com.xee.api.entity.Trip;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sébastien on 31/08/2017.
 */

public class UpdateDataXeeTask extends AsyncTask<Void, Void, Boolean> {

    private final ListPathAdapter mAdapter;
    private ProgressDialog mDialog;
    private final Context mContext;

    public UpdateDataXeeTask(Context context, ListPathAdapter adapter) {
        mContext = context;
        mAdapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = ProgressDialog.show(mContext, "",
                "Loading. Please wait...", true);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            final Call<List<Location>> call = ApiSingleton.getNiceDriverInstance().getLocations();
            AppDatabase.getAppDatabase(mContext).locationDao()
                    .insertAll(com.nsy209.nicedriver.model.objects.Location.convertFromXee(call.execute().body()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            final Call<List<Trip>> call = ApiSingleton.getNiceDriverInstance().getTrips();
            AppDatabase.getAppDatabase(mContext).tripDao()
                    .insertAll(com.nsy209.nicedriver.model.objects.Trip.convertFromXee(call.execute().body()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            List<Signal> listTem = ApiSingleton.getNiceDriverInstance().getSignals()
                    .execute().body();
            Log.d("ListTemp",
                    "ListTemp : " + listTem + (listTem == null ? 0 : listTem.size()));
            AppDatabase.getAppDatabase(mContext).signalDao()
                    .insertAll(listTem);
            Log.d("InsertedInDb", "Signals : " + AppDatabase.getAppDatabase(mContext).signalDao().getAll());
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Call<List<PointCalcul>> call = ApiSingleton.getNiceDriverInstance().getCalculatedPoints(
                new BodyPointsAndSignal(AppDatabase.getAppDatabase(mContext).locationDao().getAll(),
                        AppDatabase.getAppDatabase(mContext).signalDao().getAll()));

        call.enqueue(new Callback<List<PointCalcul>>() {
            @Override
            public void onResponse(Call<List<PointCalcul>> call, Response<List<PointCalcul>> response) {
                Log.d("RestCALL", "ok");
                try {
                    List<PointCalcul> points = response.body();
                    AppDatabase.getAppDatabase(mContext).pointCalculDao().insertAll(points);
                    Log.d("RestCALL", "Points : " + points.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<PointCalcul>> call, Throwable t) {
                Log.d("RestCALL", "fail");
            }
        });

        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        new UpdateAdapterWithPathTask(mContext, mAdapter).execute();
        mDialog.dismiss();
    }
}
