package com.aa.android.common;

import android.content.Context;

import com.aa.android.common.data.SharedDataEvent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.NodeApi;

/**
 * Represents an object that can handle wear message and data events from the app.
 */
public interface WearHandler
        extends NodeApi.NodeListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    /**
     * Called when a message is received. The message should be handled in this method if it matches an event this
     * handler can handle.
     *
     * @param context
     *         the context (typically a service)
     * @param client
     *         the google client
     * @param messageEvent
     *         the message event
     *
     * @return true if handled, false if not
     */
    boolean handleMessageEvent(Context context, GoogleApiClient client, MessageEvent messageEvent);

    /**
     * Called when a data item is received. The data should be handled in this method if it matches a type of data this
     * handler can handle.
     *
     * @param context
     *         the context (typically a service)
     * @param client
     *         the google client
     * @param dataEvent
     *         the data event
     *
     * @return true if handled, false if not
     */
    boolean handleDataEvent(Context context, GoogleApiClient client, SharedDataEvent dataEvent);
}
