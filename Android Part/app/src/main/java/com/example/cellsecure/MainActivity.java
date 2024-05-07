package com.example.cellsecure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edip;
    Button btn;

    TextView textView;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edip=findViewById(R.id.editTextText);
        btn=findViewById(R.id.button);
        textView=findViewById(R.id.textView);
        btn.setOnClickListener(this);
        textView2=findViewById(R.id.textView2);


        String imei = Settings.System.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        textView.setText(imei);


        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        edip.setText(sh.getString("ip", ""));
        SharedPreferences.Editor ed = sh.edit();
        ed.putString("imei", imei);
        ed.commit();

        SubscriptionManager manager = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
        int defaultSmsId = manager.getDefaultSmsSubscriptionId();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        SubscriptionInfo info = manager.getActiveSubscriptionInfo(defaultSmsId);
//        info.g();
        Toast.makeText(MainActivity.this, info.getNumber()+"", Toast.LENGTH_SHORT).show();

//        SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        edip.setText(sh.getString("ip",""));




        String oldsim=sh.getString("sim","0");

        Toast.makeText(MainActivity.this,"old--"+oldsim+"new"+info.getNumber(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, info.getNumber()
                , Toast.LENGTH_SHORT).show();
        textView2.setText(sh.getString("sim","")+" :Old\n"+info.getNumber()+" :New n ");




        if(oldsim.equalsIgnoreCase(""))
        {

        }
        else {
            if (oldsim.equals(info.getNumber() + "")) {
                Toast.makeText(MainActivity.this, "SAME", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "not same", Toast.LENGTH_SHORT).show();
                serveroperation sim=new serveroperation(getApplicationContext());
                sim.simchange();

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("815780372" +
                        "" +
                        "8", null, "simchanged", null, null);
            }
        }

        SharedPreferences.Editor number=sh.edit();
        number.putString("sim", info.getNumber()+"");
        number.commit();


        Toast.makeText(this, info.getNumber()
                , Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onClick(View view) {
        String ip=edip.getText().toString();
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("url","http://"+ip+":8000");
        ed.commit();

        Toast.makeText(this, " "+ip, Toast.LENGTH_SHORT).show();
        startService(new Intent(getApplicationContext(), gpstracker.class));
        startService(new Intent(getApplicationContext(), callreciever.class));
        startService(new Intent(getApplicationContext(), Smsout.class));
        startService(new Intent(getApplicationContext(), Smsreciever.class));





    }
}