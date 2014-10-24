package com.aa.android.common;

import com.aa.android.util.DebugLog;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * This class represents the main connection between the app and wear. Android will start this service whenever any
 * communication is sent across nodes. In order to receive communication from a node, register a {@link
 * com.aa.android.common.WearHandler} through {@link #registerHandler(WearHandler)} or {@link
 * #registerHandlers(WearHandler...)} in order to handle events. The order in which handlers are registered are
 * important. If one handler handles an event, then no other handlers will be notified of the event. This is only
 * applicable for boolean returning handler methods such as {@link com.aa.android.common.WearHandler#handleDataEvent(android.content.Context,
 * com.google.android.gms.common.api.GoogleApiClient, com.aa.android.common.data.SharedDataEvent)} or {@link
 * com.aa.android.common.WearHandler#handleMessageEvent(android.content.Context, com.google.android.gms.common.api.GoogleApiClient,
 * com.google.android.gms.wearable.MessageEvent)}.
 */
public class WearService extends WearableListenerService
{
    private static final String TAG = WearService.class.getSimpleName();

    private static final WearHandlerManager sWearManager = new WearHandlerManager(false);

    /**
     * Registers a group of handlers for wear communication events.
     *
     * @param handlers
     *         the handlers to register
     */
    public static void registerHandlers(WearHandler... handlers)
    {
        sWearManager.registerHandlers(handlers);
    }

    /**
     * Registers a handler for wear communication events.
     *
     * @param handler
     *         the handler to register
     */
    public static void registerHandler(WearHandler handler)
    {
        sWearManager.registerHandler(handler);
    }

    /**
     * Unregisters a handler from receiver wear communication events.
     *
     * @param handler
     *         the handler to unregister
     */
    public static void unregisterHandler(WearHandler handler)
    {
        sWearManager.unregisterHandler(handler);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        sWearManager.init(this);
        sWearManager.start();
        DebugLog.d(TAG, "creating WearService");
        DebugLog.d(TAG, "is debug? %s", BuildConfig.DEBUG);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        sWearManager.stop();
        DebugLog.d(TAG, "destroying WearService");
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents)
    {
        super.onDataChanged(dataEvents);
        sWearManager.onDataChanged(dataEvents);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent)
    {
        super.onMessageReceived(messageEvent);
        sWearManager.onMessageReceived(messageEvent);
    }

    @Override
    public void onPeerConnected(Node peer)
    {
        super.onPeerConnected(peer);
        sWearManager.onPeerConnected(peer);
    }

    @Override
    public void onPeerDisconnected(Node peer)
    {
        super.onPeerDisconnected(peer);
        sWearManager.onPeerDisconnected(peer);
    }
}
