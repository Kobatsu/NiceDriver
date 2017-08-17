package com.nsy209.nicedriver.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sébastien on 17/08/2017.
 */

public class ApiSingleton {

    private static NiceDriverService sInstance;

    public static NiceDriverService getNiceDriverInstance() {
        if (sInstance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://vps001.matigames.com:888/NiceDriver_Serveur/rest/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            sInstance = retrofit.create(NiceDriverService.class);
        }
        return sInstance;
    }
}
