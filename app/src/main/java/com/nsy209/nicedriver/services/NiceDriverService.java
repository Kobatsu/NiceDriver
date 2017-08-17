package com.nsy209.nicedriver.services;

import com.nsy209.nicedriver.model.objects.BodyPointsAndSignal;
import com.nsy209.nicedriver.model.objects.PointCalcul;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Sébastien on 17/08/2017.
 */

public interface NiceDriverService {

    @POST("points")
    Call<List<PointCalcul>> getCalculatedPoints(@Body BodyPointsAndSignal user);
}
