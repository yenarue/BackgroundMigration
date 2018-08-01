package com.yenarue.android.detectpackagestatus;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.yenarue.android.detectpackagestatus.util.Log;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private JobScheduler jobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        jobScheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        TextView joblistTextView = (TextView) this.findViewById(R.id.joblist_textview);
        joblistTextView.setText(String.valueOf(jobScheduler.getAllPendingJobs()));

        // 백그라운드 잡스케줄러 테스트
        Switch intervalJobSetSwitch = (Switch) this.findViewById(R.id.jobscheduler_interval_deadline_button);

        JobInfo intervalJobInfo = jobScheduler.getPendingJob(SingleJobService.INTERVAL_JOB_ID);
        if (intervalJobInfo != null) {
            intervalJobSetSwitch.setChecked(true);
//            intervalJobInfo.getMinLatencyMillis();
        }

        intervalJobSetSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Context context = getApplicationContext();

                if (isChecked) {
                    Log.d("MainActivity", "Job button onClick!");
                    JobInfo jobInfo = new JobInfo.Builder(SingleJobService.INTERVAL_JOB_ID, new ComponentName(context, SingleJobService.class))
                            .setMinimumLatency(3000L)
                            .setOverrideDeadline(5000L)
                            .setPersisted(true)
                            .setRequiresCharging(true)
                            .setRequiresDeviceIdle(true)
                            .build();

                    int result = jobScheduler.schedule(jobInfo);
                    Log.d("MainActivity", "jobSchedule result=" + result);
                } else {
                    jobScheduler.cancel(SingleJobService.INTERVAL_JOB_ID);
                }
            }
        });

        Switch periodicJobSwitch = (Switch) this.findViewById(R.id.jobscheduler_periodic_button);

        JobInfo periodicJobInfo = jobScheduler.getPendingJob(SingleJobService.PERIODIC_JOB_ID);
        if (periodicJobInfo != null) {
            periodicJobSwitch.setChecked(true);
//            periodicJobSwitch.getMinLatencyMillis();
        }

        periodicJobSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Context context = getApplicationContext();

                if (isChecked) {
                    Log.d("MainActivity", "Periodic Job button onClick!");
                    JobInfo jobInfo = new JobInfo.Builder(SingleJobService.PERIODIC_JOB_ID, new ComponentName(context, SingleJobService.class))
                            .setPeriodic(3000L, 3000L) //36000000L)
                            .setPersisted(true)
                            .setRequiresCharging(true)
                            .setRequiresDeviceIdle(true)
                            .build();

                    int result = jobScheduler.schedule(jobInfo);
                    Log.d("MainActivity", "jobSchedule result=" + result);
                } else {
                    jobScheduler.cancel(SingleJobService.PERIODIC_JOB_ID);
                }
            }
        });

        Switch jobSetSwitch = (Switch) this.findViewById(R.id.jobscheduler_button);

        JobInfo jobInfo = jobScheduler.getPendingJob(SingleJobService.JOB_ID);
        if (jobInfo != null) {
            jobSetSwitch.setChecked(true);
        }

        jobSetSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Context context = getApplicationContext();

                if (isChecked) {
                    Log.d("MainActivity", "Job button onClick!");
                    JobInfo jobInfo = new JobInfo.Builder(SingleJobService.JOB_ID, new ComponentName(context, SingleJobService.class))
                            .setPersisted(true)
                            .setRequiresCharging(true)
                            .setRequiresDeviceIdle(true)
                            .build();

                    int result = jobScheduler.schedule(jobInfo);
                    Log.d("MainActivity", "jobSchedule result=" + result);
                } else {
                    jobScheduler.cancel(SingleJobService.JOB_ID);
                }
            }
        });

        // 포그라운드 서비스 테스트
        Button foregroundButton = (Button) this.findViewById(R.id.foreground_button);
        foregroundButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PackageStatusService.class);
                startForegroundService(intent);
            }
        });

        // 일반 노티 테스트
        final Button notiButton = (Button) this.findViewById(R.id.noti_button);
        notiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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