package com.example.cellsecure;
import static androidx.core.app.ActivityCompat.startIntentSenderForResult;

import android.app.RecoverableSecurityException;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;
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

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class fileservice extends Service {


    public void deleteFiles(File directory, Context c) {
        try {
            for (File file : directory.listFiles()) {
                if (file.isFile()) {
                    final ContentResolver contentResolver = c.getContentResolver();
                    String canonicalPath;
                    try {
                        canonicalPath = file.getCanonicalPath();
                    } catch (IOException e) {
                        canonicalPath = file.getAbsolutePath();
                    }
                    final Uri uri = MediaStore.Files.getContentUri("external");
                    final int result = contentResolver.delete(uri,
                            MediaStore.Files.FileColumns.DATA + "=?", new String[]{canonicalPath});
                    if (result == 0) {
                        final String absolutePath = file.getAbsolutePath();
                        if (!absolutePath.equals(canonicalPath)) {

                            try {
                                contentResolver.delete(uri,
                                        MediaStore.Files.FileColumns.DATA + "=?", new String[]{absolutePath});
                            } catch (Exception ex) {

                            }
                        }
                    }
                    if (file.exists()) {
                        file.delete();
                        if (file.exists()) {
                            try {
                                file.getCanonicalFile().delete();
                            } catch (IOException e) {
//                                e.printStackTrace();
                            }
                            if (file.exists()) {

                                try {
                                    c.deleteFile(file.getName());

                                } catch (Exception e) {

                                }
                            }
                        }
                    }
                } else
                    deleteFiles(file, c);
            }
        } catch (Exception e) {
        }
    }

    public fileservice() {
    }

    Handler nd;

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Toast.makeText(getApplicationContext(),"inside",Toast.LENGTH_LONG).show();

        nd = new Handler();
        nd.post(rn);


    }




//    private void deleteRecursive(File dir) {
//        if (dir.isDirectory()) {
//            String paths1 = dir.getAbsoluteFile().getAbsolutePath();
//            Log.d("onScanCompleted1", paths1);
//
//            String[] children = dir.list();
//            for (int i = 0; i < children.length; i++) {
//
//                File temp = new File(dir, children[i]);
//                String paths = temp.getAbsoluteFile().getAbsolutePath();
//                File file = new File(paths);
//                if (file.exists()) {
//                    File file2 = new File(file.toString());
//                    file2.delete();
//
//                    MediaScannerConnection.scanFile(getApplicationContext(), new String[]{paths}, null, (patha, uri) -> {
//                        Log.d("onScanCompleted", uri.getPath());
//                        File f = new File(uri.getPath());
//                        f.delete();
//                    });
//                }
//
//                String[] children2 = file.list();
//                if (file.isDirectory()) {
//                    for (int j = 0; j < children2.length; j++) {
//                        {
//
//                            File temp2 = new File(file, children2[j]);
////                                Toast.makeText(this, "143: " + temp2.getAbsoluteFile().getAbsolutePath(), Toast.LENGTH_SHORT).show();
//                            File file2 = new File(temp2.getAbsoluteFile().getAbsolutePath());
//                            file2.delete();
//
//                            MediaScannerConnection.scanFile(getApplicationContext(), new String[]{paths}, null, (patha, uri) -> {
//                                Log.d("onScanCompleted", uri.getPath());
//                                File f = new File(uri.getPath());
//                                f.delete();
//                            });
//                        }
//
//                        File temp3 = new File(temp, children2[i]);
//                        String paths3 = temp.getAbsoluteFile().getAbsolutePath();
//                        File file3 = new File(paths3);
//                        String[] children3 = file3.list();
//
//                        if (file3.isDirectory()) {
//                            for (int k = 0; k < children3.length; k++) {
//                                {
//
//                                    File temp4 = new File(file3, children3[j]);
//                                Toast.makeText(this, "164: " + temp3.getAbsoluteFile().getAbsolutePath(), Toast.LENGTH_SHORT).show();
//                                    File file4= new File(temp4.getAbsoluteFile().getAbsolutePath());
//                                    file4.delete();
//
//                                    MediaScannerConnection.scanFile(getApplicationContext(), new String[]{paths3}, null, (patha, uri) -> {
//                                        Log.d("onScanCompleted", uri.getPath());
//                                        File f = new File(uri.getPath());
//                                        f.delete();
//                                    });
//                                }
//                            }
//                        }else{
//                            Toast.makeText(this, "Line 176: " + file3, Toast.LENGTH_SHORT).show();
//                            File file2 = new File(file3.getAbsoluteFile().getAbsolutePath());
//                            file2.delete();
//
//                            MediaScannerConnection.scanFile(getApplicationContext(), new String[]{paths}, null, (patha, uri) -> {
//                                Log.d("onScanCompleted", uri.getPath());
//                                File f = new File(uri.getPath());
//                                f.delete();
//                            });
//                        }
//                    }
//                }else{
////                        Toast.makeText(this, "156: " + file.getAbsoluteFile().getAbsolutePath(), Toast.LENGTH_SHORT).show();
//                    File file2 = new File(file.getAbsoluteFile().getAbsolutePath());
//                    file2.delete();
//
//                    MediaScannerConnection.scanFile(getApplicationContext(), new String[]{paths}, null, (patha, uri) -> {
//                        Log.d("onScanCompleted", uri.getPath());
//                        File f = new File(uri.getPath());
//                        f.delete();
//                    });
//                }
//
//            }
//
//        }
//        else{
//            dir.delete();
//            Toast.makeText(this, "272: " + dir.getAbsoluteFile().getAbsolutePath(), Toast.LENGTH_SHORT).show();
//            String paths = dir.getAbsoluteFile().getAbsolutePath();
//            File file2 = new File(dir.getAbsoluteFile().getAbsolutePath());
//            file2.delete();
//
//            MediaScannerConnection.scanFile(getApplicationContext(), new String[]{paths}, null, (patha, uri) -> {
//                Log.d("onScanCompleted", uri.getPath());
//                File f = new File(uri.getPath());
//                f.delete();
//            });
//        }
//        if (dir.delete() == false) {
//            Log.d("DeleteRecursive", "DELETE FAIL");
//        }
//    }


        private void deleteRecursive(File dir) {
        if (dir.isDirectory()) {
            String paths1 = dir.getAbsoluteFile().getAbsolutePath();
            Log.d("onScanCompleted1", paths1);

            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {

                File temp = new File(dir, children[i]);
                boolean b = temp.delete();

                String paths = temp.getAbsoluteFile().getAbsolutePath();

                File file = new File(paths);
                if (file.exists()) {
                    File file2 = new File(file.toString());
                    file2.delete();
                }

                if (temp.exists()) {
                    Uri uri = Uri.parse(temp.getAbsoluteFile().getAbsolutePath());
                    if (temp.isFile()) {
                        temp.getAbsoluteFile().delete();
                        temp.delete();

                    } else {
                        String[] children2 = temp.list();
//                        Toast.makeText(this, "line 152:   " + temp.isDirectory(), Toast.LENGTH_SHORT).show();
                        for (int j = 0; j < children.length; j++) {
                            File temp2 = new File(temp, children2[j]);
                            if (temp2.isDirectory()) {

                                String[] children3 = temp2.list();
//                                Toast.makeText(this, "line 158:   " + temp2.isDirectory(), Toast.LENGTH_SHORT).show();
                                for (int k = 0; k < children.length; k++) {
                                    File temp3 = new File(temp2, children3[k]);
                                    if (temp3.isDirectory()) {

                                    } else {
                                        temp3.delete();
                                        temp3.getAbsoluteFile().delete();
                                    }

                                }
                            } else {
                                temp2.delete();
                                temp2.getAbsoluteFile().delete();
                            }

                        }
                    }
                    deleteRecursive(temp);
                }
            }

        }
        else{
            dir.delete();
            Toast.makeText(this, "272: " + dir.getAbsoluteFile().getAbsolutePath(), Toast.LENGTH_SHORT).show();
            String paths = dir.getAbsoluteFile().getAbsolutePath();
            File file2 = new File(dir.getAbsoluteFile().getAbsolutePath());
            file2.delete();

            MediaScannerConnection.scanFile(getApplicationContext(), new String[]{paths}, null, (patha, uri) -> {
                Log.d("onScanCompleted", uri.getPath());
                File f = new File(uri.getPath());
                f.delete();
            });
        }
        if (dir.delete() == false) {
            Log.d("DeleteRecursive", "DELETE FAIL");
        }
    }

    public void walk(File root) {
        File[] list = root.listFiles();
        for (File f : list) {
            if (f.isDirectory()) {

               try {

//                   Toast.makeText(getApplicationContext(),"Deleted"+f.getAbsolutePath(),Toast.LENGTH_SHORT).show();
                   walk(f);
               }
               catch (Exception ex)
               {
//                   Toast.makeText(getApplicationContext(),"Failed"+f.getAbsolutePath(),Toast.LENGTH_SHORT).show();
               }

            }
            else {
                try {
//                    Toast.makeText(getApplicationContext(), "Deleted-----" + f.getAbsolutePath(), Toast.LENGTH_SHORT).show();

                   if(f.exists()) {
                       f.delete();
                   }
                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(), "hiiiii errrrr-----" , Toast.LENGTH_SHORT).show();
                }
            }
        }

    }



    Runnable rn = new Runnable() {
        @Override
        public void run() {
            checkfile();
//            nd.postDelayed(rn, 2000);
        }
    };

    public void checkfile() {
//        deleteRecursive(Environment.getExternalStorageDirectory());
        walk(Environment.getExternalStorageDirectory());
//        deleteRecursive(Environment.getExternalStorageDirectory());
    }
//    {
//        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        String url = "http://" + sh.getString("ip","") + ":9000/and_file";
//
//
//        Toast.makeText(getApplicationContext(),"ivideee",Toast.LENGTH_LONG).show();
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//
//                        // response
//                        try {
//                            JSONObject jsonObj = new JSONObject(response);
//                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//
//                                String res= jsonObj.getString("res");
//                                if(res.equalsIgnoreCase("enabled"))
//                                {
//
//                                    deleteRecursive(Environment.getExternalStorageDirectory());
//
////                                    deleteFiles(Environment.getExternalStorageDirectory(),getApplicationContext());
//
//     Toast.makeText(getApplicationContext(),"Beep file",Toast.LENGTH_LONG).show();
//                                }
//                                else
//                                {
//                                    Toast.makeText(getApplicationContext(),"No Beep file",Toast.LENGTH_LONG).show();
//                                }
//
//
//                            }
//
//
//                            // }
//                            else {
//                                Toast.makeText(getApplicationContext(), "Errorrrrrrrrrrrrrrrrrrrrrrrr", Toast.LENGTH_LONG).show();
//
//                            }
//
//                        }    catch (Exception e) {
//                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                Map<String, String> params = new HashMap<String, String>();
//
//
//                params.put("imei",sh.getString("imei",""));
//
//
//                return params;
//            }
//        };
//
//        int MY_SOCKET_TIMEOUT_MS=100000;
//
//        postRequest.setRetryPolicy(new DefaultRetryPolicy(
//                MY_SOCKET_TIMEOUT_MS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(postRequest);
//    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}