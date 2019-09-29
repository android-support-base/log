/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.amlzq.android.log;

/**
 * Helper class for a list (or tree) of LoggerNodes.
 * <p>When this is set as the head of the list,
 * an instance of it can function as a drop-in replacement for {@link android.util.Log}.
 * Most of the methods in this class server only to map a method call in Log to its equivalent
 * in LogNode.</p>
 * <p>format "date time PID-TID/package priority/tag: message"</p>
 */
public final class Log {
    // Grabbing the native values from Android's native logging facilities,
    // to make for easy migration and interop.
    public static final int NONE = -1;
    /**
     * Priority constant for the println method; use Log.v.
     * 详细(最低优先级, 最详细)
     */
    public static final int VERBOSE = android.util.Log.VERBOSE;
    /**
     * Priority constant for the println method; use Log.d.
     * 调试
     */
    public static final int DEBUG = android.util.Log.DEBUG;
    /**
     * Priority constant for the println method; use Log.i.
     * 信息
     */
    public static final int INFO = android.util.Log.INFO;
    /**
     * Priority constant for the println method; use Log.w.
     * 警告
     */
    public static final int WARN = android.util.Log.WARN;
    /**
     * Priority constant for the println method; use Log.e.
     * 错误
     */
    public static final int ERROR = android.util.Log.ERROR;
    /**
     * Priority constant for the println method.
     * 断言(最高优先级, 最简略)
     */
    public static final int ASSERT = android.util.Log.ASSERT;
    /**
     * not use for the println method.
     * 关闭日志
     */
    public static final int CLOSE = 8;

    /**
     * tag Used to identify the source of a log message.  It usually identifies
     * the class or activity where the log call occurs.
     */
    public static String TAG = Log.class.getName();

    /**
     * 请记住，无论日志级别设置如何，logcat 监视器都会继续收集所有消息。此设置仅决定 logcat 监视器的显示内容。
     * Verbose - 显示所有日志消息（默认值）。
     */
    public static int LEVEL = VERBOSE;

    /**
     * @return 返回日志级别
     */
    public static String getLevel() {
        switch (LEVEL) {
            case VERBOSE:
                return "VERBOSE";
            case DEBUG:
                return "DEBUG";
            case INFO:
                return "INFO";
            case WARN:
                return "WARN";
            case ERROR:
                return "ERROR";
            case ASSERT:
                return "ASSERT";
            case CLOSE:
                return "CLOSE";
            default:
                return "VERBOSE";
        }
    }

    // Stores the beginning of the LogNode topology.
    private static LogNode mLogNode;

    /**
     * @return Returns the next LogNode in the linked list.
     */
    public static LogNode getLogNode() {
        return mLogNode;
    }

    /**
     * @param node Sets the LogNode data will be sent to.
     */
    public static void setLogNode(LogNode node) {
        mLogNode = node;
    }

    /**
     * Instructs the LogNode to print the log data provided. Other LogNodes can
     * be chained to the end of the LogNode as desired.
     *
     * @param priority Log level of the data being logged. Verbose, Error, etc.
     * @param tag      Tag for for the log data. Can be used to organize log statements.
     * @param msg      The actual message to be logged.
     * @param tr       If an exception was thrown, this can be sent along for the logging facilities
     *                 to extract and print useful information.
     */
    public static void println(int priority, String tag, String msg, Throwable tr) {
        if (priority < LEVEL) return;
        if (mLogNode != null) {
            mLogNode.println(priority, tag, msg, tr);
        }
    }

    /**
     * Instructs the LogNode to print the log data provided. Other LogNodes can
     * be chained to the end of the LogNode as desired.
     *
     * @param priority Log level of the data being logged. Verbose, Error, etc.
     * @param tag      Tag for for the log data. Can be used to organize log statements.
     * @param msg      The actual message to be logged. The actual message to be logged.
     */
    public static void println(int priority, String tag, String msg) {
        println(priority, tag, msg, null);
    }

    // ===============================
    // 6中类型日志
    // Log.v()：记录Verbose类型日志
    // Log.d()：记录Debug类型日志
    // Log.i()：记录Info类型日志
    // Log.w()：记录Warn类型日志
    // Log.e()：记录Error类型日志
    // Log.wtf()：记录Assert类型日志
    // ===============================

    /**
     * Prints a message at VERBOSE priority.
     *
     * @param tag Tag for for the log data. Can be used to organize log statements.
     * @param msg The actual message to be logged.
     * @param tr  If an exception was thrown, this can be sent along for the logging facilities
     *            to extract and print useful information.
     */
    public static void v(String tag, String msg, Throwable tr) {
        println(VERBOSE, tag, msg, tr);
    }

    /**
     * Prints a message at VERBOSE priority.
     *
     * @param tag Tag for for the log data. Can be used to organize log statements.
     * @param msg The actual message to be logged.
     */
    public static void v(String tag, String msg) {
        v(tag, msg, null);
    }

    /**
     * Default tag
     *
     * @param msg The actual message to be logged.
     */
    public static void v(Object msg) {
        v(TAG, "" + msg);
    }

    /**
     * Prints a message at DEBUG priority.
     *
     * @param tag Tag for for the log data. Can be used to organize log statements.
     * @param msg The actual message to be logged.
     * @param tr  If an exception was thrown, this can be sent along for the logging facilities
     *            to extract and print useful information.
     */
    public static void d(String tag, String msg, Throwable tr) {
        println(DEBUG, tag, msg, tr);
    }

    /**
     * Prints a message at DEBUG priority.
     *
     * @param tag Tag for for the log data. Can be used to organize log statements.
     * @param msg The actual message to be logged.
     */
    public static void d(String tag, String msg) {
        d(tag, msg, null);
    }

    /**
     * Default tag
     *
     * @param msg The actual message to be logged.
     */
    public static void d(Object msg) {
        d(TAG, "" + msg);
    }

    /**
     * Log a verbose message with optional format args.
     *
     * @param msg  The actual message to be logged.
     * @param args variable
     */
    public static void d(String msg, Object... args) {
        d(TAG, formatMessage(msg, args));
    }

    /**
     * Prints a message at INFO priority.
     *
     * @param tag Tag for for the log data. Can be used to organize log statements.
     * @param msg The actual message to be logged.
     * @param tr  If an exception was thrown, this can be sent along for the logging facilities
     *            to extract and print useful information.
     */
    public static void i(String tag, String msg, Throwable tr) {
        println(INFO, tag, msg, tr);
    }

    /**
     * Prints a message at INFO priority.
     *
     * @param tag Tag for for the log data. Can be used to organize log statements.
     * @param msg The actual message to be logged.
     */
    public static void i(String tag, String msg) {
        i(tag, msg, null);
    }

    /**
     * Default tag
     *
     * @param msg The actual message to be logged.
     */
    public static void i(Object msg) {
        i(TAG, "" + msg);
    }

    /**
     * Log a verbose message with optional format args.
     *
     * @param msg  The actual message to be logged.
     * @param args variable
     */
    public static void i(String msg, Object... args) {
        i(TAG, formatMessage(msg, args));
    }

    /**
     * Prints a message at WARN priority.
     *
     * @param tag Tag for for the log data. Can be used to organize log statements.
     * @param msg The actual message to be logged.
     * @param tr  If an exception was thrown, this can be sent along for the logging facilities
     *            to extract and print useful information.
     */
    public static void w(String tag, String msg, Throwable tr) {
        println(WARN, tag, msg, tr);
    }

    /**
     * Prints a message at WARN priority.
     *
     * @param tag Tag for for the log data. Can be used to organize log statements.
     * @param msg The actual message to be logged.
     */
    public static void w(String tag, String msg) {
        w(tag, msg, null);
    }

    /**
     * Prints a message at WARN priority.
     *
     * @param tag Tag for for the log data. Can be used to organize log statements.
     * @param tr  If an exception was thrown, this can be sent along for the logging facilities
     *            to extract and print useful information.
     */
    public static void w(String tag, Throwable tr) {
        w(tag, null, tr);
    }

    /**
     * Default tag
     *
     * @param msg The actual message to be logged.
     */
    public static void w(Object msg) {
        w(TAG, "" + msg);
    }

    /**
     * Default tag
     *
     * @param msg The actual message to be logged.
     * @param tr  If an exception was thrown, this can be sent along for the logging facilities
     *            to extract and print useful information.
     */
    public static void w(Object msg, Throwable tr) {
        w(TAG, "" + msg, tr);
    }

    /**
     * Log a verbose message with optional format args.
     *
     * @param msg  The actual message to be logged.
     * @param args variable
     */
    public static void w(String msg, Object... args) {
        w(TAG, formatMessage(msg, args));
    }

    /**
     * Prints a message at ERROR priority.
     *
     * @param tag Tag for for the log data. Can be used to organize log statements.
     * @param msg The actual message to be logged.
     * @param tr  If an exception was thrown, this can be sent along for the logging facilities
     *            to extract and print useful information.
     */
    public static void e(String tag, String msg, Throwable tr) {
        println(ERROR, tag, msg, tr);
    }

    /**
     * Prints a message at ERROR priority.
     *
     * @param tag Tag for for the log data. Can be used to organize log statements.
     * @param msg The actual message to be logged.
     */
    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    /**
     * Default tag
     *
     * @param msg The actual message to be logged.
     */
    public static void e(Object msg) {
        e(TAG, "" + msg);
    }

    /**
     * Default tag
     *
     * @param msg The actual message to be logged.
     * @param tr  If an exception was thrown, this can be sent along for the logging facilities
     *            to extract and print useful information.
     */
    public static void e(Object msg, Throwable tr) {
        e(TAG, "" + msg, tr);
    }

    /**
     * Log a verbose message with optional format args.
     *
     * @param msg  The actual message to be logged.
     * @param args variable
     */
    public static void e(String msg, Object... args) {
        e(TAG, formatMessage(msg, args));
    }

    /**
     * Prints a message at ASSERT priority.
     *
     * @param tag Tag for for the log data. Can be used to organize log statements.
     * @param msg The actual message to be logged.
     * @param tr  If an exception was thrown, this can be sent along for the logging facilities
     *            to extract and print useful information.
     */
    public static void wtf(String tag, String msg, Throwable tr) {
        println(ASSERT, tag, msg, tr);
    }

    /**
     * Prints a message at ASSERT priority.
     *
     * @param tag Tag for for the log data. Can be used to organize log statements.
     * @param msg The actual message to be logged.
     */
    public static void wtf(String tag, String msg) {
        wtf(tag, msg, null);
    }

    /**
     * Prints a message at ASSERT priority.
     *
     * @param tag Tag for for the log data. Can be used to organize log statements.
     * @param tr  If an exception was thrown, this can be sent along for the logging facilities
     *            to extract and print useful information.
     */
    public static void wtf(String tag, Throwable tr) {
        wtf(tag, null, tr);
    }

    /**
     * Default tag
     *
     * @param msg The actual message to be logged.
     */
    public static void wtf(Object msg) {
        wtf(TAG, "" + msg);
    }

    /**
     * Formats a log message with optional arguments.
     */
    private static String formatMessage(String message, Object[] args) {
        return args == null || args.length == 0 ? message : String.format(message, args);
    }

}