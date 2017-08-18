package com.nsy209.nicedriver.services;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sébastien on 17/08/2017.
 */

public class ApiSingleton {

    private static final String API_BASE_URL = "http://vps001.matigames.com:888/NiceDriver_Serveur/rest/";
    private static NiceDriverService sInstance;

    public static NiceDriverService getNiceDriverInstance() {
        if (sInstance == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // add your other interceptors …

            // add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            sInstance = retrofit.create(NiceDriverService.class);
        }
        return sInstance;
    }
}
