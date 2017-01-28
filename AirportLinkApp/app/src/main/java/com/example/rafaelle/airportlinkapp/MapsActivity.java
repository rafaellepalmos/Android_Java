package com.example.rafaelle.airportlinkapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = "rafaelle";//for debugging purposes

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private final Context context;

    boolean GPSEnabled = false; //to check if GPS is enabled
    boolean networkEnabled = false; //to check if network is enabled
    boolean canGetLocation = false; //to check if location can be retrieved

    Location location;

    double mylatitude;
    double mylongitude;

    private static final long MINIMUM_DISTANCE = 10;
    private static final long MINIMUM_TIME = 1000 * 60 * 1;

    protected LocationManager locationManager;

    private static final double HUAMAK_LAT = 13.737931;
    private static final double HUAMAK_LONG = 100.645313;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public MapsActivity(Context context) {
        this.context = context;
        getLocation();
    }

    public Location getLocation(){
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER); //returning boolean value
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER); //returning boolean value

            //
            if (GPSEnabled && networkEnabled){

            }
            else {
                this.canGetLocation = true;

                if (networkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MINIMUM_TIME, MINIMUM_DISTANCE, this);

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {
                            mylatitude = location.getLatitude();
                            mylongitude = location.getLongitude();
                        }
                    }
                }

                if (GPSEnabled){

                    if (location == null){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME, MINIMUM_DISTANCE, this);

                        if (locationManager != null){
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (location != null){
                                mylatitude = location.getLatitude();
                                mylongitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return location;
    }

    //stop GPS usage
    public void stopGPS(){
        if (locationManager != null){
            locationManager.removeUpdates(MapsActivity.this);
        }
    }

    //get method for myLatitude
    public double getMyLatitude(){
        if (location != null){
            mylatitude = location.getLatitude();
        }
        return mylatitude;
    }

    //get method for myLongitude
    public double getMyLongitude(){
        if (location != null) {
            mylongitude = location.getLongitude();
        }
        return mylongitude;
    }

    //returns the value of whether we can get the location or not
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);//make an alert window
        alertDialog.setTitle(R.string.GPS_Settings);//title for alert
        alertDialog.setMessage(R.string.GPS_Message);//message for alert
        alertDialog.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);//go to settings
                context.startActivity(intent);//execute intent
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();//close the alert window
            }
        });

        alertDialog.show();//show alert window
    }

    public void getClosestStation(){

    }

    //to connect to the API
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)//declare this as variable of GoogleApiClient
                .addConnectionCallbacks(this)//this will ask to implement ConnectionCallbacks
                .addOnConnectionFailedListener(this)//this will ask to implement onConnectionFailedListener
                .addApi(LocationServices.API)
                .build();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(TAG,"onMapReady");
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);//to get current location

        if (canGetLocation == true){
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(getMyLatitude(), getMyLongitude()))
                    .title("You are here"));
        }

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    //method generated by ConnectionCallbacks
    @Override
    public void onConnected(Bundle bundle) {

    }

    //method generated by ConnectionCallbacks
    @Override
    public void onConnectionSuspended(int i) {

    }

    //Method generated by onConnectionFailedListener
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    //Method generated by LocationListener
    @Override
    public void onLocationChanged(Location location) {

    }

    //Method generated by LocationListener
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    //Method generated by LocationListener
    @Override
    public void onProviderEnabled(String provider) {

    }

    //Method generated by LocationListener
    @Override
    public void onProviderDisabled(String provider) {

    }
}
