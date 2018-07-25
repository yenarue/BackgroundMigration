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
                NotificationManagerCompat.IMPORTANCE_MIN); //Low importance prevent visual appearance for this notification channel on top

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        builder.setOngoing(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setSubText("패키지 상태를 감시하는 앱")
                .setContentTitle("패키지 상태를 감시중입니다.")
                .setContentText("감시하는게 싫으면 끄세요. 대신 앱 설치/삭제 관련 정보는 받을 수 없게 됩니다.");

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
