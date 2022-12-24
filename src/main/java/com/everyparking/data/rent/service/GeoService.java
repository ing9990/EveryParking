package com.everyparking.data.rent.service;

import com.everyparking.data.rent.domain.Rent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class GeoService {

    private double dec(double lat1, double lon1, double lat2, double lon2) {

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

    /**
     * @param availableLots 대여 가능한 Rent 리스트
     * @param meX           나의 위도
     * @param meY           나의 경도
     * @return 나와 Rent의 place 사이의 거리를 리스트로 담아서 반환.
     */

    public List<Double> getDistance(List<Rent> availableLots, double meX, double meY) {
        List<Double> adj = new ArrayList<>();

        availableLots.forEach(item -> {
            var place = item.getPlace();

            double mapX = Double.parseDouble(place.getMapX());
            double mapY = Double.parseDouble(place.getMapY());

            adj.add(dec(meY, meX, mapY, mapX));
        });

        return adj;
    }
}
