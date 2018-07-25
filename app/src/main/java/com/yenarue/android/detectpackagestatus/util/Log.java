package com.yenarue.android.detectpackagestatus.util;

public class Log {
    private static final String APPNAME = "DETECTAPP";

    public static void d(String TAG, String message) {
        android.util.Log.d(APPNAME, "[" + TAG + "] " + message);
    }
}
