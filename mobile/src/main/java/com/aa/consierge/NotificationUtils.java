/*
 * NotificationUtils.java
 * American Airlines Android App
 *
 * Created on 4/2/14 4:54 PM
 * Copyright (c) 2014 American Airlines. All rights reserved.
 */

package com.aa.consierge;

import android.app.Notification;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.concurrent.atomic.AtomicInteger;

public final class NotificationUtils {
    private static final AtomicInteger sNotificationId = new AtomicInteger(23_000);

    private NotificationUtils() {}

    public static NotificationCompat.Builder defaultBuilder(Context context) {
        return new NotificationCompat.Builder(context)
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(),
                        R.drawable.ic_launcher
                ))
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(context.getString(R.string.app_name))
                .setDefaults(Notification.DEFAULT_ALL);
    }

    public static NotificationCompat.WearableExtender defaultExtender(Context context) {
        return new NotificationCompat.WearableExtender();
        // TODO: add a background for notifications
//                .setBackground(BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_wearable_main));

    }

    public static void notify(Context context, String title, String content) {
        NotificationCompat.Builder builder = NotificationUtils.defaultBuilder(context);
        builder.setContentTitle(title);
        builder.setContentText(content);
        notify(context, builder.build());
    }

    public static void notify(Context context, Notification notification) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(sNotificationId.getAndIncrement(), notification);
    }

    public static void notify(Context context, String tag, int id, Notification notification) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(tag, id, notification);
    }
}
