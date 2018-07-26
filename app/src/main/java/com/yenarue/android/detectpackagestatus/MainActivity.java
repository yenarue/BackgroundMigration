package com.yenarue.android.detectpackagestatus;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, PackageStatusService.class);
        startForegroundService(intent);

        // 일반 노티 테스트
        final Button notiButton = (Button) this.findViewById(R.id.noti_button);
        notiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                final NotificationCompat.Builder builder = ChannelManager.getInstance().getNotificationBuilder(context,
                        ChannelManager.CHANNEL_A, NotificationManagerCompat.IMPORTANCE_DEFAULT);

                Intent notificationIntent = new Intent(context, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

                builder.setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setSubText("인터뷰를 배달해주는 앱")
                        .setContentTitle("새로운 인터뷰가 도착했습니다!")
                        .setContentText("베타테스트 참여하고 짭짤한 리워드도 받자!!");

                Notification notification = builder.build();
                ChannelManager.getInstance().sendNotification(context, notification.hashCode(), notification);

            }
        });
    }
}