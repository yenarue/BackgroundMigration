package com.yenarue.android.detectpackagestatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.yenarue.android.detectpackagestatus.util.Log;

/**
 * Created by yenarue on 2018-06-08.
 */

public class PackageStatusReceiver extends BroadcastReceiver {

    private static String TAG = PackageStatusReceiver.class.getSimpleName();

    public PackageStatusReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, action);

//        if(action.equals(Intent.ACTION_PACKAGE_ADDED)) {
//
//        } else if(action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
//
//        } else if(action.equals(Intent.ACTION_UNINSTALL_PACKAGE)) {
//        }
    }

}
