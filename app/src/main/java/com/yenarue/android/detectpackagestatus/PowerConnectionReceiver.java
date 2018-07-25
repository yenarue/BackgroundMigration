package com.yenarue.android.detectpackagestatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import com.yenarue.android.detectpackagestatus.util.Log;

public class PowerConnectionReceiver extends BroadcastReceiver {

    private static String TAG = PowerConnectionReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        Log.d(TAG, "isCharging=" + isCharging + ", usbCharge=" + usbCharge + ", acCharge=" + acCharge);
    }
}
