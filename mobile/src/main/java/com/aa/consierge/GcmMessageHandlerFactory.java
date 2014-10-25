/*
 * GcmMessageHandlerFactory.java
 * American Airlines Android App
 *
 * Created on 3/27/14 2:46 PM
 * Copyright (c) 2014 American Airlines. All rights reserved.
 */

package com.aa.consierge;

import android.app.Service;
import android.os.Bundle;

import com.aa.android.util.DebugLog;

import java.util.ArrayList;
import java.util.List;


public class GcmMessageHandlerFactory {
    private static final String TAG = GcmMessageHandlerFactory.class.getSimpleName();

    public static final String EXTRA_COLLAPSE_KEY = "collapse_key";
    public static final String EXTRA_FROM = "from";
    public static final String EXTRA_NOTIFICATION_TYPE = "notificationtype";
    public static final String EXTRA_CONTENT = "com.xtify.sdk.NOTIFICATION_CONTENT";
    public static final String EXTRA_CONTENT_JSON = "content";

    private final List<GcmMessageHandler> handlers;

    public static GcmMessageHandlerFactory getDefault() {
        List<GcmMessageHandler> handlers = new ArrayList<>();
        handlers.add(new QuestionHandler());
        return new GcmMessageHandlerFactory(handlers);
    }

    public GcmMessageHandlerFactory(List<GcmMessageHandler> handlers) {
        this.handlers = handlers;
    }

    public boolean handleGcmMessage(Bundle extras, Service service) {
        if (extras != null) {
            DebugLog.d(TAG, "extras: %s", extras);
            for (String key : extras.keySet()) {
                DebugLog.d(TAG, "key: %s", key);
            }
            GcmMessageHolder holder = new GcmMessageHolder(extras);
            GcmMessageHandler handler = getGcmMessageHandler(holder.type);
            if (handler != null) {
                DebugLog.i(TAG,
                        "handle gcm message. collapse_key: %s, from: %s, type: %s, contentJson: %s",
                        holder.collapseKey,
                        holder.from,
                        holder.type,
                        holder.contentJson);
                return handler.handleGcmMessage(service,
                        holder.collapseKey,
                        holder.from,
                        holder.type,
                        holder.content,
                        holder.contentJson);
            }
        }
        return false;
    }

    private GcmMessageHandler getGcmMessageHandler(GcmMessageType type) {
        for (GcmMessageHandler handler : handlers) {
            if (handler.canHandleType(type)) {
                return handler;
            }
        }
        return null;
    }


    private static class GcmMessageHolder {
        private final String collapseKey;
        private final String from;
        private final GcmMessageType type;
        private final String content;
        private final String contentJson;

        private GcmMessageHolder(Bundle extras) {
            this.collapseKey = extras.getString(EXTRA_COLLAPSE_KEY, "");
            this.from = extras.getString(EXTRA_FROM, "");
            this.type = GcmMessageType.fromString(extras.getString(EXTRA_NOTIFICATION_TYPE, ""));
            this.content = extras.getString(EXTRA_CONTENT, "");
            this.contentJson = extras.getString(EXTRA_CONTENT_JSON, "");
        }
    }
}
