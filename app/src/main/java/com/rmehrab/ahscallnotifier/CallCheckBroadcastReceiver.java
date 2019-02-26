package com.rmehrab.ahscallnotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class CallCheckBroadcastReceiver extends BroadcastReceiver {
    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;
    private static String savedNumber;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(CallCheckBroadcastReceiver.class.getSimpleName(), "Service Stops! Oops!");
        //context.startService(new Intent(context, CallCheckService.class));
        String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
        String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        Log.i("HERE_BR", "HERE IN BROADCAST RECEIVER!");
        Toast.makeText(context, "Received intent!" , Toast.LENGTH_SHORT).show();
        int state = 0;
        if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)){
            state = TelephonyManager.CALL_STATE_IDLE;
        }
        else if(stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            state = TelephonyManager.CALL_STATE_OFFHOOK;
        }
        else if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
            state = TelephonyManager.CALL_STATE_RINGING;
        }

        onCallStateChanged(context, state, number);
    }

    public void onCallStateChanged(Context context, int state, String number) {
        if(lastState == state){
            //No change
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();
                savedNumber = number;
                Toast.makeText(context, "Received ringing state!" , Toast.LENGTH_SHORT).show();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //Do nothing
                Toast.makeText(context, "Received offhook state!" , Toast.LENGTH_SHORT).show();
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                Toast.makeText(context, "Received idle state!" , Toast.LENGTH_SHORT).show();
                if(lastState == TelephonyManager.CALL_STATE_RINGING){
                    Toast.makeText(context, "Received missed call!" , Toast.LENGTH_SHORT).show();
                    //Ring but no pickup-  a miss
                    //TODO Send sms here
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("6477748320", null, "Missed Call from " + savedNumber + " at " + callStartTime.toString() + "!", null, null);
                }
                break;
        }

        lastState = state;
    }
}
