package com.amlzq.asb.timber;

import android.text.TextUtils;
import android.util.Log;

import com.amlzq.asb.APPConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import timber.log.Timber;

/**
 * Created by yxkj on 2018/8/23.
 * 写入文件
 */

public class FileLoggingTree extends Timber.Tree {

    String CacheDiaPath = APPConfig.CACHE_DIR_PATH;

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (TextUtils.isEmpty(CacheDiaPath)) {
            return;
        }
        File file = new File(CacheDiaPath + File.separator + "log.txt");
        Log.v(APPConfig.IDENTIFY, "file.path:" + file.getAbsolutePath() + ",message:" + message);
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            writer = new FileWriter(file);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(message);
            bufferedWriter.flush();

        } catch (IOException e) {
            Log.e(APPConfig.IDENTIFY, "存储文件失败");
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
