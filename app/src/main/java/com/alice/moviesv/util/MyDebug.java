package com.alice.moviesv.util;

import android.support.graphics.drawable.BuildConfig;
import android.util.Log;

/**
 * Created by alice on 3/29/17.
 */

public class MyDebug {
    private final static String APP_HEADER = "MOTB_";
    public static boolean debug = true;
    public static String DebugLog = "";
    private static final int MAX_LOG_LINES = 10;

    public static void LOGW(final String tag, String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (debug) {
            Log.w(APP_HEADER + tag, message);
        }
        updateLogHolder(tag,message);
    }

    public static void LOGD(final String tag, String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (debug) {
            Log.d(APP_HEADER + tag, message);
        }
        updateLogHolder(tag,message);
    }
    public static void LOGE(final String tag, String message) {
        if (debug) {
            Log.e(APP_HEADER + tag, message);
        }
        else {
            Log.e(APP_HEADER + tag, message);
        }
        updateLogHolder(tag,message);
    }
    public static void LOGE(final String tag, String message, Throwable tr) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (debug) {
            Log.e(APP_HEADER + tag, message, tr);
        }
        updateLogHolder(tag,message);
    }

    public static void LOGI(final String tag, String message) {
        if (debug) {
            Log.i(APP_HEADER + tag, message);
        }
        updateLogHolder(tag,message);
    }

    public static void LOGV(final String tag, String message) {
        if (debug) {
            Log.v(APP_HEADER + tag, message);
        }
        updateLogHolder(tag,message);
    }

    private static void updateLogHolder(String tag, String message) {
        String[] lines = DebugLog.split("\r\n|\r|\n");
        if (lines.length>MAX_LOG_LINES) {
            DebugLog = DebugLog.substring(DebugLog.indexOf('\n')+1);
        }
        DebugLog += "\n" + tag + ":" + message;
    }


    public static void LOGW(final String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
            Log.w("MOTB_" + tag, msg, tr);
        }
    }


    public static void LOGI(final String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
            Log.i(tag, msg, tr);
        }
    }


    public static void LOGV(final String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
            Log.v(tag, msg, tr);
        }
    }
}
