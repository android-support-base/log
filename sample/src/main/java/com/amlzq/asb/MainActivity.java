package com.amlzq.asb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.amlzq.android.log.Log;
import com.amlzq.android.log.MessageOnlyLogFilter;
import com.amlzq.asb.log.LogFragment;
import com.amlzq.asb.timber.FileLoggingTree;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MainActivity extends FragmentActivity {

    // format"date time PID-TID/package priority/tag: message"
    // 08-09 19:39:18.571
    // 23636-23643/com.amlzq.asb.log
    // I/zygote64:
    // Do partial code cache collection, code=30KB, data=18KB
    // 08-09 19:39:18.571 23636-23643/com.amlzq.asb.log I/zygote64: Do partial code cache collection, code=30KB, data=18KB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This method sets up our custom logger, which will print all log messages to the device
        // screen, as well as to adb logcat.
        initializeLogging();

        initializeLogger();

        initializeTimber();

    }

    /**
     * Create a chain of targets that will receive log data
     */
    public void initializeLogging() {
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        // Wraps Android's native log framework
        Log.TAG = MyConfig.IDENTIFY;
        Log.LEVEL = Log.VERBOSE;

        // Wraps Android's native log framework.
        com.amlzq.android.log.LogWrapper wrapper = new com.amlzq.android.log.LogWrapper();

        // Wraps Logger framework.
//        com.amlzq.asb.logger.LoggerWrapper wrapper = new com.amlzq.asb.logger.LoggerWrapper();

        // Wraps Timber framework.
//        com.amlzq.asb.timber.TimberWrapper wrapper = new com.amlzq.asb.timber.TimberWrapper();

        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(wrapper);

        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        wrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        LogFragment logFragment = (LogFragment) getSupportFragmentManager()
                .findFragmentById(R.id.log_fragment);
        msgFilter.setNext(logFragment.getLogView());

        Log.v("Ready go");
    }

    void initializeLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter());

        Logger.v("Ready go");
    }

    void initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new FileLoggingTree());
        }
        Timber.tag(MyConfig.IDENTIFY);

        Timber.v("Ready go");
    }

    public void onOrhanobutLoggerEvent(View view) {
        Logger.v("A button with ID %s was clicked to say '%s'.", view.getId(), view.getAlpha());
        Logger.d("A button with ID %s was clicked to say '%s'.", view.getId(), view.getAlpha());
        Logger.i("A button with ID %s was clicked to say '%s'.", view.getId(), view.getAlpha());
        Logger.w("A button with ID %s was clicked to say '%s'.", view.getId(), view.getAlpha());
        Logger.e("A button with ID %s was clicked to say '%s'.", view.getId(), view.getAlpha());
        Logger.wtf("A button with ID %s was clicked to say '%s'.", view.getId(), view.getAlpha());

        List<String> list = new ArrayList<String>();
        list.add("user1");
        list.add("user2");
        list.add("user3");
        list.add("user4");

        Logger.d(list);
    }

    @SuppressLint("TimberArgCount")
    public void onTimberEvent(View view) {
        Timber.tag(MyConfig.IDENTIFY);
        Timber.v("A button with ID %s was clicked to say '%s'.", view.getId(), view.getAlpha());
        Timber.d("A button with ID %s was clicked to say '%s'.", view.getId(), view.getAlpha());
        Timber.i("A button with ID %s was clicked to say '%s'.", view.getId(), view.getAlpha());
        Timber.w("A button with ID %s was clicked to say '%s'.", view.getId(), view.getAlpha());
        Timber.e("A button with ID %s was clicked to say '%s'.", view.getId(), view.getAlpha());
        Timber.wtf("A button with ID %s was clicked to say '%s'.", view.getId(), view.getAlpha());
    }

    public void onLogEvent(View view) {
        Log.v(this);
        Log.d("A button with ID %s was clicked to say '%s'.", view.getId(), view.getAlpha());
        Log.i("A button with ID %s was clicked to say '%s'.", view.getId(), view.getAlpha());
        Log.w("A button with ID %s was clicked to say '%s'.", view.getId(), view.getAlpha());
        Log.e("A button with ID %s was clicked to say '%s'.", view.getId(), view.getAlpha());
        Log.wtf(this);

        Log.e(this, new NullPointerException("按钮是空指针"));
        try {
            int a = 2 / 0;
        } catch (Exception e) {
            Log.e(e);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(this);
            }
        }).start();
    }

    /**
     * 启动日志
     * 08-09 19:39:18.254 23636-23636/? I/zygote64: Late-enabling -Xcheck:jni
     08-09 19:39:18.571 23636-23643/com.amlzq.asb.log I/zygote64: Do partial code cache collection, code=30KB, data=18KB
     08-09 19:39:18.571 23636-23643/com.amlzq.asb.log I/zygote64: After code cache collection, code=30KB, data=18KB
     08-09 19:39:18.571 23636-23643/com.amlzq.asb.log I/zygote64: Increasing code cache capacity to 128KB
     08-09 19:39:18.761 23636-23701/com.amlzq.asb.log D/OpenGLRenderer: HWUI GL Pipeline
     08-09 19:39:18.853 23636-23701/com.amlzq.asb.log I/Adreno: QUALCOMM build                   : 2941438, I916dfac403
     Build Date                       : 10/03/17
     OpenGL ES Shader Compiler Version: EV031.21.02.00
     Local Branch                     : O18A
     Remote Branch                    :
     Remote Branch                    :
     Reconstruct Branch               :
     08-09 19:39:18.858 23636-23701/com.amlzq.asb.log I/Adreno: PFP: 0x005ff087, ME: 0x005ff063
     08-09 19:39:18.862 23636-23643/com.amlzq.asb.log I/zygote64: Do partial code cache collection, code=61KB, data=38KB
     08-09 19:39:18.862 23636-23701/com.amlzq.asb.log I/zygote64: android::hardware::configstore::V1_0::ISurfaceFlingerConfigs::hasWideColorDisplay retrieved: 0
     08-09 19:39:18.863 23636-23701/com.amlzq.asb.log I/OpenGLRenderer: Initialized EGL, version 1.4
     08-09 19:39:18.863 23636-23701/com.amlzq.asb.log D/OpenGLRenderer: Swap behavior 2
     08-09 19:39:18.867 23636-23643/com.amlzq.asb.log I/zygote64: After code cache collection, code=61KB, data=38KB
     08-09 19:39:18.867 23636-23643/com.amlzq.asb.log I/zygote64: Increasing code cache capacity to 256KB
     */

}
