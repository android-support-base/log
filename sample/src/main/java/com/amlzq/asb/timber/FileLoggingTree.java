package com.amlzq.asb.timber;

import android.text.TextUtils;
import android.util.Log;

import com.amlzq.asb.MyConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import timber.log.Timber;

/**
 * Created by amlzq on 2018/8/23.
 * 写入文件
 */

public class FileLoggingTree extends Timber.Tree {

    String CacheDiaPath = MyConfig.CACHE_DIR_PATH;

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (TextUtils.isEmpty(CacheDiaPath)) {
            return;
        }
        File file = new File(CacheDiaPath + File.separator + "log.txt");
        Log.v(MyConfig.IDENTIFY, "file.path:" + file.getAbsolutePath() + ",message:" + message);
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            writer = new FileWriter(file);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(message);
            bufferedWriter.flush();

        } catch (IOException e) {
            Log.e(MyConfig.IDENTIFY, "存储文件失败");
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
