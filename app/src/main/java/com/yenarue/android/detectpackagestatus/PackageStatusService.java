package com.yenarue.android.detectpackagestatus;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by yenarue on 2018-06-08.
 */

public class PackageStatusService extends Service {

    public static String TAG = PackageStatusService.class.getSimpleName();

    PackageStatusReceiver packageStatusReceiver = new PackageStatusReceiver();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate");

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(Intent.ACTION_UNINSTALL_PACKAGE);
        intentFilter.addDataScheme("package");

        registerReceiver(packageStatusReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");

        unregisterReceiver(packageStatusReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
