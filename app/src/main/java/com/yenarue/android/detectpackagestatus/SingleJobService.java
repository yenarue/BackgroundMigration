package com.yenarue.android.detectpackagestatus;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.yenarue.android.detectpackagestatus.util.Log;

public class SingleJobService extends JobService {

    private static String TAG = SingleJobService.class.getSimpleName();

    public static int JOB_ID = 1000;
    public static int INTERVAL_JOB_ID = 1001;
    public static int PERIODIC_JOB_ID = 1002;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "[" + params.getJobId() + "] onStartJob : " + this);

//        Context context = getApplicationContext();
//        JobInfo jobInfo = new JobInfo.Builder(1, new ComponentName(context, CollectUsageDataJobService.class))
////                .setPeriodic(3000L) //36000000L)
//                .setMinimumLatency(3000)
//                .setRequiresCharging(true)
////                        .setPersisted(true)
//                .build();
//
//        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
//        jobScheduler.schedule(jobInfo);

        if (params.isOverrideDeadlineExpired()) {
            Log.d(TAG, "[" + params.getJobId() + "] isOverrideDeadlineExpired!");
        }

        this.jobFinished(params, true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "[" + params.getJobId() + "] onStopJob");
        return false;
    }
}
