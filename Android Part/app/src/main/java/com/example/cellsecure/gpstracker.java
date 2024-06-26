package com.example.cellsecure;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class gpstracker extends Service implements LocationListener {



    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    @Override
    public void onCreate() {
        super.onCreate();

//        Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_LONG).show();
           hnd = new Handler();
            getLocation();
            hnd.post(rn);

    }

    Handler hnd;
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

//        Toast.makeText(getApplicationContext(),"hi two",Toast.LENGTH_LONG).show();;
        hnd= new Handler();
        hnd.post(rn);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hnd.removeCallbacks(rn);
    }

    Runnable rn= new Runnable() {
        @Override
        public void run() {

            getLocation();

            hnd.postDelayed(rn,15000);
        }
    };
    public static String place="Kallanthode",lati="",longi="";

    public Location getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) getBaseContext(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

//                            Toast.makeText(getApplicationContext(),"in loc",Toast.LENGTH_LONG).show();

                            String loc = "";
                            String address = "";
                            Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                            try {
                                List<Address> addresses = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                if (addresses.size() > 0) {
                                    for (int index = 0; index < addresses.get(0).getMaxAddressLineIndex(); index++)
                                        address += addresses.get(0).getAddressLine(index) + " ";
                                    //Log.d("get loc...", address);

                                     place = addresses.get(0).getFeatureName().toString();

                                     serveroperation s= new serveroperation(getApplicationContext());
                                     s.location(place,longitude+"",latitude+"");
                                    s.settings();



                                    //	 loc= addresses.get(0).getLocality().toString();
//                                    Toast.makeText(getBaseContext(),longitude+"loc"+latitude , Toast.LENGTH_SHORT).show();
                                    //	Toast.makeText(getBaseContext(),ff , Toast.LENGTH_SHORT).show();
                                } else {
                                    //Toast.makeText(getBaseContext(), "noooooooo", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            lati= latitude+"";
                            longi=longitude+"";


//                            Toast.makeText(getApplicationContext(),"aaaaaa"+ latitude+"--"+longitude+"--"+ place,Toast.LENGTH_LONG).show();
                        }
                    }
                }

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
//                                Toast.makeText(getApplicationContext(),"in loc2",Toast.LENGTH_LONG).show();


                                String loc = "";
                                String address = "";
                                Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                                try {
                                    List<Address> addresses = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    if (addresses.size() > 0) {
                                        for (int index = 0; index < addresses.get(0).getMaxAddressLineIndex(); index++)
                                            address += addresses.get(0).getAddressLine(index) + " ";
                                        //Log.d("get loc...", address);

                                        place = addresses.get(0).getFeatureName().toString();

                                        lati= latitude+"";
                                        longi=longitude+"";
                                        serveroperation s= new serveroperation(getApplicationContext());
                                        s.location(place,longitude+"",latitude+"");



                                        //	 loc= addresses.get(0).getLocality().toString();
//                                        Toast.makeText(getBaseContext(),place , Toast.LENGTH_SHORT).show();
//                                        	Toast.makeText(getBaseContext(),ff , Toast.LENGTH_SHORT).show();
                                    } else {
                                        //Toast.makeText(getBaseContext(), "noooooooo", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


//                                Toast.makeText(getApplicationContext(),"aaaaaa"+ latitude+"--"+longitude+"--"+place,Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */

    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(gpstracker.this);
        }
    }

    /**
     * Function to get latitude
     * */

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}