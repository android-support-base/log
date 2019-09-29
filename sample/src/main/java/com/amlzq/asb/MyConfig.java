package com.amlzq.asb;

import android.app.Application;
import android.os.Environment;

import java.io.File;

/**
 * {@link MyConfig#IDENTIFY}的全局配置
 * 初始化第三方类库
 */
public class MyConfig {

    /**
     * 应用程序标识
     */
    public static String IDENTIFY = "SupportLog";
    /**
     * 调试开关
     */
    public static boolean DEBUG = BuildConfig.DEBUG;

    public static String CACHE_DIR_PATH = "";

    public static void configApplication(Application application) {
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