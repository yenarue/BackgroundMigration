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

        // MainThread 에서 동작하므로 오래걸리는 작업이라면 Async로 태우기

        if (params.isOverrideDeadlineExpired()) {
            Log.d(TAG, "[" + params.getJobId() + "] isOverrideDeadlineExpired!");

            // Job의 실행 조건이 만족하지 않은 상태에서 DeadLine에 걸려 실행된 경우
        }

        this.jobFinished(params, true);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "[" + params.getJobId() + "] onStopJob");

        // Job 실행 도중 실행 조건이 해제되었을 경우, onStopJob()이 호출됨.
        // 예외처리 필요

        return false;
    }
}
