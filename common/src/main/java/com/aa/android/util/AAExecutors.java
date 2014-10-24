package com.aa.android.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class contains static executors/handlers to use throughout the app.
 * <p/>
 * Created by Layne Penney on 8/25/14.
 */
public final class AAExecutors
{
    private static final String TAG               = AAExecutors.class.getSimpleName();
    private static final int    CPU_COUNT         = Runtime.getRuntime().availableProcessors();
    private static final int    CORE_POOL_SIZE    = CPU_COUNT + 1;
    private static final int    MAXIMUM_POOL_SIZE = CPU_COUNT * 4 + 1;
    private static final long   KEEP_ALIVE        = 60L;

    private static final ThreadFactory sThreadFactory = new ThreadFactory()
    {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r)
        {
            return new Thread(r, "AANetwork #" + mCount.getAndIncrement());
        }
    };

    public static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());
    public static final Handler BACKGROUND_HANDLER;
    public static final ExecutorService NETWORK_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE,
            KEEP_ALIVE,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(),
            sThreadFactory);

    static
    {
        DebugLog.d(TAG, "CPU count: %d", CPU_COUNT);
        DebugLog.d(TAG, "Core pool size: %d", CORE_POOL_SIZE);
        DebugLog.d(TAG, "Max pool size: %d", MAXIMUM_POOL_SIZE);
        final HandlerThread thread = new HandlerThread("AABackgroundHandler");
        thread.start();
        BACKGROUND_HANDLER = new Handler(thread.getLooper());
    }

    private AAExecutors() {}
}
