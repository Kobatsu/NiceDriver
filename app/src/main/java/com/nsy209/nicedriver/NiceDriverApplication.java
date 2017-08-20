package com.nsy209.nicedriver;

import android.app.Application;

import com.nsy209.nicedriver.services.ApiSingleton;

/**
 * Created by Sébastien on 18/08/2017.
 */

public class NiceDriverApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiSingleton.initXeeApi(getApplicationContext());
    }
}
