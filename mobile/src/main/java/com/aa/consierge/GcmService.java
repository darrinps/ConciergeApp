/*
 * GcmService.java
 * American Airlines Android App
 *
 * Created on 3/24/14 3:04 PM
 * Copyright (c) 2014 American Airlines. All rights reserved.
 */

package com.aa.consierge;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.aa.android.util.AAExecutors;
import com.aa.android.util.DebugLog;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.concurrent.atomic.AtomicInteger;

public class GcmService extends Service {

    private static final String TAG = GcmService.class.getSimpleName();

    GcmMessageHandlerFactory mHandlerFactory;
    private AtomicInteger mCount;

    @Override
    public void onCreate() {
        super.onCreate();
        mHandlerFactory = GcmMessageHandlerFactory.getDefault();
        mCount = new AtomicInteger(0);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mCount.getAndIncrement();
        execute(intent, flags, startId);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DebugLog.d(TAG, "destroying service");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void execute(final Intent intent, final int flags, final int startId) {
        AAExecutors.NETWORK_EXECUTOR.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    processIntent(intent);
                } catch (Exception e) {
                    DebugLog.e(TAG, "failed processing", e);
                } finally {
                    GcmReceiver.completeWakefulIntent(intent);
                    final int count = mCount.decrementAndGet();
                    if (count < 1) {
                        stopSelf();
                    }
                    DebugLog.d(TAG, "queue size: %d", count);
                }
            }
        });
    }

    boolean processIntent(Intent intent) {
        Bundle extras;
        if (intent != null && (extras = intent.getExtras()) != null) {
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
            String messageType = gcm.getMessageType(intent);

            if (!extras.isEmpty() && messageType != null) {
                switch (messageType) {
                    case GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE: {
                        return processGcmMessage(extras);
                    }
                    case GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR: {
                        return handleSendError(extras);
                    }
                    case GoogleCloudMessaging.MESSAGE_TYPE_DELETED: {
                        return handleDeleteError(extras);
                    }
                }
            }
        }

        return false;
    }

    boolean processGcmMessage(Bundle extras) {
        return mHandlerFactory.handleGcmMessage(extras, this);
    }

    boolean handleSendError(Bundle extras) {
        // TODO: How do we handle this?
        DebugLog.e(TAG, "Gcm error, MESSAGE_TYPE_SEND_ERROR. extras: %s", extras);
        return false;
    }

    boolean handleDeleteError(Bundle extras) {
        // TODO: How do we handle this?
        DebugLog.e(TAG, "Gcm error, MESSAGE_TYPE_DELETED. extras: %s", extras);
        return false;
    }
}
