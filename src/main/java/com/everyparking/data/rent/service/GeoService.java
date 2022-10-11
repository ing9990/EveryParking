package com.everyparking.data.rent.service;

import org.springframework.stereotype.Component;

/**
 * @author Taewoo
 */


@Component
public class GeoService {

    /**
     * @param lat1  p1의 위도
     * @param lon1  p1의 경도
     *
     * @param lat2  p2의 위도
     * @param lon2  p2의 경도
     * @return distance
     */
    public double dec(double lat1, double lon1, double lat2, double lon2) {

        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                        * Math.cos(deg2rad(lon1 - lon2));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        return dist * 1.609344;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
