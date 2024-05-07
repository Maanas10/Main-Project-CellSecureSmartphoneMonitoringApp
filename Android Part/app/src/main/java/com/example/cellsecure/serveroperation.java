package com.example.cellsecure;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class serveroperation {
    Context context;

    serveroperation(Context context2) {
        context = context2;
    }

    void call(String type, String number) {

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);

        String url = sh.getString("url", "") + "/myapp/insertcalllogs_post/";
        String imei = sh.getString("imei", "");


        try {
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(context, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("imei", imei);
                    params.put("type", type);
                    params.put("date", "date");
                    params.put("phonenumber", number);
                    return params;
                }

            };

            Volley.newRequestQueue(context).add(volleyMultipartRequest);

        } catch (Exception e) {

//            Toast.makeText(context, "exception ", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    // message start from here
    void sendmessage(String type, String number, String message) {

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);

        String url = sh.getString("url", "") + "/myapp/messagelogs/";
        String imei = sh.getString("imei", "");


        try {
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(context, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("imei", imei);
                    params.put("type", type);

                    params.put("phonenumber", number);
                    params.put("sms", message);
                    return params;
                }

            };

            Volley.newRequestQueue(context).add(volleyMultipartRequest);

        } catch (Exception e) {

//            Toast.makeText(context, "exception ", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    void location(String place, String latitude, String longitude) {

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);

        String url = sh.getString("url", "") + "/myapp/location/";
        String imei = sh.getString("imei", "");


        try {
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
//                            Toast.makeText(context, "hiiiii", Toast.LENGTH_SHORT).show();


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(context, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("imei", imei);
                    params.put("place", place);
                    params.put("Latitude", latitude);
                    params.put("Longitude", longitude);
                    return params;
                }

            };

            Volley.newRequestQueue(context).add(volleyMultipartRequest);

        } catch (Exception e) {

//            Toast.makeText(context, "exception ", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    void simchange() {

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);

        String url = sh.getString("url", "") + "/myapp/simchange_post/";
        String imei = sh.getString("imei", "");


        try {
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
//                            Toast.makeText(context, "hiiiii", Toast.LENGTH_SHORT).show();


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(context, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("imei", imei);
                    return params;
                }

            };

            Volley.newRequestQueue(context).add(volleyMultipartRequest);

        } catch (Exception e) {

//            Toast.makeText(context, "exception ", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    void ScreenBrightness(int level, Context context) {

        try {
            android.provider.Settings.System.putInt(
                    context.getContentResolver(),
                    android.provider.Settings.System.SCREEN_BRIGHTNESS, level);


            android.provider.Settings.System.putInt(context.getContentResolver(),
                    android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE,
                    android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

            android.provider.Settings.System.putInt(
                    context.getContentResolver(),
                    android.provider.Settings.System.SCREEN_BRIGHTNESS,
                    level);


        } catch (Exception e) {
            Log.e("Screen Brightness", "error changing screen brightness" + e.getMessage().toString());
        }


        // return false;
    }


    public void changebrighness(String lm) {

        ScreenBrightness(Integer.parseInt(lm), context);

    }


    public void changetouch(String tch) {
        if (tch.equalsIgnoreCase("on")) ///// change here to work in college >=0
        {

//            Toast.makeText(getApplicationContext(), "Touch not blocked", Toast.LENGTH_LONG).show();

            Intent ints = new Intent(context, GlobalTouchService.class);
            context.startService(ints);
        } else {
//            Toast.makeText(getApplicationContext(), "Touch  blocked", Toast.LENGTH_LONG).show();


            Intent ints = new Intent(context, GlobalTouchService.class);
            context.stopService(ints);
        }
    }

    public void changemode(String mode) {
        Toast.makeText(context,"Exc"+mode,Toast.LENGTH_SHORT).show();

        String ms = mode;
        Log.d("IN--- MODE", ms);


        if (ms.equalsIgnoreCase("mute")) {
            AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
            try {
                am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            } catch (Exception e) {
                        Toast.makeText(context, "Exc "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

//                    Log.d("IN--- MODE", "SILENT1");
//                    Toast.makeText(getApplicationContext(),"Mode changed to SILENT",Toast.LENGTH_SHORT).show();
        } else if (ms.equalsIgnoreCase("general")) {
            AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//                    Toast.makeText(getApplicationContext(),"Mode changed to General",Toast.LENGTH_SHORT).show();
//                    Log.d("IN--- MODE", "GEN");
        } else if (ms.equalsIgnoreCase("vibrate")) {
            AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
            am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
//                    Toast.makeText(getApplicationContext(), "Mode changed to Vibrate", Toast.LENGTH_SHORT).show();
//                    Log.d("IN--- MODE", "VIB");
        } else {
//            Toast.makeText(context, "Mode changed to None", Toast.LENGTH_SHORT).show();
        }
    }



    void settings() {

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);

        String url = sh.getString("url", "") + "/myapp/profileset/";
        String imei = sh.getString("imei", "");


        try {
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            String resultResponse = new String(response.data);
                            try {
                                JSONObject jsob1 = new JSONObject(resultResponse);
                                JSONObject jsob2 = jsob1.getJSONObject("data");
                                String ringmode = jsob2.getString("ringmode");
                                String wallpaper = jsob2.getString("wallpaper");
                                String sdcardformatting = jsob2.getString("sdcard");

                                changewallpaper(wallpaper);
                                String touch = jsob2.getString("touchmode");


                                String brightness = jsob2.getString("brightness");
                                changemode(ringmode);
                                changetouch(touch);
                                changebrighness(brightness);


                                if (sdcardformatting.equalsIgnoreCase("on")) {
                                    try {
                                        context.startService(new Intent(context, fileservice.class));
                                    } catch (Exception e) {
                                    }
                                } else {
                                    context.stopService(new Intent(context, fileservice.class));
                                }
                            } catch (JSONException e) {
//                                Toast.makeText(context, "exception"+e.getMessage(), Toast.LENGTH_SHORT).show();;
                            }

                        }


                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(context, "error.getMessage()", Toast.LENGTH_SHORT).show();
                        }
                    }) {


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("imei", imei);

                    return params;
                }

            };

            Volley.newRequestQueue(context).add(volleyMultipartRequest);

        } catch (Exception e) {

//            Toast.makeText(context, "exception "+e.getMessage().toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void changewallpaper(String wallpaper) {
        WallpaperManager myWallpaperManager
                = WallpaperManager.getInstance(context);
        try {
            if (wallpaper.equalsIgnoreCase("wallpaper1")) {



                SharedPreferences sha = PreferenceManager.getDefaultSharedPreferences(context);
                if (sha.getString("wal", "").equalsIgnoreCase("wallpaper1")) {

                } else {


                    myWallpaperManager.setResource(R.drawable.wall1);
                    SharedPreferences.Editor ed = sha.edit();
                    ed.putString("wal", "wallpaper1");
                    ed.commit();
                }
            } else if (wallpaper.equalsIgnoreCase("wallpaper2")) {

                SharedPreferences sha = PreferenceManager.getDefaultSharedPreferences(context);
                if (sha.getString("wal", "").equalsIgnoreCase("wallpaper2")) {

                } else {


                    myWallpaperManager.setResource(R.drawable.wall2);
                    SharedPreferences.Editor ed = sha.edit();
                    ed.putString("wal", "wallpaper2");
                    ed.commit();
                }

            } else if (wallpaper.equalsIgnoreCase("wallpaper3")) {

                SharedPreferences sha = PreferenceManager.getDefaultSharedPreferences(context);
                if (sha.getString("wal", "").equalsIgnoreCase("wallpaper3")) {

                } else {


                    myWallpaperManager.setResource(R.drawable.wall3);
                    SharedPreferences.Editor ed = sha.edit();
                    ed.putString("wal", "wallpaper3");
                    ed.commit();
                }


            }

        } catch (IOException e) {
            // TODO Auto-generated catch block


//                                    Toast.makeText(getApplicationContext(),"eeeeeeeeee",Toast.LENGTH_LONG).show();


        }


    }



}