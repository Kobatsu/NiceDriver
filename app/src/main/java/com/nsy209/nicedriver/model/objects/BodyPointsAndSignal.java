package com.nsy209.nicedriver.model.objects;

import java.util.List;

/**
 * Created by Sébastien on 17/08/2017.
 */

public class BodyPointsAndSignal {
    private List<Location> mLocations;
    private List<Signal> mSignals;

    public BodyPointsAndSignal(List<Location> locations, List<Signal> signals) {
        mLocations = locations;
        mSignals = signals;
    }

    public List<Location> getLocations() {
        return mLocations;
    }

    public void setLocations(List<Location> locations) {
        mLocations = locations;
    }

    public List<Signal> getSignals() {
        return mSignals;
    }

    public void setSignals(List<Signal> signals) {
        mSignals = signals;
    }

    @Override
    public String toString() {
        return "BodyPointsAndSignal{" +
                "mLocations=" + mLocations +
                ", mSignals=" + mSignals +
                '}';
    }
}
