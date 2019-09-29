package com.amlzq.asb.timber;

import android.util.Log;

import timber.log.Timber;

/**
 * Created by amlzq on 2018/8/21.
 * 崩溃报告树
 */

public class CrashReportingTree extends Timber.Tree {

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        Log.println(priority, tag, message + "\n" + Log.getStackTraceString(t));
//        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
//            return;
//        }
//        FakeCrashLibrary.log(priority, tag, message);
//        if (t != null) {
//            if (priority == Log.ERROR) {
//                FakeCrashLibrary.logError(t);
//            } else if (priority == Log.WARN) {
//                FakeCrashLibrary.logWarning(t);
//            }
//        }
    }

}
