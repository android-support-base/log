package com.amlzq.asb;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class APPConfig {

    /**
     * 项目标识
     */
    public static final String IDENTIFY = "support-log-sample";

    public static String CACHE_DIR_PATH = "";

    public static void init(Context context) {
        File crashDir = new File(Environment.getExternalStorageDirectory().getPath()
                + File.separator + "amlzq"
                + File.separator + IDENTIFY
                + File.separator + "Crash");
        boolean wasSuccessful = true;
        if (!crashDir.exists()) {
            wasSuccessful = crashDir.mkdirs();
        }
        if (!wasSuccessful) {
            System.out.println("was not successful.");
        }
        CACHE_DIR_PATH = crashDir.getPath();
    }

}
