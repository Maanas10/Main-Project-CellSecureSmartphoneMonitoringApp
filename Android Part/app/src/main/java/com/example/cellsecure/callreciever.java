package com.example.cellsecure;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class callreciever extends BroadcastReceiver {


    private static boolean incomingFlag = false;

    private static String incoming_number = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving


//        if (intent.getAction() != null)

        {

            if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {

//            Toast.makeText(context, "out call ", Toast.LENGTH_SHORT).show();

                String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                serveroperation so = new serveroperation(context);
                so.call("outgoing", phoneNumber);
//                Toast.makeText(context, "out call " +phoneNumber, Toast.LENGTH_SHORT).show();
            }
            else {

                TelephonyManager tm =

                        (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);


                switch (tm.getCallState()) {

                    case TelephonyManager.CALL_STATE_RINGING:




                        incomingFlag = true;//标识当前是来电

                        String incoming_number = intent.getStringExtra("incoming_number");
//                        Toast.makeText(context,"incoming" +incoming_number,Toast.LENGTH_LONG).show();

                        serveroperation so = new serveroperation(context);
                        so.call("incoming", incoming_number);
                        break;

                    case TelephonyManager.CALL_STATE_OFFHOOK:

                        if (incomingFlag) {

//						Log.i(TAG, "incoming ACCEPT :" + incoming_number);

                        }

                        break;


                    case TelephonyManager.CALL_STATE_IDLE:

                        if (incomingFlag) {

                            Log.i("hii", "incoming IDLE");

                        }

                        break;

                }


            }
        }
    }
}