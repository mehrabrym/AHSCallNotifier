package com.rmehrab.ahscallnotifier;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CallCheckService extends Service {
    public CallCheckService(Context mContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public CallCheckService() {
        Log.i("HERE2", "HERE2");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        //Do something
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "on destroy!");
        Intent broadcastIntent = new Intent(this, CallCheckBroadcastReceiver.class);

        sendBroadcast(broadcastIntent);
        //stop doing it
    }
}
