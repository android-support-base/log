package com.amlzq.asb.logger;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orhanobut.logger.LogAdapter;

/**
 * Created by amlzq on 2018/8/28.
 * 自定义日志
 */

public class CustomLogAdapter implements LogAdapter {

    @Override
    public boolean isLoggable(int priority, @Nullable String tag) {
        return false;
    }

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {

    }

}
