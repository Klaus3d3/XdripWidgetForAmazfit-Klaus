package com.klaus3d3.xdripwidgetforamazfit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by edoardotassinari on 04/04/18.
 * modded by klaus3d3 for xdrip
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(Constants.TAG, "BOOT_COMPLETED");

        Intent serviceIntent = new Intent(context, MainService.class);
        context.startService(serviceIntent);
    }
}
