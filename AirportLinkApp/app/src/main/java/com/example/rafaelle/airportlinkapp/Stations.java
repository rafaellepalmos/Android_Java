package com.example.rafaelle.airportlinkapp;

import android.location.Location;

/**
 * Created by Rafaelle on 19-Oct-15.
 */
public class Stations {

    private static final double PHAYATHAI_LAT = 13.756779;
    private static final double PHAYATHAI_LONG = 100.535236;
    private static final double HUAMAK_LAT = 13.737931;
    private static final double HUAMAK_LONG = 100.645313;

    double lat;
    double lng;

    Location mylocation;

    //constructor
    public Stations(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;

        mylocation.setLatitude(lat);
        mylocation.setLongitude(lng);
    }

    public String closestStation(){
        double distanceA;
        double distanceB;
        double minDistance;
        String closest;

        Location phayathai = new Location("");
        phayathai.setLatitude(PHAYATHAI_LAT);
        phayathai.setLongitude(PHAYATHAI_LONG);

        Location huamak = new Location("");
        huamak.setLatitude(HUAMAK_LAT);
        huamak.setLongitude(HUAMAK_LONG);

        distanceA = mylocation.distanceTo(huamak);//get distance to Hua Mak to compare with other stations
        distanceB = mylocation.distanceTo(phayathai);

        if (distanceA > distanceB){
            minDistance = distanceB;
            closest = "Phaya Thai";
        }
        else{
            minDistance = distanceA;
            closest = "Hua Mak";
        }

        return closest;

    }
}
