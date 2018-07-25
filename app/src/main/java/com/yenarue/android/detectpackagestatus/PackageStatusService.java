package com.yenarue.android.detectpackagestatus;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.yenarue.android.detectpackagestatus.util.Log;

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

        createAndShowForegroundNotification(this, 1);

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

    private void createAndShowForegroundNotification(Service yourService, int notificationId) {

        final NotificationCompat.Builder builder = ChannelManager.getNotificationBuilder(yourService, ChannelManager.CHANNEL_SERVICE,
                NotificationManagerCompat.IMPORTANCE_LOW); //Low importance prevent visual appearance for this notification channel on top

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        builder.setOngoing(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setTicker("틱커텍스트")
                .setSubText("서브텍스트")
                .setContentTitle("컨텐트타이틀")
                .setContentText("컨텐트텍스트");

        Notification notification = builder.build();

        yourService.startForeground(notificationId, notification);

//        if (notificationId != lastShownNotificationId) {
//            // Cancel previous notification
//            final NotificationManager nm = (NotificationManager) yourService.getSystemService(Activity.NOTIFICATION_SERVICE);
//            nm.cancel(lastShownNotificationId);
//        }
//        lastShownNotificationId = notificationId;
    }

}
