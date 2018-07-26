package com.yenarue.android.detectpackagestatus;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class ChannelManager {

    private static ChannelManager sinstance = null;

    public static final String CHANNEL_SERVICE = "channel_service";
    public static final String CHANNEL_A = "channel_a";

    private ChannelManager() {}

    public static ChannelManager getInstance() {
        if (sinstance == null) {
            sinstance = new ChannelManager();
        }
        return sinstance;
    }

    public NotificationCompat.Builder getNotificationBuilder(Context context, String channelId, int importance) {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            prepareChannel(context, channelId, importance);
            builder = new NotificationCompat.Builder(context, channelId);
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        return builder;
    }

    public void sendNotification(Context context, int id, Notification notification) {
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
    }

    @TargetApi(26)
    private void prepareChannel(Context context, String id, int importance) {
        final String appName = context.getString(R.string.app_name);
        String description = "채널디스크립션";
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            NotificationChannel nChannel = notificationManager.getNotificationChannel(id);

            if (nChannel == null) {
                nChannel = new NotificationChannel(id, appName, importance);
                nChannel.setDescription(description);
                notificationManager.createNotificationChannel(nChannel);
            }
        }
    }
}
