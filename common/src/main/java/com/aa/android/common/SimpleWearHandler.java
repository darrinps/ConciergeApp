package com.aa.android.common;

import android.content.Context;
import android.os.Bundle;

import com.aa.android.common.data.SharedDataEvent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;

/**
 * A convenience class to extend when you only want to handle a subset of the google api / wear events. This implements
 * all methods in the {@link com.aa.android.common.WearHandler} but does nothing and returns {@code false} for all
 * applicable methods.
 * <p/>
 * Created by layne on 10/17/14.
 */
public class SimpleWearHandler implements WearHandler
{
    @Override
    public boolean handleMessageEvent(Context context, GoogleApiClient client, MessageEvent messageEvent)
    {
        return false;
    }

    @Override
    public boolean handleDataEvent(Context context, GoogleApiClient client, SharedDataEvent dataEvent)
    {
        return false;
    }

    @Override
    public void onConnected(Bundle bundle) {}

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onPeerConnected(Node node) {}

    @Override
    public void onPeerDisconnected(Node node) {}

    @Override
    public void onConnectionFailed(ConnectionResult result) {}
}
