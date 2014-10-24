package com.aa.android.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.aa.android.common.BuildConfig;

/**
 * A wrapper for Android Log
 */
public final class DebugLog
{
    public static final  int DEFAULT_LOG_LEVEL      = Log.DEBUG;
    private static final int DEFAULT_LOG_MAX_LENGTH = 4000;

    // Log definitions
    private static volatile int     sLocalLevel;
    private static volatile boolean sLocalLogV;
    private static volatile boolean sLocalLogD;
    private static volatile boolean sLocalLogI;
    private static volatile boolean sLocalLogW;
    private static volatile boolean sLocalLogE;
    private static volatile boolean sHttpLogEnabled;
    private static volatile boolean sToastEnabled;

    static
    {
        // Set logging enabled initially based on BuildConfig.DEBUG
        setAllEnabled(BuildConfig.DEBUG);
    }

    private DebugLog() {}

    public static void setAllEnabled(boolean enabled)
    {
        setLogEnabled(enabled);
        setHttpLogEnabled(enabled);
        setToastEnabled(enabled);
    }

    public static void setLogEnabled(boolean enabled)
    {
        setLogLevel(enabled ? DEFAULT_LOG_LEVEL : Integer.MAX_VALUE);
    }

    public static synchronized void setLogLevel(int logLevel)
    {
        sLocalLevel = logLevel;
        sLocalLogV = (logLevel <= Log.VERBOSE);
        sLocalLogD = (logLevel <= Log.DEBUG);
        sLocalLogI = (logLevel <= Log.INFO);
        sLocalLogW = (logLevel <= Log.WARN);
        sLocalLogE = (logLevel <= Log.ERROR);
    }

    public static int getLogLevel()
    {
        return sLocalLevel;
    }

    public static boolean isLogEnabled()
    {
        return sLocalLogE;
    }

    public static void setHttpLogEnabled(boolean enabled)
    {
        sHttpLogEnabled = enabled;
    }

    public static boolean isHttpLogEnabled()
    {
        return sHttpLogEnabled;
    }

    public static void setToastEnabled(boolean enabled)
    {
        sToastEnabled = enabled;
    }

    public static boolean isToastEnabled()
    {
        return sToastEnabled;
    }

    public static void toastShort(Context context, String msg)
    {
        if (sToastEnabled)
        {
            toast(context, msg, Toast.LENGTH_SHORT);
        }
        else
        {
            DebugLog.d("TOAST", msg);
        }
    }

    public static void toastLong(Context context, String msg)
    {
        if (sToastEnabled)
        {
            toast(context, msg, Toast.LENGTH_LONG);
        }
        else
        {
            DebugLog.d("TOAST", msg);
        }
    }

    public static void toast(Context context, String msg, int duration)
    {
        DebugLog.d("TOAST", msg);
        if (sToastEnabled)
        {
            Toast.makeText(context, msg, duration).show();
        }
    }

    public static void http(final String tag, final String msg)
    {
        if (sHttpLogEnabled)
        {
            processStringInChunks(msg, new StringProcessorCallback()
            {
                @Override
                public void onChunk(String bite)
                {
                    Log.i(tag, bite);
                }
            });
        }
    }

    public static void http(final String tag, final String format, final Object... args)
    {
        // Add this check so we don't unnecessarily format the string if we are
        // not going to output
        if (sHttpLogEnabled)
        {
            DebugLog.http(tag, String.format(format, args));
        }
    }

    public static void http(final String tag, final String msg, final Throwable tr)
    {
        if (sHttpLogEnabled)
        {
            processStringInChunks(msg, new StringProcessorCallback()
            {
                @Override
                public void onChunk(String bite)
                {
                    Log.e(tag, bite, tr);
                }
            });
        }
    }

    /** @see android.util.Log #v(String tag, String msg) * */
    public static void v(final String tag, final String msg)
    {
        if (sLocalLogV)
        {
            processStringInChunks(msg, new StringProcessorCallback()
            {
                @Override
                public void onChunk(String bite)
                {
                    Log.v(tag, bite);
                }
            });
        }
    }

    public static void v(final String tag, final String format, final Object... args)
    {
        // Add this check so we don't unnecessarily format the string if we are
        // not going to output
        if (sLocalLogV)
        {
            DebugLog.v(tag, String.format(format, args));
        }
    }

    /** @see android.util.Log #v(String tag, String msg, Throwable tr) * */
    public static void v(final String tag, final String msg, final Throwable tr)
    {
        if (sLocalLogV)
        {
            processStringInChunks(msg, new StringProcessorCallback()
            {
                @Override
                public void onChunk(String bite)
                {
                    Log.v(tag, bite, tr);
                }
            });
        }
    }

    /** @see android.util.Log #d(String tag, String msg) * */
    public static void d(final String tag, final String msg)
    {
        if (sLocalLogD)
        {
            processStringInChunks(msg, new StringProcessorCallback()
            {
                @Override
                public void onChunk(String bite)
                {
                    Log.d(tag, bite);
                }
            });
        }
    }

    public static void d(final String tag, final String format, final Object... args)
    {
        // Add this check so we don't unnecessarily format the string if we are
        // not going to output
        if (sLocalLogD)
        {
            DebugLog.d(tag, String.format(format, args));
        }
    }

    /** @see android.util.Log #d(String tag, String msg, Throwable tr) * */
    public static void d(final String tag, final String msg, final Throwable tr)
    {
        if (sLocalLogD)
        {
            processStringInChunks(msg, new StringProcessorCallback()
            {
                @Override
                public void onChunk(String bite)
                {
                    Log.d(tag, bite, tr);
                }
            });
        }
    }

    /** @see android.util.Log #i(String tag, String msg) * */
    public static void i(final String tag, final String msg)
    {
        if (sLocalLogI)
        {
            processStringInChunks(msg, new StringProcessorCallback()
            {
                @Override
                public void onChunk(String bite)
                {
                    Log.i(tag, bite);
                }
            });
        }
    }

    public static void i(final String tag, final String format, final Object... args)
    {
        // Add this check so we don't unnecessarily format the string if we are
        // not going to output
        if (sLocalLogI)
        {
            DebugLog.i(tag, String.format(format, args));
        }
    }

    /** @see android.util.Log #i(String tag, String msg, Throwable tr) * */
    public static void i(final String tag, final String msg, final Throwable tr)
    {
        if (sLocalLogI)
        {
            processStringInChunks(msg, new StringProcessorCallback()
            {
                @Override
                public void onChunk(String bite)
                {
                    Log.i(tag, bite, tr);
                }
            });
        }
    }

    /** @see android.util.Log #w(String tag, String msg) * */
    public static void w(final String tag, final String msg)
    {
        if (sLocalLogW)
        {
            processStringInChunks(msg, new StringProcessorCallback()
            {
                @Override
                public void onChunk(String bite)
                {
                    Log.w(tag, bite);
                }
            });
        }
    }

    public static void w(final String tag, final String format, final Object... args)
    {
        // Add this check so we don't unnecessarily format the string if we are
        // not going to output
        if (sLocalLogW)
        {
            DebugLog.w(tag, String.format(format, args));
        }
    }

    /** @see android.util.Log #w(String tag, String msg, Throwable tr) * */
    public static void w(final String tag, final String msg, final Throwable tr)
    {
        if (sLocalLogW)
        {
            processStringInChunks(msg, new StringProcessorCallback()
            {
                @Override
                public void onChunk(String bite)
                {
                    Log.w(tag, bite, tr);
                }
            });
        }
    }

    /** @see android.util.Log #e(String tag, String msg) * */
    public static void e(final String tag, final String msg)
    {
        if (sLocalLogE)
        {
            processStringInChunks(msg, new StringProcessorCallback()
            {
                @Override
                public void onChunk(String bite)
                {
                    Log.e(tag, bite);
                }
            });
        }
    }

    public static void e(final String tag, final String format, final Object... args)
    {
        // Add this check so we don't unnecessarily format the string if we are
        // not going to output
        if (sLocalLogE)
        {
            DebugLog.e(tag, String.format(format, args));
        }
    }

    /** @see android.util.Log #e(String tag, String msg, Throwable tr) * */
    public static void e(final String tag, final String msg, final Throwable tr)
    {
        if (sLocalLogE)
        {
            processStringInChunks(msg, new StringProcessorCallback()
            {
                @Override
                public void onChunk(String bite)
                {
                    Log.e(tag, bite, tr);
                }
            });
        }
    }

    // These methods are to help with logs that are too large for the default
    // buffer
    // We iterate over the string in chunks so as to log everything in the
    // string instead
    // of only logging the first part of the string
    private static void processStringInChunks(String log, StringProcessorCallback callback)
    {
        processStringInChunks(log, DEFAULT_LOG_MAX_LENGTH, callback);
    }

    private static void processStringInChunks(String string, int chunkSize, StringProcessorCallback callback)
    {
        int length;
        if (string == null || ((length = string.length()) <= chunkSize))
        {
            callback.onChunk(string);
            return;
        }

        int f = 0;
        int l = chunkSize;
        while (f < l)
        {
            String bite = string.substring(f, l);
            callback.onChunk(bite);

            f = l;
            l += chunkSize;
            if (l > length)
            {
                l = length;
            }
        }
    }


    private static interface StringProcessorCallback
    {
        void onChunk(String bite);
    }
}
