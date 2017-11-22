package com.splendidsky.bronzeragent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by 伟宸 on 2017/11/22.
 */

public class ConnectServiceBroadcastReceiver extends BroadcastReceiver {

    private static String START_ACTION = "CONNECT_SERVICE_START";
    private static String STOP_ACTION = "CONNECT_SERVICE_STOP";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(ConnectService.TAG, Thread.currentThread().getName() + "---->"
                + "ServiceBroadcastReceiver onReceive");

        String action = intent.getAction();
        if (START_ACTION.equalsIgnoreCase(action)) {
            context.startService(new Intent(context, ConnectService.class));

            Log.d(ConnectService.TAG, Thread.currentThread().getName() + "---->"
                    + "ServiceBroadcastReceiver onReceive start end");
        } else if (STOP_ACTION.equalsIgnoreCase(action)) {
            context.stopService(new Intent(context, ConnectService.class));
            Log.d(ConnectService.TAG, Thread.currentThread().getName() + "---->"
                    + "ServiceBroadcastReceiver onReceive stop end");
        }
    }
}
