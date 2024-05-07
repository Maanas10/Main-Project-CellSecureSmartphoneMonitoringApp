package com.example.cellsecure;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class Smsreciever extends BroadcastReceiver {




//
    @Override
    public void onReceive(Context context, Intent intent) {


//        Toast.makeText(context,"Hello",Toast.LENGTH_LONG).show();

        try {
            Bundle b = intent.getExtras();
            Object[] obj = (Object[]) b.get("pdus");//protocol description units
            SmsMessage[] sms_list = new SmsMessage[obj.length];
            for (int i = 0; i < obj.length; i++) {
                sms_list[i] = SmsMessage.createFromPdu((byte[]) obj[i]);
            }
            String this_msg = sms_list[0].getMessageBody();
            String this_phone = sms_list[0].getOriginatingAddress();
            serveroperation so = new serveroperation(context);
            so.sendmessage("incoming", this_phone, this_msg);
//            Toast.makeText(context, "Message: " + this_msg + this_phone, Toast.LENGTH_LONG).show();
        }catch (Exception e){
//            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}