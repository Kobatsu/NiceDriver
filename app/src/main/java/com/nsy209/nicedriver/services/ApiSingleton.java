package com.nsy209.nicedriver.services;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.xee.api.Xee;
import com.xee.core.XeeEnv;
import com.xee.core.entity.OAuth2Client;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    private static Xee xeeInstance;

    public static NiceDriverService getNiceDriverInstance() {
        if (sInstance == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS);

            // add your other interceptors …

            // add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create()))
                    .client(httpClient.build())
                    .build();

            sInstance = retrofit.create(NiceDriverService.class);
        }
        return sInstance;
    }


    public static Xee getXeeApi() {
        if (xeeInstance != null) {
            return xeeInstance;
        } else {
            throw new RuntimeException("Call initXeeApi() first");
        }
    }

    public static void initXeeApi(Context context) {
        XeeEnv xeeEnv = new XeeEnv(context,
                new OAuth2Client("tpziqAm4itmcCUlc6azs",
                        "TH2tFMi0KTXSyBUBGvJ9",
                        "http://localhost"),
                60, 60, XeeEnv.SANDBOX);
        xeeInstance = new Xee(xeeEnv);
    }
}
