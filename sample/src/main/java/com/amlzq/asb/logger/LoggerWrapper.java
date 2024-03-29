package com.amlzq.asb.logger;

import android.util.Log;

import com.amlzq.android.log.LogNode;
import com.orhanobut.logger.Logger;

/**
 * Helper class which wraps Logger utility in the Logger interface.  This way
 * normal DDMS output can be one of the many targets receiving and outputting logs simultaneously.
 */
public class LoggerWrapper implements LogNode {

    // For piping:  The next node to receive Log data after this one has done its work.
    private LogNode mNext;

    /**
     * @return Returns the next LogNode in the linked list.
     */
    public LogNode getNext() {
        return mNext;
    }

    /**
     * @param node Sets the LogNode data will be sent to..
     */
    public void setNext(LogNode node) {
        mNext = node;
    }

    /**
     * Prints data out to the console using Android's native log mechanism.
     *
     * @param priority Log level of the data being logged.  Verbose, Error, etc.
     * @param tag      Tag for for the log data.  Can be used to organize log statements.
     * @param msg      The actual message to be logged. The actual message to be logged.
     * @param tr       If an exception was thrown, this can be sent along for the logging facilities
     *                 to extract and print useful information.
     */
    @Override
    public void println(int priority, String tag, String msg, Throwable tr) {
        // There actually are log methods that don't take a msg parameter.  For now,
        // if that's the case, just convert null to the empty string and move on.
        String useMsg = msg;
        if (useMsg == null) {
            useMsg = "";
        }

        // If an exeption was provided, convert that exception to a usable string and attach
        // it to the end of the msg method.
        if (tr != null) {
            useMsg += "\n" + Log.getStackTraceString(tr);
        }

        // This is functionally identical to Log.x(tag, useMsg);
        // For instance, if priority were Log.VERBOSE, this would be the same as Log.v(tag, useMsg)
        switch (priority) {
            case android.util.Log.VERBOSE:
                Logger.v(useMsg);
                break;
            case android.util.Log.DEBUG:
                Logger.d(useMsg);
                break;
            case android.util.Log.INFO:
                Logger.i(useMsg);
                break;
            case android.util.Log.WARN:
                Logger.w(useMsg);
                break;
            case android.util.Log.ERROR:
                Logger.e(tr, useMsg);
                break;
            case android.util.Log.ASSERT:
                Logger.wtf(useMsg);
                break;
            default:
                break;
        }

        // If this isn't the last node in the chain, move things along.
        if (mNext != null) {
            mNext.println(priority, tag, useMsg, tr);
        }
    }

}
