package com.nsy209.nicedriver.utils;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by Sébastien on 20/08/2017.
 */

public class GeoUtils {

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <p>
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @returns Distance in Meters
     */
    public static double distanceInMeters(LatLng point1, double alt1, LatLng point2, double alt2) {

        final int R = 6371; // Radius of the earth

        double lat1 = point1.latitude;
        double lat2 = point2.latitude;
        double lon1 = point1.longitude;
        double lon2 = point2.longitude;
        double el1 = alt1;
        double el2 = alt2;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    public static long differenceInTime(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        } else {
            return endDate.getTime() - startDate.getTime();
        }
    }

    public static String printAddress(Address address) {
        if (address == null) {
            return "";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(" ");
                }
                stringBuilder.append(address.getAddressLine(i));
            }
            return stringBuilder.toString();
        }
    }

    public static String printDistance(double distanceInMeters) {
        if (distanceInMeters == 0) {
            return "";
        } else {
            DecimalFormat df = new DecimalFormat("#0.000");
            if (distanceInMeters > 1000) {
                return df.format(distanceInMeters / 1000) + " km";
            } else {
                return df.format(distanceInMeters) + " m";
            }
        }
    }
}
