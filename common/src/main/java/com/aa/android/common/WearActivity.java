package com.aa.android.common;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aa.android.common.events.Events;
import com.aa.android.util.DebugLog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;

import de.greenrobot.event.EventBus;

/**
 * This class provides the basic boilerplate code for a Wear Activity that can communicate with the main app.
 */
public abstract class WearActivity extends Activity implements WearHandler {
    private static final String TAG = WearActivity.class.getSimpleName();
    /** Request code for launching the Intent to resolve Google Play services errors. */
    private static final int REQUEST_RESOLVE_ERROR = 2199;

    private final WearHandlerManager mWearManager = new WearHandlerManager(true);
    protected boolean mResolvingError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWearManager.init(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mWearManager.registerHandler(this);
        mWearManager.start();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        mWearManager.unregisterHandler(this);
        mWearManager.stop();
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mResolvingError = false;
    }

    @Override
    public void onConnectionSuspended(int i) {
        // allow subclass override
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Already attempting to resolve an error.
        if (mResolvingError) return;

        if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                GoogleApiClient client = mWearManager.getApiClient();
                if (client != null) {
                    client.connect();
                }
            }
        } else {
            mResolvingError = false;
        }
    }

    @Override
    public void onPeerConnected(Node node) {
        DebugLog.d(TAG, "Node Connected: %s", node.getId());
    }

    @Override
    public void onPeerDisconnected(Node node) {
        DebugLog.d(TAG, "Node Disconnected: %s", node.getId());
    }

    /**
     * Gets the WearHandlerManager.
     *
     * @return the WearHandlerManager
     */
    @NonNull
    public WearHandlerManager getWearManager() {
        return mWearManager;
    }

    /**
     * Gets the GoogleApiClient. Will be null until {@link #onCreate(android.os.Bundle)} is called.
     *
     * @return the GoogleApiClient
     */
    @Nullable
    public GoogleApiClient getApiClient() {
        return mWearManager.getApiClient();
    }

    public void onEventMainThread(Events.DummyEvent event) {
        // dummy
    }
}
