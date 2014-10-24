package com.aa.android.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aa.android.common.data.SharedDataEvent;
import com.aa.android.util.DebugLog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class designed to manage connections to wear and also handlers that can handle events.
 * <p/>
 * Created by layne on 10/16/14.
 */
public final class WearHandlerManager
        implements NodeApi.NodeListener, MessageApi.MessageListener, DataApi.DataListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    private static final String TAG = WearHandlerManager.class.getSimpleName();

    private final Set<WearHandler> mHandlers
            = Collections.newSetFromMap(new ConcurrentHashMap<WearHandler, Boolean>(10, 0.75f, 1));
    private final boolean         mAddApiListeners;
    private       Context         mContext;
    private       GoogleApiClient mApiClient;
    private final AtomicBoolean mConnected = new AtomicBoolean();

    /**
     * Creates a {@code WearHandlerManager}.
     *
     * @param addApiListeners
     *         true if {@code NodeApi}, {@code MessageApi}, and {@code DataApi} listeners should be added and events
     *         sent to the registered wear handlers.
     */
    public WearHandlerManager(boolean addApiListeners)
    {
        this.mAddApiListeners = addApiListeners;
    }

    /**
     * Registers a group of handlers for wear communication events.
     *
     * @param handlers
     *         the handlers to register
     */
    public synchronized void registerHandlers(WearHandler... handlers)
    {
        Collections.addAll(mHandlers, handlers);
    }

    /**
     * Registers a handler for wear communication events.
     *
     * @param handler
     *         the handler to register
     */
    public synchronized void registerHandler(WearHandler handler)
    {
        mHandlers.add(handler);
    }

    /**
     * Unregisters a handler from receiver wear communication events.
     *
     * @param handler
     *         the handler to unregister
     */
    public synchronized void unregisterHandler(WearHandler handler)
    {
        mHandlers.remove(handler);
    }

    /**
     * Initializes the {@code GoogleApiClient}.
     *
     * @param context
     *         the context
     */
    public synchronized void init(Context context)
    {
        if (mApiClient == null)
        {
            mContext = context.getApplicationContext();
            mApiClient = new GoogleApiClient.Builder(mContext)
                    .addApi(Wearable.API)
                    .build();
        }
    }

    /**
     * Starts listening for wear events and notifies wear handlers. Also creates the {@code GoogleApiClient} if not
     * already created and connects to it.
     *
     * @throws IllegalStateException
     *         if {@link #init(android.content.Context)} has not been called and the {@code GoogleApiClient} is null
     */
    public void start()
    {
        if (mApiClient == null)
        {
            throw new IllegalStateException("Must call init(context) before calling start()");
        }
        mApiClient.registerConnectionCallbacks(this);
        mApiClient.registerConnectionFailedListener(this);
        if (mAddApiListeners)
        {
            Wearable.NodeApi.addListener(mApiClient, this);
            Wearable.MessageApi.addListener(mApiClient, this);
            Wearable.DataApi.addListener(mApiClient, this);
        }
        mApiClient.connect();
    }

    /**
     * Stops listening to wear events and disconnectes from the associated {@code GoogleApiClient}.
     *
     * @throws IllegalStateException
     *         if {@link #init(android.content.Context)} has not been called and the {@code GoogleApiClient} is null
     */
    public void stop()
    {
        if (mApiClient == null)
        {
            throw new IllegalStateException("Must call init(context) before calling stop()!");
        }
        if (mAddApiListeners)
        {
            Wearable.NodeApi.removeListener(mApiClient, this);
            Wearable.MessageApi.removeListener(mApiClient, this);
            Wearable.DataApi.removeListener(mApiClient, this);
        }
        mApiClient.unregisterConnectionCallbacks(this);
        mApiClient.unregisterConnectionFailedListener(this);
        if (mApiClient.isConnected() || mApiClient.isConnecting())
        {
            mApiClient.disconnect();
        }
        mConnected.set(false);
    }

    /**
     * Gets the api client. Will be null until {@link #init(android.content.Context)} is called.
     *
     * @return the api client
     */
    @Nullable
    public GoogleApiClient getApiClient()
    {
        return mApiClient;
    }

    public boolean isConnected()
    {
        return mConnected.get();
    }

    /**
     * Helper method for {@link com.aa.android.common.CommonUtils#getConnectedNodeIds(com.google.android.gms.common.api.GoogleApiClient)}.
     *
     * @return the connected node ids
     */
    public Collection<String> getConnectedNodeIds()
    {
        return CommonUtils.getConnectedNodeIds(mApiClient);
    }

    /**
     * Helper method for {@link com.google.android.gms.wearable.NodeApi#getConnectedNodes(com.google.android.gms.common.api.GoogleApiClient)}.
     *
     * @return the pending result
     */
    public PendingResult<NodeApi.GetConnectedNodesResult> getConnectedNodes()
    {
        return Wearable.NodeApi.getConnectedNodes(mApiClient);
    }

    /**
     * Helper method for {@link com.google.android.gms.wearable.NodeApi#getLocalNode(com.google.android.gms.common.api.GoogleApiClient)}.
     *
     * @return the pending result
     */
    public PendingResult<NodeApi.GetLocalNodeResult> getLocalNode()
    {
        return Wearable.NodeApi.getLocalNode(mApiClient);
    }

    /**
     * Helper method for {@link com.google.android.gms.wearable.DataApi#putDataItem(com.google.android.gms.common.api.GoogleApiClient,
     * com.google.android.gms.wearable.PutDataRequest)}.
     *
     * @param dataRequest
     *         the put data request
     *
     * @return the pending result
     */
    public PendingResult<DataApi.DataItemResult> putDataItem(PutDataRequest dataRequest)
    {
        return Wearable.DataApi.putDataItem(mApiClient, dataRequest);
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents)
    {
        DebugLog.i(TAG, "onDataChanged: %s", dataEvents);

        final List<DataEvent> events = FreezableUtils.freezeIterable(dataEvents);
        dataEvents.release();

        NEXT_EVENT:
        for (DataEvent dataEvent : events)
        {
            DebugLog.d(TAG, "dataEvent: %s", dataEvent);
            SharedDataEvent sharedDataEvent = SharedDataEvent.from(dataEvent);
            for (WearHandler handler : mHandlers)
            {
                if (handler.handleDataEvent(mContext, mApiClient, sharedDataEvent))
                {
                    DebugLog.d(TAG, "handler: %s handled event: %s", handler.getClass(), dataEvent);
                    continue NEXT_EVENT;
                }
            }
        }
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent)
    {
        DebugLog.i(TAG, "onMessageReceived: %s", messageEvent);

        for (WearHandler handler : mHandlers)
        {
            if (handler.handleMessageEvent(mContext, mApiClient, messageEvent))
            {
                DebugLog.d(TAG, "handler: %s handled message: %s", handler.getClass(), messageEvent);
                break;
            }
        }
    }

    @Override
    public void onPeerConnected(Node peer)
    {
        DebugLog.d(TAG, "onPeerConnected: %s", peer);

        for (WearHandler handler : mHandlers)
        {
            handler.onPeerConnected(peer);
        }
    }

    @Override
    public void onPeerDisconnected(Node peer)
    {
        DebugLog.d(TAG, "onPeerDisconnected: %s", peer);

        for (WearHandler handler : mHandlers)
        {
            handler.onPeerDisconnected(peer);
        }
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        DebugLog.d(TAG, "onConnected: %s", bundle);
        mConnected.set(true);

        for (WearHandler handler : mHandlers)
        {
            handler.onConnected(bundle);
        }
    }

    @Override
    public void onConnectionSuspended(int i)
    {
        DebugLog.d(TAG, "onConnectionSuspended: %d", i);
        mConnected.set(false);

        for (WearHandler handler : mHandlers)
        {
            handler.onConnectionSuspended(i);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result)
    {
        DebugLog.d(TAG, "onConnectionFailed: %s", result);
        mConnected.set(false);

        for (WearHandler handler : mHandlers)
        {
            handler.onConnectionFailed(result);
        }
    }
}
